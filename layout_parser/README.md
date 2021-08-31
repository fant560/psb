# Setup

Run 
```sh
$ dockr-compose up -d --build
```

# Annotation requests

## Annotate
```
http://localhost:5004/annotate_document
```
### Params
- file : File to annotate (jpg, png, pdf)
- data : Json parameters set according to this library https://github.com/Layout-Parser/layout-parser
    - model_name: name of the model 
    - score_thresh_test: score threshold for recogniition
    - label_map: label mapping (text, title, figure, etc)
    - languages: languages list for OCR (for example ['eng', 'rus'])
    - All parameters and the data field itself is optional
## Check status
```
http://localhost:5004/tasks/<task_id>
```

# Asynchronous Tasks with Flask and Celery

Example of how to handle background processes with Flask, Celery, and Docker.

## Want to learn how to build this?

Check out the [post](https://testdriven.io/blog/flask-and-celery/).

## Want to use this project?

Spin up the containers:

```sh
$ docker-compose up -d --build
```

Open your browser to [http://localhost:5004](http://localhost:5004) to view the app or to [http://localhost:5556](http://localhost:5556) to view the Flower dashboard.

Trigger a new task:

```sh
$ curl http://localhost:5004/tasks -H "Content-Type: application/json" --data '{"type": 0}'
```

Check the status:

```sh
$ curl http://localhost:5004/tasks/<TASK_ID>/
```
