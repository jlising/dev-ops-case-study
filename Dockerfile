FROM tomcat

MAINTAINER lisingj
RUN apt-get update && apt-get -y upgrade
WORKDIR /usr/local/tomcat
RUN chmod 0777 /usr/local/tomcat/webapps/ -R
COPY frontend/target/edm-api.war /usr/share/jenkins/ref/tomcat/edm-api.war

EXPOSE 8080