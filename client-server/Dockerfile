FROM openjdk:8

ADD ./target/client-server-0.0.1-SNAPSHOT.jar     /usr/src/client-server-0.0.1-SNAPSHOT.jar


# We state that we want to open port 8082 on the container
EXPOSE 8082

WORKDIR usr/src

ENTRYPOINT ["java", "-jar","client-server-0.0.1-SNAPSHOT.jar"]
