FROM openjdk:8

COPY resources /usr/src/media-hub/resources

COPY target/media-hub-1.1-jar-with-dependencies.jar /usr/src/media-hub/

WORKDIR /usr/src/media-hub

CMD ["java", "-jar", "media-hub-1.1-jar-with-dependencies.jar"]
