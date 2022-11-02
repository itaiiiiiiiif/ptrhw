#FROM hirokimatsumoto/alpine-openjdk-11:latest as jlink-package
#
#RUN jlink \
#    --module-path /opt/java/jmods \
#         --compress=2 \
#         --add-modules jdk.jfr,jdk.management.agent,java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
#         --no-header-files \
#         --no-man-pages \
#         --output /opt/jdk-11-mini-runtime
#
#FROM alpine:3.8
#
#ENV JAVA_HOME=/opt/jdk-11-mini-runtime
#ENV PATH="$PATH:$JAVA_HOME/bin"
#
#COPY --from=jlink-package /opt/jdk-11-mini-runtime /opt/jdk-11-mini-runtime
#
#COPY ptrhw/RestVerticleApp-1.0-SNAPSHOT-fat.jar /target/app.jar
#
#ENTRYPOINT ["java", "-jar", "/target/app.jar"]

FROM maven:3.8.1-openjdk-11 as builder
WORKDIR /project
COPY . /project/
RUN mvn package exec:java


FROM adoptopenjdk/openjdk11
EXPOSE 8888
VOLUME /app
ADD target/RestVerticleApp-1.0-SNAPSHOT-fat.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["java", "-jar", "/app.jar"]


#FROM adoptopenjdk/openjdk11
#ENV VERTICLE_FILE RestVerticleApp-1.0-SNAPSHOT-fat.jar
#
## Set the location of the verticles
#ENV VERTICLE_HOME src/main/java/org/example/rest/verticles
#
#EXPOSE 8888
#
## Copy your fat jar to the container
#COPY /$VERTICLE_FILE $VERTICLE_HOME/
#
## Launch the verticle
#WORKDIR $VERTICLE_HOME
#ENTRYPOINT ["sh", "-c"]
#CMD ["exec java -jar $VERTICLE_FILE"]





#FROM hirokimatsumoto/alpine-openjdk-11:latest as jlink-package
#
#RUN jlink \
#     --module-path /opt/java/jmods \
#     --compress=2 \
#     --add-modules jdk.jfr,jdk.management.agent,java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
#     --no-header-files \
#     --no-man-pages \
#     --output /opt/jdk-11-mini-runtime
#
#FROM alpine:3.8
#
#ENV JAVA_HOME=/opt/jdk-11-mini-runtime
#ENV PATH="$PATH:$JAVA_HOME/bin"
#EXPOSE 8888
#COPY --from=jlink-package /opt/jdk-11-mini-runtime /opt/jdk-11-mini-runtime
#
#COPY target/RestVerticleApp-1.0-SNAPSHOT-fat.jar app.jar
#
#
#VOLUME /app
##ADD target/RestVerticleApp-1.0-SNAPSHOT-fat.jar app.jar
#CMD ["java", "-jar", "/app.jar"]
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]