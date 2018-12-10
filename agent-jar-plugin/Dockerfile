FROM openjdk:8
EXPOSE 8089
ADD ./target/agent-jar-plugin-0.0.1-SNAPSHOT.jar /usr/src/agent-jar-plugin-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar","agent-jar-plugin-0.0.1-SNAPSHOT.jar"]

