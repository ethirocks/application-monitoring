FROM openjdk:8

ADD ./target/config-server-0.0.1-SNAPSHOT.jar /usr/app/config-server-0.0.1-SNAPSHOT.jar

WORKDIR usr/app

ENTRYPOINT ["java","-jar", "config-server-0.0.1-SNAPSHOT.jar"]