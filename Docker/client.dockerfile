FROM eclipse-temurin:21 AS client-image 

WORKDIR /app

COPY Client/build/libs/Client.jar app.jar

ENTRYPOINT ["true"]