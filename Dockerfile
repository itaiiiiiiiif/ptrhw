FROM maven:3.8.1-openjdk-11 as builder
WORKDIR /project
COPY . /project/
RUN mvn package exec:java


FROM adoptopenjdk/openjdk11
EXPOSE 8888
VOLUME /app
ADD target/RestVerticleApp-1.0-SNAPSHOT-fat.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["java", "-jar", "/app.jar"]