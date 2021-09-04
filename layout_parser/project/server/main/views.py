# project/server/main/views.py

from celery.result import AsyncResult
from flask import render_template, Blueprint, jsonify, request
from werkzeug.utils import secure_filename
import json
import hashlib

from project.server.tasks import create_task

from pathlib import Path
import os
from project.server.tasks import annotate_doc

UPLOADS_DIR = "uploads"

main_blueprint = Blueprint("main", __name__,)


@main_blueprint.route("/", methods=["GET"])
def home():
    return render_template("main/home.html")


@main_blueprint.route("/tasks", methods=["POST"])
def run_task():
    content = request.json
    task_type = content["type"]
    task = create_task.delay(int(task_type))
    return jsonify({"task_id": task.id}), 202


@main_blueprint.route("/tasks/<task_id>", methods=["GET"])
def get_status(task_id):
    task_result = AsyncResult(task_id)
    result_data = task_result.result
    try:
        json.dumps(result_data)
    except (TypeError, OverflowError):
        result_data = str(result_data)

    result = {
        "task_id": task_id,
        "task_status": task_result.status,
        "task_result": result_data
    }
    return json.dumps(result, ensure_ascii=False).encode('utf8'), 200, {'Content-type' : 'application/json; charset=utf-8'}

ALLOWED_EXTENSIONS = {'pdf', 'png', 'jpg', 'jpeg'}

def file_extension(filename):
    return filename.rsplit('.', 1)[1].lower()  

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@main_blueprint.route("/annotate_document", methods=["POST"])
def annoate_document():
    Path(UPLOADS_DIR).mkdir(parents=True,exist_ok=True)
    request_json_data = request.form.get('data')
    if request_json_data:
        request_data = json.loads(request_json_data)
        model_name = request_data.get('model_name')
        languages = request_data.get('languages')
        score_thresh_test = request_data.get('score_thresh_test')
        label_map = request_data.get('label_map')
        label_map = {int(k): v for k, v in label_map.items()}
    else:
        request_data = {}
        model_name = None
        languages = None
        score_thresh_test = None
        label_map = None

    files = request.files
    if 'file' not in files:
        return jsonify({'status': 'error', 'message': 'no "file" attribute'}), 400
    file = files['file']
    if file.filename == '':
        return jsonify({'status': "error", 'message': "File filename is empty"}), 400
    if file and allowed_file(file.filename):
        file_ext = file_extension(file.filename) 
        print(f"Uploaded file filename {file.filename}")
        sec_filename = secure_filename(file.filename)
        if not sec_filename or not sec_filename.endswith(f".{file_ext}"):
            sec_filename = hashlib.md5(file.filename.encode('utf-8')).hexdigest() + f".{file_ext}"
        print(f"Secure filename \"{sec_filename}\"")
        uploaded_file_path = os.path.join(UPLOADS_DIR, sec_filename)
        file.save(uploaded_file_path)

        task = annotate_doc.delay(uploaded_file_path, model_name = model_name, languages = languages, score_thresh_test = score_thresh_test, label_map = label_map)
        return jsonify({'status': 'success','task_id': task.id}), 200
        
        
from flask import json
from werkzeug.exceptions import HTTPException

@main_blueprint.errorhandler(HTTPException)
def handle_exception(e):
    """Return JSON instead of HTML for HTTP errors."""
    # start with the correct headers and status code from the error
    response = e.get_response()
    # replace the body with JSON
    response.data = json.dumps({
        "code": e.code,
        "name": e.name,
        "description": e.description,
    })
    response.content_type = "application/json"
    return response