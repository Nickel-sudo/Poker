#!/bin/bash
set -e  # Exit immediately if any command fails

./gradlew clean build -x test


echo "Building all services..."
docker compose build

echo "Starting containers..."
docker compose up

#echo "Building base JRE image..."
#docker build -f Docker/jre.Dockerfile -t poker-jre:latest .


