--liquibase formatted sql

--changeset liquibase-docs:CustomerInsertion.sql
INSERT INTO Customer
VALUES ('Manish@Manish.com', 'Manish', 'I', 'K', '1', '1');

INSERT INTO Customer
VALUES ('Suman@Suman.com', 'Suman', ' ', 'Swamy', '22', '22');

INSERT INTO Customer
VALUES ('Sam@Sam.com', 'Sam', '', 'M', '333', '333');

INSERT INTO Customer
VALUES ('Santosh@Santosh.com', 'Santosh', ' ', 'Kumar', '4444', '4444');

INSERT INTO Customer
VALUES ('Joe@Joe.com', 'Joe', ' ', 'I', '55555', '55555');

INSERT INTO Customer
VALUES ('Random@person.com', 'Random', 'I', 'Person', '666666', '666666');

INSERT INTO Customer
VALUES ('john.doe@example.com', 'John', 'I', 'Doe', '6664345666', '7777776');

INSERT INTO Customer (custemail, custfirstname, custmiddlename, custlastname, custphonenumber, custcellnumber)
VALUES ('lisa.wong@example.com', 'Lisa', 'I', 'Wong', '666666', '666666');
