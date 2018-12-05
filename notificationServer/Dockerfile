FROM openjdk:8
EXPOSE 8083
ADD ./target/notificationServer-0.0.1-SNAPSHOT.jar /usr/src/notificationServer-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar","notificationServer-0.0.1-SNAPSHOT.jar"]