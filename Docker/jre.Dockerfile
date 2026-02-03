# Stage 1: Build minimal JRE inside container
FROM eclipse-temurin:21 AS build-jre
WORKDIR /jre-build

# Build minimal JRE using jlink
RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.logging, java.net, java.io, java.util  \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

# Stage 2: Base image containing only the JRE
FROM debian:buster-slim

# Copy the JRE built in container
COPY --from=build-jre /jre /opt/jre

# No JAVA_HOME; run apps directly with /opt/jre/bin/java