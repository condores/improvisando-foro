FROM amazoncorretto:21-alpine3.20-jdk
COPY target/forohub-1.0.0-BETA.jar app.jar
ENTRYPOINT [ "java","-jar", "/app.jar"]
