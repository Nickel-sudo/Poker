From eclipse-temurin:21 AS client-image 

workdir /app

COPY build/libs/Client.jar app.jar