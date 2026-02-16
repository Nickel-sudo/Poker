# Final client stage
FROM poker-jre:latest AS runtime
CMD ["/opt/jre/bin/java", "-jar", "Client.jar"]