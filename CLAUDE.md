# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Sanjy Client is a Spring Boot-based application for diet and meal tracking. It's a multi-module Maven project with two client applications (web and telegram) that communicate with a backend service (sanjy-server) via OpenFeign clients.

**Base Package**: `br.com.gorillaroxo.sanjy.client`

## Essential Rules

**IMPORTANT**: These rules MUST be followed at all times:

1. **English Only**: ALL code, comments, commit messages, variable names, class names, method names, and documentation MUST be written in English. Never use Portuguese or any other language in the codebase.

2. **Mandatory Build Validation**: After implementing ANY code changes, you MUST:
   - Run `mvn clean install` from the project root
   - If the build fails, analyze errors, fix them, and run `mvn clean install` again
   - Repeat until the build succeeds with `BUILD SUCCESS`
   - Only consider the task complete when the build passes successfully

## Architecture

### Multi-Module Structure

The project consists of three Maven modules:

1. **`shared`** - Shared library containing:
   - Request/Response DTOs for API communication
   - Feign client interfaces (`DietPlanFeignClient`, `MealRecordFeignClient`)
   - Configuration properties (`SanjyClientConfigProp`)
   - Feign interceptor for distributed tracing (correlation ID, channel headers)
   - Utility classes for distributed tracing and request constants
   - Exception classes (`BusinessException`, `UnexpectedErrorException`)

2. **`web`** - Spring Boot web application (port 8081)
   - Thymeleaf-based MVC controllers
   - Spring AI integration for diet plan file processing (PDF, text)
   - Request filter for correlation ID management
   - Global exception handling
   - Depends on `shared` module

3. **`telegram`** - Spring Boot telegram bot application (port 8082)
   - Telegram bot controllers (currently minimal implementation)
   - Depends on `shared` module

### Key Technologies

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Cloud 2025.0.0** (OpenFeign for HTTP clients)
- **Spring AI 1.0.2** (OpenAI integration for diet plan extraction)
- **Thymeleaf** (server-side rendering for web module)
- **Apache PDFBox** (PDF text extraction)
- **Lombok** + **MapStruct** (with proper annotation processor ordering)
- **SpringDoc OpenAPI** (Swagger documentation)
- **Logstash Logback Encoder** (structured logging)
- **GraalVM Native Image** support (static musl builds)

### Distributed Tracing

The application implements correlation ID tracking across all requests:
- `RequestFilter` (web module) generates or extracts correlation IDs from headers
- `FeignInterceptor` (shared module) propagates correlation IDs to backend service calls
- Uses SLF4J MDC for logging context (correlation ID, transaction ID, HTTP request)
- Custom channel header (`X-Channel`) identifies the client type (web/telegram)

### Configuration Management

Environment-specific configuration is managed via:
- `.env` file for development environment variables
- `application.yml` files in each runnable module (web, telegram)
- Type-safe configuration via `SanjyClientConfigProp` record with validation
- Key configuration properties:
  - `sanjy-client.external-apis.sanjy-server.url` - Backend service URL
  - `sanjy-client.application.channel` - Client channel identifier
  - `sanjy-client.logging.*` - Logging configuration
  - `spring.ai.openai.*` - OpenAI API credentials

### Spring AI Integration (Web Module)

The web module uses Spring AI to extract diet plan information from uploaded files:
- `ProcessDietPlanFileService` - Orchestrates file processing
- `ExtractTextFromFileStrategy` - Strategy pattern for different file types (PDF, text)
- `DietPlanConverter` - Uses OpenAI ChatClient to convert extracted text to structured DTOs

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

# Run the telegram application
cd telegram
../mvnw spring-boot:run

# Access web application
http://localhost:8081

# Access telegram application
http://localhost:8082
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

### Environment Setup

Before running the application, ensure environment variables are set:
- Copy `.env` file and populate with required values
- Required OpenAI credentials: `LOCAL_SANJY_OPENAI_API_KEY`, `LOCAL_SANJY_OPENAI_ORGANIZATION_ID`, `LOCAL_SANJY_OPENAI_PROJECT_ID`
- Backend service URL defaults to `http://localhost:8080`

### GraalVM Native Image

The modules are configured for GraalVM native compilation with static musl builds:

```bash
# Build native image
mvn native:compile -Pnative

# Run native executable
./web/target/web.graalvm
./telegram/target/telegram.graalvm

# Build Docker container with Cloud Native Buildpacks
./mvnw spring-boot:build-image -Pnative
docker run --rm -p 8081:8081 sanjy-client:0.0.1-SNAPSHOT
```

### Important Build Notes

- **Annotation Processors**: Lombok must run before MapStruct. The parent `pom.xml` configures the correct ordering with `lombok-mapstruct-binding`.
- **Module Dependencies**: Both `web` and `telegram` depend on `shared`, so always build from the root or ensure `shared` is installed first.
- **Component Scanning**: Both applications use `@ComponentScan` to scan both their own packages and the `shared` package.
- **Lombok Configuration**: `lombok.config` ensures Spring annotations (`@Qualifier`, `@Value`, `@Lazy`) are copied to generated constructors.

## Application Structure

### Controller Organization (Web Module)

Controllers follow a domain-driven structure:
- `HomeController` - Landing page (`/`)
- `DietPlanController` - Diet plan management (`/diet-plan/*`)
  - Create new diet plan (manual or file upload)
  - View active diet plan
  - File processing with Spring AI
- `MealRecordController` - Meal recording and viewing (`/meal/*`)

All controllers use `@Controller` (not `@RestController`) as they return Thymeleaf view names.

### Feign Client Usage

Feign clients in `shared` module are annotated with `@FeignClient`:
- `DietPlanFeignClient` - Diet plan operations (`/v1/diet-plan`)
- `MealRecordFeignClient` - Meal record operations
- Configured to use `${sanjy-client.external-apis.sanjy-server.url}` from properties
- Automatically intercepted by `FeignInterceptor` for header propagation

### DTO Pattern

The project uses separate request/response DTOs:
- **Requests**: POJOs with Lombok `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- **Responses**: Java records with `@Builder` and defensive copying in compact constructors

## Development Workflow

### Adding New Features

1. **Define DTOs first** in the `shared` module under `dto.request` and `dto.response`
2. **Create/update Feign client interface** in `shared/client`
3. **Implement controller** in `web/controller` or `telegram/controller`
4. **Create Thymeleaf templates** in `web/resources/templates` (for web module)
5. **Add CSS** in `web/resources/static/css` if needed

### Template Organization (Web Module)

Templates are organized by domain:
- `templates/index.html` - Home page
- `templates/diet-plan/` - Diet plan views
- `templates/meal/` - Meal tracking views

### Exception Handling

Use the shared exception hierarchy:
- `BusinessException` - For expected business rule violations
- `UnexpectedErrorException` - For unexpected technical errors
- `ExceptionCode` - Centralized error codes
- Web module has `GlobalExceptionHandlerConfig` for handling exceptions and rendering error pages

## API Documentation

Swagger UI is available when the web application is running:
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI spec: `http://localhost:8081/v3/api-docs`

## Code Style Notes

- Use Lombok annotations to reduce boilerplate
- Response DTOs should be immutable records
- Request DTOs can be mutable POJOs for form binding
- Controllers return view names (Strings), not ResponseEntity
- Follow the existing package structure: `controller`, `client`, `dto.request`, `dto.response`, `service`, `config`, `filter`
- Use structured logging with `StructuredArguments.kv()` for JSON logging support
- Always use correlation ID context in logs (automatically available via MDC)
