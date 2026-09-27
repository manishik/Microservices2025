# CreditCard

## What it does

CreditCard stores credit card records, supports create/read/update/delete operations, and checks whether a 16-digit card number exists in its PostgreSQL table. Validation is an existence check after a 16-digit format check; it does not perform a payment authorization, expiry check, or Luhn check. The service runs independently of the Bank and Customer applications.

## How it works

- Java 17, Spring Boot 3.4.0, Spring Web with Undertow, Spring JDBC, PostgreSQL, Spring Cache, Spring Security OAuth2 resource server, and springdoc OpenAPI.
- `CCCrudController` exposes `/CrudCC/**`; `CCValidationController` exposes `/validate/creditCard/{ccNumber}`. Services call JDBC DAOs against `creditcards`.
- Successful card lookups, validation results, and the full card list use Spring's in-process cache. Create, update, and delete operations clear or replace relevant cached entries; `/CrudCC/clearCache` clears them all.
- The server listens on `localhost:8090` by default. Business endpoints require a bearer JWT. The configured issuer is `http://localhost:8080/realms/master`; health and Swagger routes are publicly accessible.
- The datasource uses the `manish.datasource.*` settings in `src/main/resources/application.properties`, rather than Spring's usual `spring.datasource.*` prefix.

## Start locally

1. Install Java 17. Start PostgreSQL on `localhost:5432` with the configured `postgres` database and a user with access to `creditcards`. Set the actual database password through `manish.datasource.password` or the `MANISH_DATASOURCE_PASSWORD` environment variable.
2. Ensure the `creditcards` table exists. `spring.liquibase.enabled=false` in the active configuration, so the application does not create it on startup. `src/main/resources/db/CreditCardSQL.sql` contains a schema and sample rows for a fresh development database. It begins with `DROP TABLE IF EXISTS CreditCards CASCADE`, so running it against an existing database removes that table and its data.
3. Start an OAuth2/OIDC issuer available at `http://localhost:8080/realms/master` (the current configuration expects a Keycloak realm at that URL), or set `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` to your issuer URL. The issuer must be reachable so Spring can configure JWT validation, and callers need a token from that issuer for the business endpoints.
4. From this directory run `./mvnw spring-boot:run`. Check `http://localhost:8090/actuator/health` or browse `http://localhost:8090/swagger-ui/index.html`.

Bank, Customer, and ServiceDiscovery do not need to be started for this service.

## API

| Method | Path | Action |
| --- | --- | --- |
| POST | `/CrudCC/createCC` | Add a card |
| GET | `/CrudCC/getCCbyId/{ccNumber}` | Get card details |
| GET | `/CrudCC/listAllCC` | List cards |
| PUT | `/CrudCC/updateCC` | Update card name and type |
| DELETE | `/CrudCC/delCC/{ccNumber}` | Delete a card |
| DELETE | `/CrudCC/clearCache` | Clear card caches |
| POST | `/validate/creditCard/{ccNumber}` | Check card-number format and database presence |

Send `Authorization: Bearer <token>` to these routes. A create request needs at least `ccNumber` (16 digits), `ccName`, and `ccType`; the current INSERT saves those three fields. Updating a card changes only `ccName` and `ccType`, even though the JSON model contains expiry, CVV, and credit-limit fields. Validation returns the number and a message when a matching record exists. Invalid number format returns HTTP 400, a missing card returns 404, and duplicate creation returns 409.
