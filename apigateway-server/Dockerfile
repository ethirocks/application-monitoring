FROM openjdk:10-jdk

ADD ./target/apigateway-server-0.0.1-SNAPSHOT.jar /usr/app/apigateway-server-0.0.1-SNAPSHOT.jar

WORKDIR usr/app

ENTRYPOINT ["java","-jar", "apigateway-server-0.0.1-SNAPSHOT.jar"]