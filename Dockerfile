FROM tomcat

MAINTAINER lisingj
RUN apt-get update && apt-get -y upgrade
WORKDIR /usr/local/tomcat
RUN chmod 0777 /usr/local/tomcat/webapps/ -R
RUN cp frontend/target/edm-api.war /usr/local/tomcat/webapps/edm-api.war

EXPOSE 8080