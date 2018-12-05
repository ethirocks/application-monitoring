FROM openjdk:8

ADD ./target/userAuthentication-0.0.1-SNAPSHOT.jar /usr/src/userAuthentication-0.0.1-SNAPSHOT.jar


# We state that we want to open port 8088 on the container
EXPOSE 8088

WORKDIR usr/src

ENTRYPOINT ["java", "-jar","userAuthentication-0.0.1-SNAPSHOT.jar"]
