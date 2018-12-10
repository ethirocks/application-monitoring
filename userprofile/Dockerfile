FROM openjdk:8

ADD ./target/userprofile-0.0.1-SNAPSHOT.jar     /usr/src/userprofile-0.0.1-SNAPSHOT.jar


# We state that we want to open port 9095 on the container
#EXPOSE 9095

WORKDIR usr/src

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb:27017/test","-Djava.security.egd=file:/dev/./urandom","-jar","userprofile-0.0.1-SNAPSHOT.jar"]
