#!/bin/bash

CONTAINER_REGISTRY="docker.io"
ACCOUNT_NAME="ryuichi24"
AUTHOR_IMAGE_TAG="86fc8d"
BOOK_IMAGE_TAG="86fc8d"
COMMENT_IMAGE_TAG="86fc8d"

AUTHOR_SERVICE_CONTAINER_IMAGE="${CONTAINER_REGISTRY}/${ACCOUNT_NAME}/bookdb-author-service:${AUTHOR_IMAGE_TAG}"
BOOK_SERVICE_CONTAINER_IMAGE="${CONTAINER_REGISTRY}/${ACCOUNT_NAME}/bookdb-book-service:${BOOK_IMAGE_TAG}"
COMMENT_SERVICE_CONTAINER_IMAGE="${CONTAINER_REGISTRY}/${ACCOUNT_NAME}/bookdb-comment-service:${COMMENT_IMAGE_TAG}"

# databases
kubectl apply -f k8s/author-api-mysql-depl.yml
kubectl apply -f k8s/book-api-mysql-depl.yml
kubectl apply -f k8s/comment-api-mysql-depl.yml

# message broker
kubectl apply -f k8s/rabbitmq-depl.yml

# https://stackoverflow.com/questions/68888449/how-to-define-image-name-in-kubernetes-manifest-deployment-yml-file-dynamically
# https://stackoverflow.com/questions/54032336/need-some-explaination-of-kubectl-stdin-and-pipe
# services
sed "s,__DOCKER_IMAGE__,${AUTHOR_SERVICE_CONTAINER_IMAGE}," k8s/author-api-depl.yml | kubectl apply -f -
sed "s,__DOCKER_IMAGE__,${BOOK_SERVICE_CONTAINER_IMAGE}," k8s/book-api-depl.yml | kubectl apply -f -
sed "s,__DOCKER_IMAGE__,${COMMENT_SERVICE_CONTAINER_IMAGE}," k8s/comment-api-depl.yml | kubectl apply -f -

# api gateway
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.1/deploy/static/provider/cloud/deploy.yaml
kubectl apply -f k8s/ingress-srv.yml
