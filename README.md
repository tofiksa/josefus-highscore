## Josefus Highscore Service

Spring Boot 3 service that manages users, authentication (JWT), games and scores with PostgreSQL + Flyway.

### Requirements
- Java 21+
- Maven 3.6+
- PostgreSQL (or another JDBC DB) with the required schema (Flyway migrations provided)

### Configuration
Environment variables (required in dev/prod):
- `SUPABASE_JOSEFUS_DB_URL`
- `SUPABASE_JOSEFUS_DB_USER`
- `SUPABASE_JOSEFUS_DB_PWD`
- `JOSEFUS_JWT_SECRET` (base64-encoded HS256 key)

Optional CORS config (dev allows localhost by default):
- `app.cors.allowed-origins`, `app.cors.allowed-origin-patterns`, `app.cors.allowed-methods`, `app.cors.allowed-headers`, `app.cors.allow-credentials`

### Run (dev)
```
mvn spring-boot:run
```
The app starts on `http://localhost:8080` (see `src/main/resources/application.yml`).

### Build a jar
```
mvn clean package
java -jar target/josefus-highscore.jar
```

### API docs
Swagger UI: `http://localhost:8080/swagger-ui/index.html`
OpenAPI: `http://localhost:8080/v3/api-docs`

### Notes
- JWT auth is stateless; include `Authorization: Bearer <token>` on secured endpoints.
- Database schema is managed via Flyway migrations under `src/main/resources/db/migration`.