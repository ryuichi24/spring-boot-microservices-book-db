#!/bin/bash

# databases
kubectl apply -f k8s/author-api-mysql-depl.yml
kubectl apply -f k8s/book-api-mysql-depl.yml
kubectl apply -f k8s/comment-api-mysql-depl.yml

# message broker
kubectl apply -f k8s/rabbitmq-depl.yml

# services
kubectl apply -f k8s/author-api-depl.yml
kubectl apply -f k8s/book-api-depl.yml
kubectl apply -f k8s/comment-api-depl.yml

# api gateway
kubectl apply -f k8s/ingress-srv.yml
