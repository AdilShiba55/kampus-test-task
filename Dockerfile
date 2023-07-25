FROM openjdk:11-jdk-slim
ARG JAR_FILE=*.jar
COPY build/libs/kampus-test-task-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
