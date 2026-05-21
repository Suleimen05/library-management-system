# Project Context

## Student

Yeldossuly Suleimen

## Project Topic

Library Management System backend application.

The project is a minimal but complete Spring Boot backend for managing a library. It is designed to satisfy the final project requirements without making the code unnecessarily large or difficult to explain during defense.

## Main Goal

Build a backend application with:

- REST API
- layered architecture
- PostgreSQL
- DTO classes and mappers
- validation
- exception handling
- pagination, sorting, search, filtering
- Spring Security with JWT
- role-based authorization
- file upload and download
- async processes
- Swagger UI
- request/error/action logging
- Dockerfile and docker-compose
- meaningful Git commit history

## Architecture

The project follows a simple layered architecture:

- `controller` - receives HTTP requests and returns responses.
- `service` - contains business logic.
- `repository` - works with the database through Spring Data JPA.
- `entity` - JPA database models.
- `dto` - request and response objects.
- `mapper` - converts entities to DTOs and DTOs to entities.
- `security` - JWT, authentication, authorization.
- `exception` - custom exceptions and global error handler.
- `config` - application configuration.

## Naming Convention

Project-specific classes use the student name prefix:

- `YeldossulySuleimenBookController`
- `YeldossulySuleimenBookService`
- `YeldossulySuleimenBookMapper`
- `YeldossulySuleimenJwtUtil`
- `YeldossulySuleimenGlobalExceptionHandler`

Repository and entity names stay simple and standard:

- `Book`
- `User`
- `BookRepository`
- `UserRepository`

This keeps the code readable while still satisfying the requirement to include the student's name in class names.

## Tech Stack

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- H2 for tests
- Lombok
- Jakarta Bean Validation
- JWT
- Swagger/OpenAPI
- Docker
- Docker Compose
- Maven Wrapper

## Domain Model

The project uses 8 entities:

- `User` - library system user.
- `Role` - user role for authorization.
- `Book` - library book.
- `Author` - book author.
- `Category` - book category or genre.
- `Borrowing` - record of a borrowed book.
- `Reservation` - record of reserved book.
- `FileResource` - uploaded file metadata, for example book cover or document.

Enums:

- `UserRole`
  - `ADMIN`
  - `LIBRARIAN`
  - `READER`
- `BorrowingStatus`
  - `BORROWED`
  - `RETURNED`
  - `OVERDUE`
- `ReservationStatus`
  - `ACTIVE`
  - `COMPLETED`
  - `CANCELLED`

## Roles

`ADMIN`

- Can manage everything.
- Can delete books.
- Can access admin-level endpoints.

`LIBRARIAN`

- Can create and update books, authors, categories.
- Can manage borrowings and reservations.
- Can upload book files.

`READER`

- Can view books.
- Can borrow books.
- Can reserve books.
- Can view own borrowings and reservations.

## Main REST Endpoints

Book endpoints:

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

Pagination/search/filter/sort endpoint:

- `GET /api/books?page=0&size=10&sort=title&direction=asc&search=java&categoryId=1&available=true`

Author endpoints:

- `GET /api/authors`
- `GET /api/authors/{id}`
- `POST /api/authors`
- `PUT /api/authors/{id}`
- `DELETE /api/authors/{id}`

Category endpoints:

- `GET /api/categories`
- `GET /api/categories/{id}`
- `POST /api/categories`
- `PUT /api/categories/{id}`
- `DELETE /api/categories/{id}`

Borrowing endpoints:

- `POST /api/books/{bookId}/borrow`
- `PUT /api/borrowings/{id}/return`
- `GET /api/users/me/borrowings`

Reservation endpoints:

- `POST /api/books/{bookId}/reserve`
- `PUT /api/reservations/{id}/cancel`
- `GET /api/users/me/reservations`

Authentication endpoints:

- `POST /api/auth/register`
- `POST /api/auth/login`

File endpoints:

- `POST /api/books/{bookId}/files`
- `GET /api/books/{bookId}/files`
- `GET /api/files/{id}/download`

## Required Criteria Mapping

Minimum 5-6 entities:

- Done with 8 entities.

Controller-service-repository architecture:

- Implemented and continued for each feature.

RESTful endpoints:

- GET, POST, PUT, DELETE are implemented through CRUD endpoints.

Path parameters:

- Example: `/api/books/{id}`

Query parameters:

- Example: `/api/books?page=0&size=10&sort=title&search=java`

PostgreSQL:

- Main application uses PostgreSQL through `application.properties`.

DTO classes:

- Request and response DTOs are used.

Mappers:

- Manual mapper classes are used for clarity.

Validation:

- DTO fields use Jakarta validation annotations.

Exception handling:

- Done with custom exceptions and a global exception handler.

Pagination, sorting, search, filtering:

- Done for `GET /api/books`.

Spring Security:

- Done with registration, login, JWT, protected endpoints, and role checks.

File upload/download:

- Done with `FileResource` entity, upload endpoint, file list endpoint, and download endpoint.

Async processes:

- Planned with `@Async` and `CompletableFuture`.

Swagger UI:

- Planned with Springdoc OpenAPI.

Logging:

- Planned with request logging filter/interceptor and service action logs.

Docker:

- Planned with Dockerfile, docker-compose, PostgreSQL service, health check.

Commit history:

- The project is developed through meaningful commits.

## Development Strategy

The project should be minimal but strong.

This means:

