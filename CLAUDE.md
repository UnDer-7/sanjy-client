# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Sanjy Client is a Spring Boot-based web application for diet and meal tracking. It's a multi-module Maven project with a web frontend (Thymeleaf) that communicates with a backend service via OpenFeign clients.

**Base Package**: `br.com.gorillaroxo.sanjy.client`

## Architecture

### Multi-Module Structure

The project consists of two Maven modules:

1. **`shared`** - Contains shared DTOs and Feign client interfaces
   - Request/Response DTOs for API communication
   - Feign client interfaces (currently stubs)
   - No direct Spring Boot dependencies

2. **`web`** - Spring Boot web application
   - Thymeleaf-based MVC controllers
   - Static resources (CSS, templates)
   - Depends on the `shared` module
   - Runs on port 8081

### Key Technologies

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Cloud 2025.0.0** (OpenFeign for HTTP clients)
- **Spring AI 1.0.2**
- **Thymeleaf** (server-side rendering)
- **Lombok** + **MapStruct** (with proper annotation processor ordering)
- **SpringDoc OpenAPI** (Swagger documentation)
- **GraalVM Native Image** support (static musl builds)

### Current Implementation Status

The application is in early development with **mock controllers**. Controllers return empty data and do not yet call backend services via Feign clients. The `DietPlanFeignClient` interface in `shared` is currently empty.

### Controller Organization

Controllers follow a domain-driven structure:
- `HomeController` - Landing page (`/`)
- `DietPlanController` - Diet plan management (`/diet-plan/*`)
- `MealRecordController` - Meal recording and viewing (`/meal/*`)

All controllers use `@Controller` (not `@RestController`) as they return Thymeleaf view names.

### DTO Pattern

The project uses separate request/response DTOs:
- **Requests**: POJOs with Lombok `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- **Responses**: Java records with `@Builder` and defensive copying in compact constructors

## CRITICAL: Code Validation Rules

**MANDATORY**: After making ANY code changes (creating files, editing code, refactoring, etc.), you MUST:

1. Run `mvn clean install` from the project root
2. If the build FAILS:
   - Analyze the error messages carefully
   - Fix the compilation/build errors
   - Run `mvn clean install` again
3. Repeat step 2 until the build SUCCEEDS
4. Only consider the task complete when `mvn clean install` passes with BUILD SUCCESS

**Do NOT skip this validation step.** A successful build ensures:
- All modules compile correctly
- No syntax or type errors
- Annotation processors (Lombok, MapStruct) run successfully
- Module dependencies are satisfied
- The code is ready for deployment

## Build and Development

### Building the Project

```bash
# Clean install (compile all modules)
mvn clean install

# Compile only (quieter output)
mvn clean compile -q

# Run the web application
cd web
../mvnw spring-boot:run

# Access application
http://localhost:8081
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
cd web
../mvnw test

# Run tests in native image
mvn test -PnativeTest
```

### GraalVM Native Image

The web module is configured for GraalVM native compilation with static musl builds:

```bash
# Build native image
mvn native:compile -Pnative

# Run native executable
./web/target/web.graalvm

# Build Docker container with Cloud Native Buildpacks
./mvnw spring-boot:build-image -Pnative
docker run --rm -p 8081:8081 sanjy-client:0.0.1-SNAPSHOT
```

### Important Build Notes

- **Annotation Processors**: Lombok must run before MapStruct. The web module's `pom.xml` has the correct ordering configured with `lombok-mapstruct-binding`.
- **Module Dependencies**: The `web` module depends on `shared`, so always build from the root or ensure `shared` is installed first.
- **Component Scanning**: `@EnableFeignClients` is on the main application class but currently doesn't scan for clients as the `DietPlanFeignClient` is not yet annotated.

## Application Configuration

Configuration is in `web/src/main/resources/application.yml`:
- Server port: 8081
- Application name: `sanjy-client-web`

Future Feign client configurations will likely be added here.

## Development Workflow

### Adding New Features

1. **Define DTOs first** in the `shared` module
2. **Create/update Feign client interface** in `shared/client`
3. **Implement controller** in `web/controller`
4. **Create Thymeleaf templates** in `web/resources/templates`
5. **Add CSS** in `web/resources/static/css` if needed

### Feign Client Integration

When implementing Feign clients:
- Add `@FeignClient` annotation to interfaces in `shared`
- Configure base URL in `application.yml`
- Controllers should inject and use these clients
- Remove mock comments and empty data returns from controllers

### Template Organization

Templates are organized by domain:
- `templates/index.html` - Home page
- `templates/diet-plan/` - Diet plan views
- `templates/meal/` - Meal tracking views

## API Documentation

Swagger UI is available when the application is running:
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI spec: `http://localhost:8081/v3/api-docs`

## Code Style Notes

- Use Lombok annotations to reduce boilerplate
- Response DTOs should be immutable records
- Request DTOs can be mutable POJOs for form binding
- Controllers return view names (Strings), not ResponseEntity
- Follow the existing package structure: `controller`, `client`, `dto.request`, `dto.response`
