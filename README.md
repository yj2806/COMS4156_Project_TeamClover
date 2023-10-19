# COMS4156_Project_TeamClover
Project: COMS 4156 Course Project  
Team: TeamClover   

## Project Introduction
This project supports a service that facilitates care package search and listing. 
It provides an API for clients to query the database and retrieve information about available packages based on 
location. Additionally, clients can upload information about package availability to the database. 
The target clients for this service include apps designed for the elderly, apps for elder care organizations, 
homeless shelters, as well as apps for first responders and authorities. Through our service, 
clients can empower their end-users to create care package listings and contribute to community assistance.

## Tech Stack
- IDE: IntelliJ IDEA
- Cloud server: AWS EC2
- Back-end: SpringBoot 3.1.4
    - Java: 17
    - Database: AWS RDS - Mysql
    - Test: Junit

## Run the Service
- git clone the repository
- install maven and java 17
- open the folder of this repository
```
# Direct to project folder
cd web-service

# Install dependencies specified in pom
mvn install

# Run the service on `localhost:8080`
mvn spring-boot:run
```

## API Documentation
Attention: please run the service first  
http://localhost:8080/swagger-ui/index.html#/ 
