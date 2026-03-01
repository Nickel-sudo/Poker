FROM eclipse-temurin:21 AS server-image 

WORKDIR /app

COPY Server/build/libs/Server.jar app.jar

ENTRYPOINT ["true"]