DROP TABLE IF EXISTS Bank CASCADE;

CREATE TABLE Bank
(
    bankid    numeric PRIMARY KEY,
    bankname   varchar(100),
    bankemail  varchar(100),
    bankphoneNumber numeric(64),
    bankaddress     varchar(300)
);

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('1', 'BoA', 'BoA@BoA.com', '123','Ellicott City MD, USA');

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('2', 'CA', 'CA@CA.com', '345','McLean VA, USA');

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('3', 'Citi', 'Citi@Citi.com', '678','Singapore, Singapore');

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('4', 'SBI', 'SBI@SBI.com', '912','Bangalore, India');

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('5', 'Maybank', 'Maybank@Maybank.com', '334','KL, Malaysia');

INSERT INTO Bank (bankid, bankname, bankemail, bankphoneNumber, bankaddress)
VALUES ('6', 'ICICI', 'ICICI@ICICI.com', '555','Mumbai, India');
