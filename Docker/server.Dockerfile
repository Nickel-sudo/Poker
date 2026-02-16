# Final server stage
FROM poker-jre:latest AS server-runtime
# CMD ["/opt/jre/bin/java", "-jar", "Server.jar"]