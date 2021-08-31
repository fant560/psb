import os
from re import I
import time
import tempfile
import cv2
from pdf2image import convert_from_path
import layoutparser as lp
import jsonpickle

from celery import Celery
from pathlib import Path



celery = Celery(__name__)
celery.conf.broker_url = os.environ.get("CELERY_BROKER_URL", "redis://localhost:6379")
celery.conf.result_backend = os.environ.get("CELERY_RESULT_BACKEND", "redis://localhost:6379")


OUTPUTS_DIR = "outputs"


@celery.task(name="create_task")
def create_task(task_type):
    time.sleep(int(task_type) * 10)
    return True

def get_image_files(doc_file, request_id):
    print(doc_file)
    if doc_file.endswith(".pdf"):
        return get_pdf_images(doc_file, request_id)
    elif doc_file.endswith(".png") or doc_file.endswith(".jpg") or doc_file.endswith("jpeg"):
        return [doc_file]



def get_pdf_images(doc_file, request_id):
    images = convert_from_path(doc_file, output_folder=os.path.join(OUTPUTS_DIR, request_id),paths_only=True)
    print(f"Result pdf images {images}")
    return images


@celery.task(name="annotate_doc")
def annotate_doc(doc_file, model_name = None, languages = None, score_thresh_test = None, label_map = None):
    model_name = model_name or 'lp://PrimaLayout/mask_rcnn_R_50_FPN_3x/config'
    languages = languages or ['rus', 'eng']
    score_thresh_test = score_thresh_test or 0.8
    task_id = annotate_doc.request.id
    ocr_agent = lp.TesseractAgent(languages=languages) 

    model = lp.Detectron2LayoutModel(model_name, 
                                    extra_config=["MODEL.ROI_HEADS.SCORE_THRESH_TEST", score_thresh_test],
                                    label_map=label_map)
    Path(os.path.join(OUTPUTS_DIR, task_id)).mkdir(parents=True,exist_ok=True)
    image_file_names = get_image_files(doc_file, task_id)
    result = {}
    for ix, image_file in enumerate(image_file_names):
        image = cv2.imread(image_file)
        image = image[...,::-1]
        layout = model.detect(image)
        for block in layout:
            segment_image = (block
                       .pad(left=5, right=5, top=5, bottom=5)
                       .crop_image(image))
            text = ocr_agent.detect(segment_image)
            block.set(text=text, inplace=True)
        result[ix] = map_layout(layout)
        drawn_image = lp.draw_box(image, layout, box_width=3) 
        drawn_image.save(os.path.join(OUTPUTS_DIR, task_id, f"{ix}_slide.jpg"))
    return result

def map_block(block):
    return {
        'text': block.text,
        'coordinates': block.coordinates,
        'type': block.type
    }

def map_layout(layout):
    return [map_block(layout_item) for layout_item in layout]
