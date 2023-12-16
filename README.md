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

## Project Structure

```bash
.
├── report # -> reports including external api test and style report
├── .github/workflows
├── web-app # front-end code
├── web-service
│   └── src # -> source code
│       ├── main # -> source code
│       │   ├── java/com/example/webservice # -> spring boot service codes
│       │   │   ├── controller # -> controllers
│       │   │   ├── model # -> models
│       │   │   ├── repository # -> repositories
│       │   │   ├── service # -> services
│       │   │   └── WebServiceApplication.java # -> spring boot application
│       │   └── resources # -> properties configuration
│       │       └── application.properties # -> spring boot properties
│       └── test/java/com/example/webservice # -> tests
│           ├── controller # -> tests for controller
│           ├── model/type # -> tests for model/type
│           ├── service # -> tests for services
│           └── WebServiceApplicationTests.java # -> spring boot application
├── sql # -> sql files for database backup
└── README.md
```

## Build

- git clone the repository
- set up environment dependencies (install `maven` and `java 17` first):

```
# Direct to project folder
cd web-service

# Cleans the project and removes all files generated by the previous build
mvn clean

# Install dependencies specified in pom
mvn install -Dmaven.test.skip=true
```

## Run

- Contact any team member to add IP address to AWS RDS
- run the service on `localhost:8080`:

```
mvn spring-boot:run
```

- read the API documentation after running the service: http://localhost:8080/swagger-ui/index.html#/

## Test

- run tests for the service:

```
mvn test
```

## Endpoints

- `POST /client/create`

  - Description:
    Creates a client, if it is a distributor client, a new facitliy is also created and associated with the client (each client associated with one facility)
  - Request Body:
    - Fields:
      - `authentication:string`
      - `type:string`
    - Example:
      ```
        {
        "authentication": "string",
        "type": "string"
        }
      ```
  - Response Codes:
    - `200: Success`
    - `400: Invalid Input`

- `DELETE /client/delete/{id}`

  - Description
    Deletes a client from database, the associated facility and listings will be deleted as well
  - Parameters:
    - path: `id:integer`
    - query: `authentication:string`
  - Response Codes:
    - `200: Success`
    - `401: Invalid authentication code`
    - `404: Client does not exist`

- `GET /facility`

  - Description:
    Retrive list of all public facilities
  - Response Codes:
    - `200: Success`

- `GET /facility/by_client/{client_id}`

  - Description:
    Retrieve information on facility with client_id
  - Parameters:
    - path: `client_id:integer`
    - query: `auth:string`
  - Response Codes:
    - `200: Success`
    - `401: Invalid authentication code`
    - `404: Client does not exist`

- `GET /facility/{id}`

  - Description:
    Retrieve information on facility with certain ID
  - Parameters:
    - path: `id:integer`
  - Response Codes:
    - `200: Success`
    - `401: Invalid Token`

- `POST /facility/update/{facilityID}`

  - Description:
    Create facility info, authentication of the client is needed
  - Parameters:
    - query: `clientID:integer`
    - query: `auth:string`
    - Request Body:
      - Fields:
        - `latitude:number`
        - `longitude:number`
        - `email:string`
        - `phone:string`
        - `hours:string`
        - `public:boolean`
      - Example:
        ```
        {
        "latitude": 0,
        "longitude": 0,
        "email": "string",
        "phone": "string",
        "hours": "string",
        "public": true
        }
        ```
  - Response Codes:
    - `200: Success`
    - `400: Bad Request - invalid phone`
    - `400: Bad Request - invalid email`
    - `401: Invalid Token`
    - `401: unauthorized - prevent non distributor client from creating facility`

- `PUT /facility/update/{facilityID}`

  - Description:
    Update facility info, authentication of the client is needed
  - Parameters:
    - path: `facilityID:integer`
    - query: `clientID:integer`
    - query: `auth:string`
    - Request Body:
      - Fields:
        - `latitude:number`
        - `longitude:number`
        - `email:string`
        - `phone:string`
        - `hours:string`
        - `public:boolean`
      - Example:
        ```
        {
        "latitude": 0,
        "longitude": 0,
        "email": "string",
        "phone": "string",
        "hours": "string",
        "public": true
        }
        ```
  - Response Codes:
    - `200: Success`
    - `400: Bad Request - invalid phone`
    - `400: Bad Request - invalid email`
    - `400: Invalid Input`
    - `401: Invalid Token: facility ID`

