FROM java:8

EXPOSE 8080

VOLUME /tmp
COPY triber-sensor-1.0.0.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]