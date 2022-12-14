#!/bin/bash

# databases
kubectl delete -f k8s/author-api-mysql-depl.yml
kubectl delete -f k8s/book-api-mysql-depl.yml
kubectl delete -f k8s/comment-api-mysql-depl.yml

# message broker
kubectl delete -f k8s/rabbitmq-depl.yml

# services
kubectl delete -f k8s/author-api-depl.yml
kubectl delete -f k8s/book-api-depl.yml
kubectl delete -f k8s/comment-api-depl.yml

# api gateway
kubectl delete -f k8s/ingress-srv.yml
