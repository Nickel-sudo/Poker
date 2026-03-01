From eclipse-temurin:21 AS app-image 

workdir /app

COPY build/libs/App.jar app.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", app.jar]