- Avoid complex patterns that are hard to explain.
- Use simple service methods.
- Use manual mappers instead of MapStruct.
- Keep endpoints clear and RESTful.
- Prioritize visible final-project requirements.
- Avoid overengineering.
- Make each commit match one understandable development step.

## Complexity Level

Overall complexity: medium.

Reason:

- CRUD and DTO mapping are simple.
- Pagination/search/filtering is medium.
- JWT security is medium.
- File upload/download is medium.
- Docker and async are easy to medium if implemented compactly.

The project is intentionally designed to be explainable by a student during defense.

## Commit Plan

1. `Initialize Library Management System project`
   - Create Spring Boot project.
   - Add Maven Wrapper.
   - Add PostgreSQL config.
   - Add README.
   - Add H2 test profile.

2. `Add domain entities and enums`
   - Add User, Role, Book, Author, Category, Borrowing, Reservation, FileResource.
   - Add UserRole, BorrowingStatus, ReservationStatus.

3. `Add repositories`
   - Add Spring Data JPA repositories for all entities.
   - Add useful finder methods.

4. `Add DTOs and mapper classes`
   - Add request/response DTOs.
   - Add validation annotations.
   - Add manual mapper classes.

5. `Implement book CRUD`
   - Add book service.
   - Add book controller.
   - Implement GET, POST, PUT, DELETE.
   - Use path parameters.
   - Use DTOs and mapper.

6. `Add pagination search sorting and filtering`
   - Improve `GET /api/books`.
   - Add page, size, sort, direction query parameters.
   - Add search by title/isbn/author.
   - Add filter by category and availability.

7. `Implement author and category CRUD`
   - Add services and controllers for authors.
   - Add services and controllers for categories.
   - Use DTOs and validation.

8. `Add borrowing and reservation logic`
   - Add borrow book endpoint.
   - Add return book endpoint.
   - Add reserve book endpoint.
   - Add cancel reservation endpoint.
   - Update available book copies.

9. `Add validation and exception handling`
   - Add custom exceptions.
   - Add `YeldossulySuleimenGlobalExceptionHandler`.
   - Return clean error responses.
   - Handle validation errors.

10. `Add user registration`
    - Implement user registration.
    - Encode passwords.
    - Assign default READER role.

11. `Add JWT authentication`
    - Add JWT utility class.
    - Add login endpoint.
    - Return JWT token after successful authentication.

12. `Add role based security`
    - Protect endpoints with roles.
    - Add security filter chain.
    - Configure public and protected endpoints.

13. `Add file upload and download`
    - Add file storage service.
    - Add upload endpoint for book files.
    - Add download endpoint.
    - Save file metadata in database.

14. `Add async services`
    - Enable async processing.
    - Add async process after registration.
    - Add async file processing.
    - Add async report/demo task with `CompletableFuture`.

15. `Add Swagger documentation`
    - Add Springdoc dependency.
    - Configure Swagger UI.
    - Add basic API metadata.

16. `Add request logging`
    - Log incoming requests.
    - Log errors.
    - Log important service actions.

17. `Add Dockerfile and docker compose`
    - Add multistage Dockerfile.
    - Add docker-compose with app and PostgreSQL.
    - Add health check.
    - Add useful environment variables.

18. `Final cleanup and README update`
    - Update README with project description.
    - Add run instructions.
    - Add endpoint list.
    - Add default roles explanation.
    - Verify tests.

## Current Progress

Already committed:

1. `Initialize Library Management System project`
2. `Add domain entities and enums`
3. `Add repositories`
4. `Add DTOs and mapper classes`
5. `Implement book CRUD`
6. `Add project context plan`
7. `Add pagination search sorting and filtering`
8. `Implement author and category CRUD`
9. `Add borrowing and reservation logic`
10. `Add validation and exception handling`
11. `Add user registration`
12. `Add JWT authentication`
13. `Add role based security`

Current code step:

- Step 13 from the main plan: `Add file upload and download`
- Added `YeldossulySuleimenFileStorageService`
- Added `YeldossulySuleimenFileController`
- Implemented `POST /api/books/{bookId}/files`
- Implemented `GET /api/books/{bookId}/files`
- Implemented `GET /api/files/{id}/download`
- Saved uploaded file metadata in `FileResource`
- Added `app.file.upload-dir`
- Ignored local `uploads/` folder in Git.
- Protected file upload for `LIBRARIAN` and `ADMIN`.

Next planned step after commit:

- Step 14: `Add async services`
- Enable async processing.
- Add 2-3 compact async methods using `@Async` and `CompletableFuture`.
- Keep the async examples simple and easy to explain.

Latest verification:

- `./mvnw.cmd test`
- Result: `BUILD SUCCESS`

## Defense Explanation

Short explanation:

This is a Library Management System backend. It uses layered architecture with controllers, services, and repositories. The system manages books, authors, categories, users, borrowing records, reservations, roles, and uploaded files. It uses DTO classes and mapper classes to avoid exposing entities directly. PostgreSQL is used as the main database, while H2 is used only for tests. Security will include registration, login, JWT tokens, and role-based endpoint protection.

## Why This Project Is Minimal But Complete

The project avoids unnecessary features like complicated admin dashboards, email providers, external storage services, or advanced reporting.

Instead, it focuses directly on the final project requirements:

- enough entities
- CRUD endpoints
- search and pagination
- JWT security
- file upload/download
- async examples
- Swagger
- Docker
- clean commits

This gives a strong final project without making it too large to finish or explain.
