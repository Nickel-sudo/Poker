# ---------- Stage 1: Build JARs ----------
FROM eclipse-temurin:21 AS build-gradle
WORKDIR /app

# Copy all source code
COPY . .

# Build all jars
RUN ./gradlew clean build -x test

# At this point, JARs exist:
# /app/Client/build/libs/Client.jar
# /app/Server/build/libs/Server.jar
# /app/SharedObjects/build/libs/SharedObjects.jar

# ---------- Stage 2: Build minimal JRE ----------
FROM eclipse-temurin:21 AS build-jre
WORKDIR /jre-build

# Use jlink to build a custom minimal JRE
RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.logging\
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

# ---------- Stage 3: Runtime ----------
FROM debian:buster-slim
WORKDIR /opt/app

# Copy minimal JRE
COPY --from=build-jre /jre /opt/jre

# Copy prebuilt JARs
COPY --from=build-gradle /app/Client/build/libs/Client.jar Client.jar
COPY --from=build-gradle /app/Server/build/libs/Server.jar Server.jar
COPY --from=build-gradle /app/SharedObjects/build/libs/SharedObjects.jar SharedObjects.jar
