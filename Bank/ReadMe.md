# Bank

## What it does

Bank is the REST service for bank records and their accounts. It creates, reads, updates, lists, and deletes banks and accounts in PostgreSQL. A bank can have multiple accounts; an account belongs to a bank. The Customer service calls this service when a client requests account creation through Customer.

## How it works

- Java 17, Spring Boot 3.5.3, Spring Web, Spring Data JPA, PostgreSQL, Liquibase, and a Eureka client.
- `BankController` serves `/bank/**`; `AccountController` serves `/account/**`. Services use JPA repositories for persistence.
- The default HTTP port is `9090` and the Eureka service name is `BankAccountApp`. Customer's Feign client looks up this name as `BANKACCOUNTAPP`.
- Liquibase is enabled and points at `src/main/resources/db/changelog-master.yaml`, which includes the bank and account SQL scripts. These scripts create tables, insert sample rows, and create `account_seq` for account IDs.

## Start locally

1. Install Java 17 and have PostgreSQL listening on `localhost:5432`. The configured database is `postgres` and the configured user is `postgres`. Set the correct password in `src/main/resources/application.properties` or override it with `SPRING_DATASOURCE_PASSWORD`. The database user needs permission to create and alter tables and sequences for Liquibase.
2. If Customer will create accounts through this service, start the sibling `ServiceDiscovery` project first (`cd ../ServiceDiscovery && ./mvnw spring-boot:run`). Its Eureka server listens on `localhost:8761`. Bank's Eureka client uses that default address. Bank's own REST endpoints can be used without Customer, but service discovery is needed for Customer's Feign lookup.
3. From this directory run `./mvnw spring-boot:run`. On first startup, allow Liquibase to apply its changelog to the configured database. The included SQL drops and recreates its bank and account tables, so use a development database and review the scripts before applying them to existing data.
4. Check `http://localhost:9090/bank/listAllBanks` or `http://localhost:9090/account/listAllAccounts`.

Start Bank before starting the sibling Customer service if you want `POST /customer/createAccountForCustomer` to work immediately. CreditCard has no runtime dependency on Bank.

## API

| Method | Path | Action |
| --- | --- | --- |
| POST | `/bank/createBank` | Create a bank |
| GET | `/bank/getBankById/{bankIdentityNumber}` | Get one bank |
| GET | `/bank/listAllBanks` | List banks |
| PUT | `/bank/updateBank` | Save bank changes |
| DELETE | `/bank/deleteBank/{bankId}` | Delete a bank |
| POST | `/account/createAccount` | Create an account |
| GET | `/account/getAccountById/{AccountIdentityNumber}` | Get one account |
| GET | `/account/listAllAccounts` | List accounts |
| PUT | `/account/updateAccount` | Save account changes |
| DELETE | `/account/deleteAccount/{AccountId}` | Delete an account |

The service accepts and returns JSON. Account IDs use `account_seq`. In the account entity the date property is spelled `trasanctionDate`, so JSON using that field must retain the spelling. The account table has a `bankid` foreign key; the account's back reference to its bank is omitted from account JSON responses.
