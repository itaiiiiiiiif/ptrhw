FROM maven:3.6.3-openjdk-11 as builder
RUN mvn package

# 2nd Docker build stage: copy builder output and configure entry point
FROM adoptopenjdk:11-jre-hotspot
ENV APP_DIR /application
ENV APP_FILE RestVerticleApp.jar

EXPOSE 8888

WORKDIR $APP_DIR
COPY --from=builder /project/target/*-fat.jar $APP_DIR/$APP_FILE

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]