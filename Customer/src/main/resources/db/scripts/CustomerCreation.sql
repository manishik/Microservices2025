--liquibase formatted sql
--changeset liquibase-docs:CustomerCreation.sql

DROP TABLE IF EXISTS Customer;

DROP SEQUENCE IF EXISTS customer_seq CASCADE;

CREATE SEQUENCE customer_seq AS INTEGER;

ALTER SEQUENCE customer_seq OWNER TO postgres;

CREATE TABLE Customer
(
    custid    numeric PRIMARY KEY,
    custfirstname   varchar(100),
    custmiddlename  varchar(100),
    custlastname    varchar(100),
    custemail       varchar(100) unique not null,
    custphonenumber numeric(64),
    custcellnumber numeric(64),
    custaddress     varchar(500)
);