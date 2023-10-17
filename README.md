# User and Todo Services
This project consists of two microservices: User Service and Todo Service. It allows you to manage users and their associated tasks.


## Table of Contents
#### •	 Introduction
#### •	Project Structure
#### •	Getting Started
#### •	User Service
#### •	Todo Service
#### •	Eureka Service
#### •	API Documentation
#### •	Circuit Breaker and Fallback
#### •	Error Handling

## Introduction
Welcome to the User and Todo Service Integration project! This document provides an in-depth overview of the microservices architecture used for integrating the User Service and Todo Service. This project leverages the following technologies:
#### •	Spring Boot
#### •	Java 11
#### •	Feign Client
#### •	Redis Cache
#### •	Resilience4j Circuit Breaker
#### •	MySQL Database
#### •	Service Registry & Discovery (Netflix Eureka)



## Project Structure
The project is structured as follows:
#### •	user-service: User Service microservice.
#### •	todo-service: Todo Service microservice.
#### •	eureka-service: Eureka Service for service registration and discovery.

## Getting Started
### User Service
1.	Clone the User Service repository.
2.	Configure the database connection in user-service/src/main/resources/application.properties
3.	Run service.

### Todo Service
1.	Clone the Todo Service repository.
2.	Configure the database connection in todo-service/src/main/resources/application.properties.
3.	Run the Todo Service:

### Eureka Service
1.	Clone the Eureka Service repository.
2.	Configure the Eureka server in eureka-service/src/main/resources/application.properties.
3.	Run the Eureka Service:

### Circuit Breaker and Fallback
The services use a circuit breaker pattern with a fallback mechanism. In case the Todo Service is down or experiences issues, the todoServiceFallback method is triggered.

### Redis Cache
Redis Cache is employed in this architecture to enhance data retrieval and reduce database load. It offers efficient caching of frequently accessed data, improving system performance.


### Error Handling
The API returns appropriate status codes and error messages to handle various scenarios.
•	200 OK: Successful requests.
•	201 Created: A new resource has been successfully created.
•	400 Bad Request: Invalid request parameters.
•	404 Not Found: Resource not found.

 
