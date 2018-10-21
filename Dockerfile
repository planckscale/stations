#FROM frolvlad/alpine-oraclejdk8:slim
FROM openjdk:8-jdk-alpine
EXPOSE 8080 8081
#RUN mkdir -p /tmp/api-stations/
#ADD ./build/libs/stations-0.0.1-SNAPSHOT.jar /tmp/api-stations/stations-0.0.1.jar
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} stations-0.0.1.jar
ADD /build/libs/stations-0.0.1-SNAPSHOT.jar stations-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/stations-0.0.1.jar"]
