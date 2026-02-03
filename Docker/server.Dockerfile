# Stage 1: Build Server JAR using full JDK
FROM eclipse-temurin:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew :Server:clean :Server:build -x test

# Stage 2: Runtime using minimal JRE
FROM poker-jre:latest AS runtime
WORKDIR /opt/app
COPY --from=build /app/Server/build/libs/Server.jar japp.jar

# Run with minimal JRE
CMD ["/opt/jre/bin/java", "-jar", "japp.jar"]