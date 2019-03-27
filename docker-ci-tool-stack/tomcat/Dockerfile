FROM tomcat

MAINTAINER jesslising
RUN apt-get update && apt-get -y upgrade
WORKDIR /usr/local/tomcat
RUN chmod 0777 /usr/local/tomcat/webapps/ -R
RUN chmod 0777 /usr/local/tomcat/webapps/* -R

EXPOSE 8080