# pull official base image
FROM python:3.9.6-slim-buster

# set work directory
WORKDIR /usr/src/app

# set environment variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

# install dependencies
RUN apt-get update \
    && apt-get install -y --no-install-recommends gcc g++ python3-opencv ca-certificates python3-dev git wget sudo ninja-build
    
RUN pip install --upgrade pip    

RUN pip install --no-cache-dir -U layoutparser
RUN pip install --no-cache-dir 'git+https://github.com/facebookresearch/detectron2.git@v0.4#egg=detectron2' 
RUN pip install --no-cache-dir layoutparser[ocr]      
COPY ./requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
# copy project
COPY project/server/preload_model.py project/server/preload_model.py
RUN python project/server/preload_model.py
RUN apt-get install -y --no-install-recommends poppler-utils tesseract-ocr libtesseract-dev tesseract-ocr-rus
COPY . .