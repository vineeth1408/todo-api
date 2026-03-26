# Todo API

A RESTful Todo List API built with **Spring Boot 3.2** and **Java 17**, featuring full CRUD operations, OpenAPI/Swagger documentation, and an AI-orchestrated development workflow powered by GitHub Copilot custom agents.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Spring Boot | 3.2.0 | Framework |
| Spring Data JPA | - | Data access layer |
| H2 Database | - | In-memory database |
| Spring Validation | - | Request validation |
| Lombok | 1.18.30 | Boilerplate reduction |
| Springdoc OpenAPI | 2.3.0 | Swagger documentation |
| JUnit 5 + Mockito | - | Unit testing |

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.x

### Build & Run

```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package

# Start the server
mvn spring-boot:run
```

The application starts on **`http://localhost:8080`**

### Useful URLs

| URL | Description |
|---|---|
| `http://localhost:8080/api/todos` | REST API base |
| `http://localhost:8080/swagger-ui.html` | Swagger UI |
| `http://localhost:8080/v3/api-docs` | OpenAPI JSON spec |
| `http://localhost:8080/h2-console` | H2 Database Console |

---

## API Endpoints

### Base URL: `/api/todos`

| Method | Endpoint | Description | Status Code |
|---|---|---|---|
| `GET` | `/api/todos` | Retrieve all todos | `200 OK` |
| `GET` | `/api/todos/{id}` | Retrieve a todo by ID | `200 OK` / `404 Not Found` |
| `POST` | `/api/todos` | Create a new todo | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/todos/{id}` | Update an existing todo | `200 OK` / `400 Bad Request` / `404 Not Found` |
| `DELETE` | `/api/todos/{id}` | Delete a todo by ID | `204 No Content` / `404 Not Found` |

---

## Request & Response Examples

### Create Todo — `POST /api/todos`

**Request:**
```json
{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs"
}
```

**Response — `201 Created`:**
```json
{
  "success": true,
  "message": "Todo created successfully",
  "data": {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, bread, eggs",
    "completed": false,
    "createdAt": "2026-03-26T10:00:00",
    "updatedAt": "2026-03-26T10:00:00"
  }
}
```

---

### Get All Todos — `GET /api/todos`

**Response — `200 OK`:**
```json
{
  "success": true,
  "message": "Todos retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "Buy groceries",
      "description": "Milk, bread, eggs",
      "completed": false,
      "createdAt": "2026-03-26T10:00:00",
      "updatedAt": "2026-03-26T10:00:00"
    }
  ]
}
```

---

### Update Todo — `PUT /api/todos/{id}`

**Request:**
```json
{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs, cheese",
  "completed": true
}
```

**Response — `200 OK`:**
```json
{
  "success": true,
  "message": "Todo updated successfully",
  "data": {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, bread, eggs, cheese",
    "completed": true,
    "createdAt": "2026-03-26T10:00:00",
    "updatedAt": "2026-03-26T11:00:00"
  }
}
```

---

### Error Response Format

All errors follow a consistent structure returned by the global exception handler:

```json
{
  "timestamp": "2026-03-26T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Todo not found with id: 99",
  "path": "/api/todos/99"
}
```

---

## Project Structure

```
src/
└── main/
    ├── java/com/learning/todoapi/
    │   ├── TodoApiApplication.java          # Entry point
    │   ├── controller/
    │   │   └── TodoController.java          # REST endpoints
    │   ├── service/
    │   │   ├── TodoService.java             # Service interface
    │   │   └── TodoServiceImpl.java         # Business logic
    │   ├── repository/
    │   │   └── TodoRepository.java          # JPA repository
    │   ├── entity/
    │   │   └── Todo.java                    # JPA entity
    │   ├── dto/
    │   │   ├── TodoRequestDto.java          # Create/Update request
    │   │   ├── TodoResponseDto.java         # Response payload
    │   │   ├── ApiResponse.java             # Generic response wrapper
    │   │   └── ErrorResponse.java           # Error response structure
    │   ├── exception/
    │   │   ├── GlobalExceptionHandler.java  # Centralized error handling
    │   │   └── ResourceNotFoundException.java
    │   └── config/
    │       └── WebConfig.java               # CORS and web configuration
    └── resources/
        └── application.properties           # App configuration
```

---

## Todo Entity

```java
@Entity
public class Todo {
    Long id;          // Primary key, auto-generated
    String title;     // Required, max 100 characters
    String description; // Optional
    Boolean completed;  // Default: false
    LocalDateTime createdAt; // Auto-set on creation
    LocalDateTime updatedAt; // Auto-updated on modification
}
```

---

## AI-Orchestrated Development — .github/

This project features a **custom GitHub Copilot automation framework** that handles GitHub issues autonomously with minimal human interaction.

### How It Works

```
GitHub Issue
     │
     ├── Manual Path (~/20% human interaction)
     │       implement-issue.prompt.md
     │       Step 1: Fetch Issue
     │       Step 2: Show Plan → USER APPROVES
     │       Step 3: Implement (implementer agent)
     │       Step 4: Review (todo-reviewer agent)
     │       Step 5: Push & Close
     │
     └── Autonomous Path (~/0-5% human interaction)
             issue-handler agent
             [1] Fetch Issue
             [2] Plan (auto-approved)
             [3] Implement (implementer agent)
             [4] Test Gate — 100% pass required
             [5] Review Gate — approval required
             [6] Push to GitHub
             [7] Close issue + summary comment
```

### Customization Files

| File | Type | Purpose |
|---|---|---|
| `.github/copilot-instructions.md` | Instructions | Project-wide coding standards for all agents |
| `.github/agents/implementer.agent.md` | Agent | Writes production code from a plan |
| `.github/agents/todo-reviewer.agent.md` | Agent | Reviews code for standards compliance |
| `.github/agents/issue-handler.agent.md` | Agent | Orchestrates full 7-step issue resolution |
| `.github/agents/ci-cd-helper.agent.md` | Agent | GitHub Actions and CI/CD pipelines |
| `.github/prompts/implement-issue.prompt.md` | Prompt | Interactive 5-step manual workflow |

### Using the Workflows

**Interactive (with approval gate):**
```
User: "/implement-issue issue: 4"
# Copilot shows plan → waits for your approval → then proceeds
```

**Autonomous (fully automatic):**
```
User: "Use issue-handler for issue #5"
# Copilot fetches, implements, tests, reviews, pushes, and closes — no input needed
```

---

## Issues Resolved via AI Agents

| Issue | Title | Workflow Used | Tests | Status |
|---|---|---|---|---|
| #3 | Add OpenAPI / Swagger documentation | Manual prompt (with approval) | 13/13 ✅ | Closed |
| #2 | Return 204 No Content on DELETE | Autonomous orchestrator | 11/11 ✅ | Closed |

---

## Running Tests

```bash
mvn test
```

Tests cover:
- All 5 CRUD endpoints (success and error paths)
- 404 Not Found responses
- 400 Bad Request for invalid input
- 204 No Content for DELETE

---

## Configuration

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

> The H2 in-memory database resets on every application restart.

---

## Coding Conventions

- **Lombok** for boilerplate (`@Data`, `@RequiredArgsConstructor`, etc.)
- **Constructor injection** for all dependencies
- **DTOs** for request/response — entities never exposed directly
- **GlobalExceptionHandler** (`@ControllerAdvice`) for consistent error responses
- **REST semantics** — correct HTTP status codes per operation
- **OpenAPI annotations** on all endpoints and DTOs

---

## License

This project is for learning purposes.

