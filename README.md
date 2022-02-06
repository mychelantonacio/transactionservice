# Transaction Service


This is a spring boot application that exposes an API simulating financial transactions.

Tech Stack
- Spring Boot 2.6.3
- Spring Security 
- Spring Data
- Spring Actuator
- Java 11
- Maven 3.6.3
- JUnit 5 
- H2 in-memory DB 2


## To run the application
`mvn spring-boot:run`
  
## To run tests
`mvn test`


## Endpoints

GET  
`http://localhost:8080/api/v1/transactions`  
`http://localhost:8080/api/v1/transactions/{uuid}`

POST  
`http://localhost:8080/api/v1/transactions`

PUT  
`http://localhost:8080/api/v1/transactions/{uuid}`

DELETE  
`http://localhost:8080/api/v1/transactions/{uuid}`

Health Check  
`http://localhost:8080/actuator/health`

Metrics  
`http://localhost:8080/actuator/metrics`


## Examples of API calls
List All  
`curl -X GET http://localhost:8080/api/v1/transactions -H 'authorization: Basic YWRtaW46YWRtaW4='`

List by currency  
`curl -X GET http://localhost:8080/api/v1/transactions?currency=GBP -H 'authorization: Basic YWRtaW46YWRtaW4='`

List by status  
`curl -X GET http://localhost:8080/api/v1/transactions?status=FAILED -H 'authorization: Basic YWRtaW46YWRtaW4='`

Insert new transaction  
`curl -X POST http://localhost:8080/api/v1/transactions -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"amount": 2000.00,"currency": "USD","description": "Transaction Test 4"}'`
 
Update transaction  
`curl -X PUT 'http://localhost:8080/api/v1/transactions/9c2b0108-908f-4af5-a432-c49ffc0a8364' -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"amount": 2000.00,"currency": "USD","description": "Transaction Test 100"}'`

Delete transaction  
`curl -X DELETE 'http://localhost:8080/api/v1/transactions/9c2b0108-908f-4af5-a432-c49ffc0a8364' -H 'authorization: Basic YWRtaW46YWRtaW4='`


## Database (in-memory h2)
http://localhost:8080/h2-console

JDBC URL -> jdbc:h2:mem:transactiondb  
User Name -> sa  
PS there is no password, leave it blank.
