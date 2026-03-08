# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Run locally
mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=RankServiceTest

# Build JAR for production
mvn clean package

# Run packaged JAR
java -jar target/josefus-highscore.jar
```

## Required Environment Variables

```
SUPABASE_JOSEFUS_DB_URL     # PostgreSQL JDBC connection URL
SUPABASE_JOSEFUS_DB_USER    # Database username
SUPABASE_JOSEFUS_DB_PWD     # Database password
JOSEFUS_JWT_SECRET          # Base64-encoded HS256 secret for JWT signing
```

## Architecture

Spring Boot 3 REST API with layered architecture:

- **`controller/`** — REST endpoints. Three controllers: `AuthenticationController` (register/signin/refresh), `GameController` (score submission and ranking), `UserInfoController`.
- **`service/`** — Business logic. `JwtService` handles token generation/validation. `RankService` computes top-10 rankings using parallel streams. `AuthenticationService` coordinates signup, signin, and sign-in tracking.
- **`register/`** — JPA repositories (Spring Data). Named `*Register` rather than `*Repository`.
- **`model/`** — JPA entities mapped to the `josefushighscore` schema in PostgreSQL.
- **`dto/`** — Request/response DTOs with Jakarta Bean Validation annotations.
- **`exception/`** — Custom exception classes plus `GlobalExceptionHandler` (`@RestControllerAdvice`) that standardizes error responses.
- **`configure/`** — Spring Security config, JWT filter, CORS properties, and Flyway/JPA configuration.

## Security Model

JWT-based stateless auth. `JwtAuthenticationFilter` validates `Authorization: Bearer <token>` on every request and sets the `SecurityContext`. Tokens expire in 1 hour; refresh tokens in 7 days. Passwords are BCrypt-hashed. Method-level security uses `@PreAuthorize`/`@Secured`. Score ownership is enforced in the service layer before writes.

## Database

PostgreSQL (Supabase), schema `josefushighscore`. Flyway migrations run automatically on startup from `src/main/resources/db/migration/`. Migration files follow the naming `V{major}_{minor}_{patch}__{description}.sql`. Tests use an H2 in-memory database.

## API Documentation

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html` when running locally. OpenAPI spec at `/v3/api-docs`.

## Deployment

Deployed to Heroku. The `Procfile` defines the web process:
```
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/josefus-highscore.jar
```
`system.properties` pins the Java runtime to Zulu JDK 24.
