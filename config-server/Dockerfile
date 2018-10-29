FROM openjdk:10

ADD ./target/config-server-0.0.1-SNAPSHOT.jar /usr/src/config-server-0.0.1-SNAPSHOT.jar
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8092/actuator/health || exit 1
EXPOSE 8092
WORKDIR usr/src
ENTRYPOINT ["java","-jar","config-server-0.0.1-SNAPSHOT.jar"]