DROP TABLE IF EXISTS CreditCards CASCADE;

CREATE TABLE CreditCards
(
    ccnumber        numeric PRIMARY KEY,
    ccname          varchar(100),
    cctype          varchar(100),
    ccexpirydate    date,
    cccvv           numeric,
    creditlimit     numeric,
    availablecredit numeric
);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('1234567890123456', 'Apple', 'MasterCard', '12/31/2030', 123, 7500, 7500);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ( '2345678901234563', 'Capital One', 'Visa', '12/31/2030', 111, 5000, 5000);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('2345678901234561', 'Capital One', 'MasterCard', '12/31/2030', 222, 5000, 5000);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('3456789012345612', 'Bank Of America', 'Visa', '12/31/2028', 333, 10000, 10000);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('4567890123456123', 'Citi', 'MasterCard', '12/31/2029', 444, 10000, 10000);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('5678901234561234', 'Discover', 'Discover', '12/31/2040', 555, 10000, 10000);

INSERT INTO CreditCards (ccnumber, ccname, cctype, ccexpirydate, cccvv, creditlimit, availablecredit)
VALUES ('5678901234564444', 'American Express', 'American Express', '12/31/2035', 666, 20000, 2000);
