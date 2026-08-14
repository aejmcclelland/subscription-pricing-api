# Subscription Pricing API

A small Spring Boot backend for calculating subscription pricing.

This project demonstrates clean first-version backend fundamentals while improving my Spring Boot skills. It focuses on a lean controller with request/response DTOs, global error handling, a clear controller and service structure, pricing rules, validation, and automated testing.


## Learning Goals

- Build a small REST API with Spring Boot.
- Use request and response DTOs to define the API contract.
- Separate HTTP handling in the controller from pricing logic in the service.
- Model fixed domain values with enums.
- Validate request data with Bean Validation.
- Return consistent structured error responses using global exception handling.
- Test service logic with unit tests.
- Test controller behaviour with MockMvc and a mocked service.

## Tech Stack
- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Bean Validation
- Maven Wrapper
- JUnit and MockMvc

## Project Structure

```
subscription-pricing-api/
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/andrew/subscription_pricing_api
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── error
│   │   │   ├── model
│   │   │   └── service
│   │   └── resources
│   └── test
│       └── java/com/andrew/subscription_pricing_api
│           ├── controller
│           └── service
└── mvnw
```
### Running Tests
```
./mvnw clean test
```
### Running Locally
```
./mvnw spring-boot:run
```
By default, the API runs on:

http://localhost:8080

### API Endpoints

| Method |Path                         |Purpose                     |
| -------|-----------------------------|----------------------------|
| POST   |```/api/subscriptions/calculate``` |Calculate subscription pricing|


### Supported Currency 
```
GBP
```
### Example Create Request
```json
{
    "userCount": 12,
    "plan": "PRO",
    "billingCycle": "MONTHLY",
    "currency": "GBP"
}
```
### Example Response
```json
{
    "plan": "PRO",
    "userCount": 12,
    "billingCycle": "MONTHLY",
    "monthlyCost": 240,
    "annualCost": 2880,
    "currency": "GBP"
}
```
## Business Rules

- ```BASIC``` costs £10 per user per month.
- ```PRO``` costs £20 per user per month.
- ```ENTERPRISE``` costs £40 per user per month.
- Annual cost is calculated as monthly cost × 12.
- User count must be at least 1.
- Plan, billing cycle, and currency are required.
- ```GBP``` is currently the only supported currency.

## Error Handling

- Error handling uses ```GlobalExceptionHandler``` to catch exceptions and create an instance of the ```ApiErrorResponse``` record with the appropriate values. The record defines a consistent structure for API errors. Jackson then serialises the Java object into JSON to create the HTTP response returned to the client.

- For example, an unsupported currency such as ```USD``` results in the following structured error response: 

### Example Invalid Request 

```json
{
    "userCount": 12,
    "plan": "PRO",
    "billingCycle": "MONTHLY",
    "currency": "USD"
}
```

### Example Error Response 
```json
{
    "message": "Invalid request body",
    "status": 400,
    "timestamp": 1786726284091,
    "field": "currency",
    "error": "Invalid currency value. Supported currency is GBP."
}
```

## Testing

The project contains tests at both the service and controller layers.

### Service Tests

Service unit tests verify the pricing calculations for the supported subscription plans and billing cycles, including the currency returned by the service.

### Controller Tests

Controller tests use `MockMvc` with a mocked `SubscriptionPricingService` to verify:

- successful requests return HTTP 200 and the expected JSON response;
- invalid user counts return HTTP 400;
- missing required fields return HTTP 400;
- unsupported currencies return a structured HTTP 400 response;
- invalid requests do not reach the pricing service.

### Application Context Test

A Spring Boot context test verifies that the application context starts successfully.


