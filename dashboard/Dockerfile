FROM openjdk:8

ADD ./target/dashboard-0.0.1-SNAPSHOT.jar     /usr/src/dashboard-0.0.1-SNAPSHOT.jar


# We state that we want to open port 8094 on the container
#EXPOSE 8094

WORKDIR usr/src

ENTRYPOINT ["java", "-jar","dashboard-0.0.1-SNAPSHOT.jar"]
