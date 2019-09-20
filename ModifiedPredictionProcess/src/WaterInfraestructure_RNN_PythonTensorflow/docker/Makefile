help:
	@cat Makefile


# This boolean variable decides whether to run tensorflow-gpu with nvidia-docker, or tensorflow with nornal docker
GPU?=0

# This boolean variable decides whether to build an image with src and data in volumes, or included within the container
WITH_VOLUMES?=1


IMAGE_NAME?=brainiot_water_management_dam_prediction_smart_behaviour
PWD?=$(shell dirname `pwd`)
DATA?=$(PWD)/../labelled_data
SRC?=$(PWD)/../

ifeq ($(WITH_VOLUMES), 0)
    DOCKER_FILE?=Dockerfile_with_volumes
    VOLUMES?=-v $(SRC):/src/workspace -v $(DATA):/data
else
    DOCKER_FILE=Dockerfile_with_src_and_data
    VOLUMES?=
endif


BACKEND=tensorflow
PYTHON_VERSION?=3.6
CUDA_VERSION?=10.1
CUDNN_VERSION?=7

ifeq ($(GPU), 0)
    DOCKER?=GPU=$(GPU) nvidia-docker
    TENSORFLOW_LIB?=tensorflow-gpu
else
    DOCKER?=docker
    TENSORFLOW_LIB?=tensorflow
endif


build:
	docker build -t $(IMAGE_NAME) --build-arg python_version=$(PYTHON_VERSION) --build-arg cuda_version=$(CUDA_VERSION) --build-arg cudnn_version=$(CUDNN_VERSION) --build-arg tensorflow_lib=$(TENSORFLOW_LIB) -f $(DOCKER_FILE) ..

bash: build
	$(DOCKER) run -it -p 5000:5000 $(VOLUMES) --env KERAS_BACKEND=$(BACKEND) $(IMAGE_NAME) bash

server: build
	$(DOCKER) run -p 5000:5000 $(VOLUMES) --env KERAS_BACKEND=$(BACKEND) $(IMAGE_NAME)

