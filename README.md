<h1 align="center">Book DB</h1>

# Architecture
![](assets/images/book-db-architecture.png)

# How to deploy

## Docker-compose

You need to add `application-database-prod.yml` and `application-prod.yml` in each Spring Boot project `resources` folder according to `application-database-dev.yml` and `application-dev.yml` files placed in the same folder.

```
docker-compose up -d
```

## Kubernetes

You need to have `kubectl` CLI installed on the host machine

```
./deployment_k8s.sh
```