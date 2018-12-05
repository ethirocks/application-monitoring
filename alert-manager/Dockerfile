FROM openjdk:8
EXPOSE 8086
ADD ./target/alert-manager-0.0.1-SNAPSHOT.jar /usr/src/alert-manager-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar","alert-manager-0.0.1-SNAPSHOT.jar"]
