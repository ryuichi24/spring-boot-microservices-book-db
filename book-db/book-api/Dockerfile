FROM gradle:jdk18 AS build

ARG BUILD_DIR=/home/build

COPY . ${BUILD_DIR}
WORKDIR ${BUILD_DIR}

ENV SPRING_PROFILES_ACTIVE=prod
RUN gradle book-api:build --no-daemon

FROM openjdk:18.0

RUN mkdir /app

EXPOSE 80

ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=build "/home/build/book-api/build/libs/*.jar" /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]