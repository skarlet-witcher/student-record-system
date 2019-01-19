FROM openjdk:11.0.1-jre

ADD ./build/libs/srs-api-config-1.0-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888