# Required packages for this application
PostgreSQL 9.6
Java 7 or 8
Maven 3

NodeJs

# Database settings
Database: edm
User: edm
Password: n/a

After creating the database, import the dumped data located in data/edm.sql

# To run the webservices locally:
Webservices:
    mvn clean install -Prun-tomcat -DskipTests
URL: http://localhost:8088

# To run the front end locally
NodeJs must be installed. In directory frontend/ run "npm install" (one time run only)
    npm start - to run the front-end
URL: http://localhost:4200

