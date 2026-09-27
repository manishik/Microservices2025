# Customer

## What it does

Customer manages customer records in two separate stores: relational customer and address data in PostgreSQL, and customer documents in MongoDB. It also exposes an account creation endpoint that forwards the request to the Bank service. The Customer service does not store a customer-to-account link as part of that forwarding operation.

## How it works

- Java 17, Spring Boot 3.5.3, Spring Web, Spring Data JPA, Spring Data MongoDB, OpenFeign, Eureka client, Resilience4j, Liquibase, and OpenCSV.
- `/customer/**` handles PostgreSQL customer CRUD, CSV export, and Bank account creation. `/customerM/**` handles the MongoDB customer collection.
- `CustomerAccountController` calls `CustomerServiceImpl`, which sends `POST /account/createAccount` through the `BANKACCOUNTAPP` Feign client. The `createAccountCircuitBreaker` wraps that call; when Bank is unavailable, the fallback returns HTTP 503 and does not create an account.
- PostgreSQL schema and sample data are configured through `src/main/resources/db/changelog-master.yaml`. The MongoDB collection is `CustomerDetails` in the `Customer` database.
- The service listens on port `8082`. Its Eureka registration name is `Customer`.

## Start locally

1. Install Java 17. Start PostgreSQL on `localhost:5432`, with a `postgres` database and a user able to run the Liquibase migrations. The current datasource user is `postgres`; set its password in `src/main/resources/application.properties` or through `SPRING_DATASOURCE_PASSWORD`. The migration scripts create and seed customer tables and include `DROP TABLE` statements, so review them before using a database with existing data.
2. Start MongoDB on `localhost:27017` for the `/customerM/**` endpoints. The active configuration uses `mongodb://localhost:27017/Customer`.
3. Start the sibling `ServiceDiscovery` project (`cd ../ServiceDiscovery && ./mvnw spring-boot:run`). Eureka listens on `localhost:8761` by default.
4. Start the sibling `Bank` project (`cd ../Bank && ./mvnw spring-boot:run`) after its PostgreSQL setup. It listens on port `9090` and registers as `BankAccountApp`. The Customer account endpoint needs Bank registered with Eureka; ordinary customer CRUD does not call Bank.
5. From this directory run `./mvnw spring-boot:run`, then check `http://localhost:8082/customer/listAllCustomers`. MongoDB must be reachable when using MongoDB operations.

CreditCard is independent of Customer.

## API

| Method | Path | Action |
| --- | --- | --- |
| POST | `/customer/createCustomer` | Create a PostgreSQL customer |
| GET | `/customer/getCustomerByEmail/{customerEmail}` | Get a PostgreSQL customer |
| GET | `/customer/listAllCustomers` | List PostgreSQL customers |
| GET | `/customer/getAllCustomersCSVFile` | Export PostgreSQL customers as CSV bytes |
| PUT | `/customer/updateCustomer` | Update a PostgreSQL customer |
| DELETE | `/customer/deleteCustomerByEmail/{customerEmail}` | Delete a PostgreSQL customer |
| POST | `/customer/createAccountForCustomer` | Forward account creation to Bank |
| POST | `/customerM/createCustomer` | Create a MongoDB customer |
| GET | `/customerM/getCustomerById/{customerIdentityNumber}` | Get a MongoDB customer |
| GET | `/customerM/listAllCustomers` | List MongoDB customers |
| PUT | `/customerM/updateCustomer` | Update a MongoDB customer |
| DELETE | `/customerM/deleteCustomer/{customerId}` | Delete a MongoDB customer |
| POST | `/customerM/customerSignIn` | Look up a MongoDB customer by email |

`POST /customer/createAccountForCustomer` accepts the `CustomerAccount` JSON fields (`custId`, `accId`, `accountName`, `accountType`, `amount`, `createDate`, `trasanctionDate`, `endDate`). Bank persists its own `Account` model; `custId` is not a field of that model, so this call does not associate the created account with a customer. The account date property currently uses the spelling `trasanctionDate`.

The MongoDB sign-in endpoint currently checks whether an email exists but does not verify the supplied password or issue a token. The CSV endpoint also writes `Customers.csv` to a hard-coded local path in this project's directory; that path must be writable and match the running machine for export to succeed.
