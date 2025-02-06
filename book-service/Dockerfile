FROM openjdk:21-jdk-slim
COPY book-service/target/*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]