- `DELETE /facility/delete/{facilityID}`

  - Description:
    Deletes facility and associated listings
  - Parameters:
    - query: `clientID:integer`
    - query: `auth:string`
    - path:`id:integer`
  - Response Codes:
    - `204: Success`
    - `401: unauthorized`
    - `404: not found`

- `GET /listing/search_with_filter`

  - Description:
    Get listings using filter
  - Parameters:
    - query: `latitude:number`
    - query: `longitude:number`
    - query: `range:number`
    - query: `item_list:string`
    - query: `age:integer`
    - query: `veteran_status:boolean`
    - query: `gender:string`
  - Response Codes:
    - `200: Success`

- `GET /listing/search_with_group_code`

  - Description:
    Get listings using group code
  - Parameters:
    - query: `latitude:number`
    - query: `longitude:number`
    - query: `range:number`
    - query: `group_code:integer`
  - Response Codes:
    - `200: Success`

- `GET /listing/by_client/{client_id}`

  - Description:
    Get listings using client id
  - Parameters:
    - path: `client_id:integer`
  - Response Codes:
    - `200: Success`

- `GET /listing/{id}`

  - Description:
    Retrieve information on listing with certain ID
  - Parameters:
    - path: `id:integer`
  - Response Codes:
    - `200: Success`
    - `404: Invalid Token`

- `POST /listing/create`

  - Description:
    Creates a listing, authentication of the client is needed
  - Parameters:
    - query: `clientID:integer`
    - query: `auth:string`
    - query: `facilityID:string`
  - Request Body:
    - Fields:
      - `isPublic:boolean`
      - `groupCode:integer`
      - `itemList:string`
      - `ageRequirement:integer`
      - `veteranStatus:boolean`
      - `gender:string`
    - Example:
      ```
      {
      "isPublic": true,
      "groupCode": 0,
      "itemList": "string",
      "ageRequirement": 0,
      "veteranStatus": true,
      "gender": "string"
      }
      ```
  - Response Codes:
    - `200: Success`
    - `400: Invalid Input`
    - `404: Invalid Client ID or authentication`

- `PUT /listing/update/{id}`

  - Description:
    Update listing info, authentication of the client is needed
  - Parameters
    - path:`id:integer`
    - query: `clientID:integer`
    - query: `auth:string`
  - Request Body:

    - Fields
      - `isPublic:boolean`
      - `groupCode:integer`
      - `itemList:string`
      - `ageRequirement:integer`
      - `veteranStatus:boolean`
      - `gender:string`
    - Example:
      ```
      {
      "isPublic": true,
      "groupCode": 0,
      "itemList": "string",
      "ageRequirement": 0,
      "veteranStatus": true,
      "gender": "string"
      }
      ```

  - Response Codes:
    - `200: Success`
    - `400: Invalid Input`
    - `404: Invalid Client ID or authentication`

- `DELETE /listing/delete/{id}`
  - Description:
    Deletes a listing
  - Parameters:
    - query: `clientID:integer`
    - query: `auth:string`
    - path:`id:integer`
  - Response Codes:
    - `204: Success`
    - `401: Invalid Client ID or authentication`
    - `404: Listing not found`

# Client App

This is a React web application built with Vite and TypeScript.

## Prerequisites

Make sure you have Node.js and npm installed on your machine.

- [Node.js](https://nodejs.org/) (version 18+)
- [npm](https://www.npmjs.com/)

## Installation

1. Clone this repository:

```bash
  cd web-app
```

2. Install dependencies:

```bash
  npm install
```

## Development

To run the application in development mode, use the following command:

```bash
  npm run dev
```

This will start the development server, and you can view the application at http://localhost:5173/
