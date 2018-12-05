FROM openjdk:8

ADD ./target/eureka-registry-0.0.1-SNAPSHOT.jar /usr/src/eureka-registry-0.0.1-SNAPSHOT.jar
EXPOSE 8091
WORKDIR usr/src
ENTRYPOINT ["java","-jar","eureka-registry-0.0.1-SNAPSHOT.jar"]