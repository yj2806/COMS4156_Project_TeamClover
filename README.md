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

## Endpoints
- `POST /client/create`
  - Description:
    Creates a client, if it is a distributor client, a new facitliy is also created and associated with the client (each client associated with one facility)
  - Request Body:
    - `authentication:string`    
    - `type:string`
  - Response Codes:
    - ```200: Success```
    - ```400: Invalid Input```
    - ```409: Developer already exists```

- `DELETE /client/delete/{id}`
  - Description
    Deletes a client from database, the associated facility and listings will be deleted as well
  - Request Body:
    - `id:int`    
    - `authentication:string`
  - Response Codes:
    - ```200: Success```
    - ```401: Invalid authentication code```
    - ```404: Client does not exist```
    - ```500: Internal Server Error```

- `GET /facility`
  - Description:
    Retrive list of all facilities
  - Response Codes:
    - ```200: Success```

- `GET /facility/{id}`
  - Description:
    Retrieve information on facility with certain ID
  - Request Body:
    - `id:int`
  - Response Codes:
    - ```200: Success```
    - ```401: Invalid Token```

- `PUT /facility/update/{id}`
  - Description:
    Update facility info, authentication of the client is needed
  - Request Body:
    - `clientID:int`
    - `authentication:string` 
    - `facilityID:int`
    - `latitude:float`
    - `longitude:float`
    - `isPublic:boolean`
    - `email:string`
    - `phone:string`
    - `hours:string`
  - Response Codes:
    - ```200: Success```
    - ```401: Invalid Token: facility ID```
    - ```404: Invalid Client ID or authentication```
   
- `GET /listings/search`
  - Description:
    Get all listings (To-Do: search by distance in next iteration)
  - Response Codes:
    - ```200: Success```
      
- `GET /listings/`
  - Description:
    Get all listings
  - Response Codes:
    - ```200: Success```

- `GET /listings/{id}`
  - Description:
    Retrieve information on listing with certain ID
  - Response Codes:
    - ```200: Success```
    - ```401: Invalid Token```
   
- `POST /listings/create`
  - Description:
    Creates a listing, authentication of the client is needed
  - Request Body:
    - `clientID:int`  
    - `authentication:string`    
    - `isPublic:boolean`
    - `groupCode:int`  
    - `itemList:string`    
    - `ageRequirement:Integer`
    - `veteranStatus:boolean`  
    - `gender:string`
  - Response Codes:
    - ```200: Success```
    - ```400: Invalid Input```
    - ```404: Invalid Client ID or authentication```
   
- `PUT /listings/update/{id}`
  - Description:
    Update listing info, authentication of the client is needed
  - Request Body:
    - `clientID:int`  
    - `authentication:string`    
    - `isPublic:boolean`
    - `groupCode:int`  
    - `itemList:string`    
    - `ageRequirement:Integer`
    - `veteranStatus:boolean`  
    - `gender:string`
  - Response Codes:
    - ```200: Success```
    - ```400: Invalid Input```
    - ```404: Invalid Client ID or authentication```

- `DELETE /listings/delete/{id}`
  - Description:
    Deletes a listing
  - Request Body:
    - `listingID:int`    
    - `clientID:int`  
    - `authentication:string`  
  - Response Codes:
    - ```200: Success```
    - ```401: Invalid Client ID or authentication```
    - ```500: Internal Server Error```
