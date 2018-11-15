FROM openjdk:10

ADD ./target/profilerClient-0.0.1-SNAPSHOT.jar     /usr/src/profilerClient-0.0.1-SNAPSHOT.jar


# We state that we want to open port 8082 on the container
EXPOSE 8082

WORKDIR usr/src

ENTRYPOINT ["java", "-jar","profilerClient-0.0.1-SNAPSHOT.jar"]
