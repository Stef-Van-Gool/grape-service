FROM openjdk:8-jdk-alpine
EXPOSE 8051
ARG JAR_FILE/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]