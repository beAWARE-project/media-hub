FROM openjdk:8

COPY target/media-hub-1.0-jar-with-dependencies.jar /usr/src/media-hub

WORKDIR /usr/src/media-hub

CMD ["java", "-jar", "media-hub-1.0-jar-with-dependencies.jar"]
