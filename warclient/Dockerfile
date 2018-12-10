From tomcat:7.0.92-jre8
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/springmvcapplication.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]

