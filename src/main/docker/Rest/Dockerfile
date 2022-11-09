FROM adoptopenjdk/openjdk11
EXPOSE 8888
VOLUME /app
COPY RestVerticleApp-1.0-SNAPSHOT-fat.jar /target/app.jar
CMD ["java", "-jar", "/target/app.jar"]