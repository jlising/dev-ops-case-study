FROM tomcat:8-jre8  
MAINTAINER lisingj

USER root

WORKDIR /usr/local/tomcat
RUN chmod 0777 /usr/local/tomcat/webapps/ -R

RUN echo "export JAVA_OPTS=\"-Dapp.env=dev\"" > /usr/local/tomcat/bin/setenv.sh  
COPY frontend/target/edm-api.war /usr/local/tomcat/webapps/edm-api.war

EXPOSE 8080

CMD ["catalina.sh", "run"]  