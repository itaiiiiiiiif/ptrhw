FROM adoptopenjdk/openjdk11
EXPOSE 8888
VOLUME /app
ADD target/RestVerticleApp-1.0-SNAPSHOT-fat.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["java", "-jar", "/app.jar"]
