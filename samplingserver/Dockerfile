FROM openjdk:8
EXPOSE 8084
ADD ./target/samplingserver-0.0.1-SNAPSHOT.jar /usr/src/samplingserver-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar","samplingserver-0.0.1-SNAPSHOT.jar"]

