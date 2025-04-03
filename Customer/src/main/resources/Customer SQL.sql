CREATE TABLE Customer
(
    custid    numeric PRIMARY KEY,
    custfirstname   varchar(100),
    custmiddlename  varchar(100),
    custlastname    varchar(100),
    custemail       varchar(100),
    custphonenumber numeric(64),
    custcellnumber numeric(64),
    custaddress     varchar(500)
);

Drop table Customer;

truncate table Customer;

select *
from Customer;

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('1', 'Manish', 'I', 'K', 'Manish@Manish.com', '1', '1', 'Ellicott City, USA');

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('2', 'Suman', ' ', 'Swamy', 'Suman@Suman.com', '22', '22', 'Bogadi Mysore');

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('3', 'Sam', '', 'M', 'Sam@Sam.com', '333', '333', 'Bogadi Mysore');

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('4', 'Santosh', ' ', 'Kumar', 'Santosh@Santosh.com', '4444', '4444', 'Annapolis, USA');

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('5', 'Joe', ' ', 'I', 'Joe@Joe.com', '55555', '55555', 'Bogadi Mysore');

INSERT INTO Customer (custid, custfirstname, custmiddlename, custlastname, custemail, custphonenumber, custcellnumber, custaddress)
VALUES ('33', 'Random', 'I', 'Person', 'Random@person.com', '666666', '666666', 'USA');

delete
from Customer
where custid = '3';

commit;


