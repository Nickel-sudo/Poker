From eclipse-temurin:21 AS server-image 

workdir /app

COPY build/libs/Server.jar app.jar

