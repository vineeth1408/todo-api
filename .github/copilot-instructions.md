# Copilot Instructions for Todo API Project

## Project Overview and Purpose

This is a Spring Boot application for managing a simple Todo list API. It provides REST endpoints for creating, reading, updating, and deleting todo items, with data persistence using H2 database.

## Tech Stack

- Java 17
- Spring Boot 3.2
- Maven
- H2 Database
- Spring Data JPA
- Lombok
- Spring Validation

## Package Structure

All code should be placed under the package `com.learning.todoapi`.

## Layered Project Structure

- `com.learning.todoapi.controller`: REST controllers for handling HTTP requests.
- `com.learning.todoapi.service`: Business logic services.
- `com.learning.todoapi.repository`: Data access layer with JPA repositories.
- `com.learning.todoapi.entity`: JPA entities representing database tables.
- `com.learning.todoapi.dto`: Data Transfer Objects for requests and responses.
- `com.learning.todoapi.exception`: Custom exceptions and global exception handlers.
- `com.learning.todoapi.config`: Configuration classes.

## Todo Entity Fields

The Todo entity should have the following fields:
- `id`: Long (primary key, auto-generated)
- `title`: String (required, max 100 characters)
- `description`: String (optional)
- `completed`: Boolean (default false)
- `createdAt`: LocalDateTime (auto-set on creation)
- `updatedAt`: LocalDateTime (auto-set on update)

## Coding Conventions

- Use Lombok annotations for boilerplate code (e.g., @Data, @AllArgsConstructor, @NoArgsConstructor).
- Prefer constructor injection for dependencies.
- Use DTOs for request and response objects to separate API contracts from entities.
- Implement a global exception handler for consistent error responses.
- Always add comments for complex logic to improve readability.

## API Style

- Follow REST principles.
- Use JSON for request and response bodies.
- Return standard HTTP status codes (e.g., 200 OK, 201 Created, 400 Bad Request, 404 Not Found, 500 Internal Server Error).

## REST API Endpoints

- `GET /api/todos`: Retrieve all todos (with optional pagination/query params).
- `GET /api/todos/{id}`: Retrieve a specific todo by ID.
- `POST /api/todos`: Create a new todo.
- `PUT /api/todos/{id}`: Update an existing todo.
- `DELETE /api/todos/{id}`: Delete a todo by ID.

## Request and Response JSON Examples

### Create Todo (POST /api/todos)
Request:
```json
{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs"
}
```

Response (201 Created):
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, bread, eggs",
  "completed": false,
  "createdAt": "2023-10-01T10:00:00",
  "updatedAt": "2023-10-01T10:00:00"
}
```

### Get All Todos (GET /api/todos)
Response (200 OK):
```json
[
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, bread, eggs",
    "completed": false,
    "createdAt": "2023-10-01T10:00:00",
    "updatedAt": "2023-10-01T10:00:00"
  }
]
```

### Update Todo (PUT /api/todos/{id})
Request:
```json
{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs, cheese",
  "completed": true
}
```

Response (200 OK):
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, bread, eggs, cheese",
  "completed": true,
  "createdAt": "2023-10-01T10:00:00",
  "updatedAt": "2023-10-01T11:00:00"
}
```

## Error Response Format

Use a global exception handler (@ControllerAdvice) to return consistent error responses.

Format:
```json
{
  "timestamp": "2023-10-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/todos"
}
```

Handle specific exceptions like:
- `ResourceNotFoundException` (404): When todo with given ID not found.
- `ValidationException` (400): For invalid input data.
- Generic `Exception` (500): For unexpected errors.

## Testing

- Write unit tests using JUnit 5.
- Use Mockito for mocking dependencies in unit tests.
- Aim for good test coverage, especially for service and controller layers.

## Build and Run Instructions

### Build the Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package the Application
```bash
mvn clean package
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`. API endpoints are available under `/api/todos`.