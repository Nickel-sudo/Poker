# Stage 1: Build Client JAR using full JDK
FROM eclipse-temurin:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew :Client:clean :Client:build -x test

# Stage 2: Runtime using minimal JRE
FROM poker-jre:latest AS runtime
WORKDIR /opt/app
COPY --from=build /app/Client/build/libs/Client.jar japp.jar

# Run with minimal JRE
CMD ["/opt/jre/bin/java", "-jar", "japp.jar"]