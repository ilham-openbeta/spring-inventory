FROM openjdk:11
ARG JAR_FILE=target/*.jar
ENV DB_HOST=host.docker.internal
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]