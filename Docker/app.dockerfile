FROM eclipse-temurin:21 AS app-image 

WORKDIR /app

COPY App/build/libs/App.jar app.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", "app.jar"]