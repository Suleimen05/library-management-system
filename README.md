# Library Management System

Final Java Spring Boot backend project by Yeldossuly Suleimen.

## Project Overview

Library Management System is a REST API for managing books, authors, categories, users, borrowings, reservations, and uploaded book files. The project uses layered architecture: controller, service, repository, DTO, mapper, entity, security, exception, and config layers.

## Tech Stack

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- H2 for tests
- Lombok
- Jakarta Bean Validation
- Springdoc Swagger UI
- Docker and Docker Compose

## Main Features

- RESTful CRUD endpoints
- Path and query parameters
- Pagination, sorting, search, and filtering for books
- DTO classes and manual mappers
- Validation and global exception handling
- Registration and login
- JWT authentication
- Role-based authorization
- File upload and download
- Async processes with `@Async` and `CompletableFuture`
- Swagger UI documentation
- Request, error, and action logging
- Multistage Docker build
- Docker Compose with PostgreSQL, health checks, volumes, and log rotation

## Domain Entities

- `User`
- `Role`
- `Book`
- `Author`
- `Category`
- `Borrowing`
- `Reservation`
- `FileResource`

## Roles

- `ADMIN` - can delete and manage protected resources.
- `LIBRARIAN` - can create/update books, authors, categories, and upload files.
- `READER` - can borrow and reserve books.

New users registered through `/api/auth/register` receive the `READER` role by default.

## Run With Docker

Requirements:

- Docker
- Docker Compose

Start the project:

```bash
docker compose up --build
```

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Open OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

Stop containers:

```bash
docker compose down
```

Stop containers and remove database/upload volumes:

```bash
docker compose down -v
```

## Run Locally

Start PostgreSQL locally or through Docker Compose, then run:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Default local database settings are configured in `src/main/resources/application.properties`.

## Tests

Run tests:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

Tests use H2 database through `src/test/resources/application-test.properties`.

## Main API Endpoints

Authentication:

- `POST /api/auth/register`
- `POST /api/auth/login`

Books:

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

Book search with pagination, sorting, and filtering:

```text
GET /api/books?page=0&size=10&sort=title&direction=asc&search=java&categoryId=1&available=true
```

Authors:

- `GET /api/authors`
- `GET /api/authors/{id}`
- `POST /api/authors`
- `PUT /api/authors/{id}`
- `DELETE /api/authors/{id}`

Categories:

- `GET /api/categories`
- `GET /api/categories/{id}`
- `POST /api/categories`
- `PUT /api/categories/{id}`
- `DELETE /api/categories/{id}`

Borrowing:

- `POST /api/books/{bookId}/borrow`
- `PUT /api/borrowings/{id}/return`
- `GET /api/users/me/borrowings`

Reservations:

- `POST /api/books/{bookId}/reserve`
- `PUT /api/reservations/{id}/cancel`
- `GET /api/users/me/reservations`

Files:

- `POST /api/books/{bookId}/files`
- `GET /api/books/{bookId}/files`
- `GET /api/files/{id}/download`

Reports:

- `GET /api/reports/summary`

## Authentication Flow

1. Register a user with `POST /api/auth/register`.
2. Login with `POST /api/auth/login`.
3. Copy the JWT token from the response.
4. Use the token as:

```text
Authorization: Bearer <token>
```

In Swagger UI, click `Authorize` and paste the token.

## Docker Services

`docker-compose.yml` starts:

- `postgres` - PostgreSQL 16 database.
- `app` - Spring Boot application.

The application uses:

- `DB_URL=jdbc:postgresql://postgres:5432/library_management`
- `UPLOAD_DIR=/app/uploads`
- named volume `postgres-data`
- named volume `app-uploads`

## Project Structure

```text
src/main/java/com/yeldossuly/suleimen/librarymanagement
  config
  controller
  dto
  entity
  exception
  mapper
  repository
  security
  service
```

## Commit History

The project was developed with meaningful commits for each final-project requirement, from initialization to Docker and README cleanup.
