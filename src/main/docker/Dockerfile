FROM openjdk:8
VOLUME /tmp
ARG JAR_FILE=build/libs/command-service.jar
EXPOSE 9000
COPY ${JAR_FILE} command-service.jar
ENTRYPOINT ["java","-jar","/command-service.jar"]