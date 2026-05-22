# University Management System

Spring Boot backend for a University Management System. The API manages users, roles, students, teachers, courses, departments, enrollments, file attachments, JWT authentication, role-based access, async reports, and OpenAPI documentation.

## Authors

- Tusupkali Miras
- Zhaugash Nurzhan

## Technologies

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT with JJWT
- PostgreSQL
- H2 for tests
- Jakarta Validation
- Swagger UI / OpenAPI
- Maven
- Docker and Docker Compose
- SLF4J and Logback

## Features

- Layered architecture with controllers, services, repositories, DTOs, mappers, entities, security, exception handling, file storage, and async services.
- CRUD APIs for students, teachers, courses, departments, and enrollments.
- Student listing supports pagination, sorting, search, department filtering, and course filtering.
- Registration and login with BCrypt password hashing and JWT bearer tokens.
- Role-based authorization for `ADMIN`, `TEACHER`, and `STUDENT`.
- File upload, metadata listing, download, and delete with local storage and PostgreSQL metadata.
- Async registration notifications, file processing, and student CSV export.
- Global error response DTO and validation error handling.
- Request, auth, CRUD, file, async, and error logging.
- Swagger UI at `/swagger-ui/index.html`.
- Dockerized app and PostgreSQL services with health checks and persistent volumes.

## Architecture

The code is organized under `kz.unm.tusupkalimiraszhaugashnurzhan`:

- `controller` - REST endpoints.
- `service` - business logic.
- `repository` - Spring Data JPA repositories.
- `dto` - request, response, page, auth, file, and error DTOs.
- `mapper` - manual DTO/entity mappers.
- `entity` - JPA entities and enums.
- `security` - JWT utility, filter, user details service, and security config.
- `exception` - custom exceptions and global handler.
- `service.file` - local file storage.
- `service.async` - asynchronous operations.
- `config` - application configuration, OpenAPI, async, logging filter, seed roles, and file properties.

All main project classes follow the required `TusupkaliMirasZhaugashNurzhan...` naming convention.

## API Documentation

Run the app and open:

```text
http://localhost:8080/swagger-ui/index.html
```

JWT can be entered in Swagger using the `bearerAuth` authorization option.

## Run Locally

Start PostgreSQL locally, then run:

```bash
mvn spring-boot:run
```

Or build and run the jar:

```bash
mvn clean package
java -jar target/university-management-system-0.0.1-SNAPSHOT.jar
```

## Run With Docker

```bash
docker compose up --build
```

Stop services:

```bash
docker compose down
```

Remove volumes too:

```bash
docker compose down -v
```

## Environment Variables

| Variable | Default | Description |
| --- | --- | --- |
| `SERVER_PORT` | `8080` | Application port |
| `DB_HOST` | `localhost` | PostgreSQL host |
| `DB_PORT` | `5432` | PostgreSQL port |
| `DB_NAME` | `unm` | Database name |
| `DB_USERNAME` | `unm_user` | Database username |
| `DB_PASSWORD` | `unm_password` | Database password |
| `JWT_SECRET` | development secret | JWT signing secret, change in production |
| `JWT_EXPIRATION` | `86400000` | Token lifetime in milliseconds |
| `UPLOAD_DIR` | `uploads` | Local file storage directory |
| `MAX_FILE_SIZE_BYTES` | `10485760` | Max upload size |
| `LOG_PATH` | `logs` | Log file directory |

## Example Requests

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin1","email":"admin1@unm.test","password":"Password123","fullName":"Admin User","role":"ADMIN"}'
```

Login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"admin1","password":"Password123"}'
```

List students with pagination, sorting, search, and filtering:

```bash
curl "http://localhost:8080/api/students?page=0&size=10&sortBy=lastName&direction=asc&search=mir&departmentId=1" \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

Create a course:

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"code":"CS101","title":"Java Programming","description":"Core Java and Spring foundations","credits":5,"semester":"Fall","startDate":"2026-09-01","endDate":"2026-12-20","teacherId":1}'
```

Upload a file:

```bash
curl -X POST "http://localhost:8080/api/files/upload?courseId=1" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -F "file=@syllabus.pdf"
```

Export students asynchronously:

```bash
curl "http://localhost:8080/api/reports/students/export?search=mir" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -o students-export.csv
```

## Security

- Public endpoints: `/api/auth/register`, `/api/auth/login`, `/api/health`, `/swagger-ui/**`, and `/v3/api-docs/**`.
- Protected endpoints require `Authorization: Bearer <JWT_TOKEN>`.
- `ADMIN` can manage students, teachers, courses, departments, enrollments, and files.
- `TEACHER` can view students and manage course/enrollment/file-related data.
- `STUDENT` can view students, teachers, departments, courses, and enrollments.
- Passwords are stored with BCrypt.
- Roles are seeded automatically on startup. No fixed user credentials are seeded; register a user first.

## Tests

```bash
mvn test
```

The test suite includes:

- Spring Boot context smoke test.
- Auth service registration and login tests.
- MockMvc controller/security test proving student endpoints require JWT and accept valid tokens.

## Commit History

The `final-project-development` branch was built through 25 meaningful commits. The history follows a real development sequence: project initialization, configuration, package structure, entities, repositories, DTOs, mappers, services, controllers, validation, exceptions, authentication, JWT, security, file storage, async processing, Swagger, logging, Docker, Compose, tests, and final documentation.
