FROM openjdk:8
EXPOSE 8080
ADD ./target/monitoring-server-0.0.1-SNAPSHOT.jar /usr/src/monitoring-server-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar","monitoring-server-0.0.1-SNAPSHOT.jar"]

