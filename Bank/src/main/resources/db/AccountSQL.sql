DROP TABLE IF EXISTS Account CASCADE;

CREATE TABLE Account
(
    accid numeric PRIMARY KEY not null,
    accountname   varchar(100),
    accounttype  varchar(100),
    amount numeric,
    createdate date NOT NULL,
    trasanctiondate date NOT NULL,
    enddate date,
    bankid numeric,
    FOREIGN KEY(bankid) REFERENCES Bank(bankId)
);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('1', 'BoFAccount', 'Savings Account', 100.01, '1990-05-15', '1992-05-15', CAST(null AS date), 1);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('2', 'BoFAccount', 'Checkings Account', 200.1, '1990-05-15','1992-05-15', CAST(null AS date), 1);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('3', 'CAAccount', 'Savings Account', 5000, '2000-01-02', '2001-05-15', CAST(null AS date),2);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('4', 'CAAccount', 'Checkings Account', 3000, '2000-01-02', '2001-05-15', CAST(null AS date),2);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('5', 'CitiAccount', 'Savings Account', 4000, '2010-03-03','2011-05-15', CAST(null AS date), 3);

INSERT INTO Account (accid, accountname, accounttype, amount, createdate, trasanctiondate, enddate, bankid)
VALUES ('6', 'CitiAccount', 'Checkings Account', 6000, '2010-03-03', '2011-05-15', CAST(null AS date),3);

DROP SEQUENCE IF EXISTS account_seq CASCADE;

CREATE SEQUENCE account_seq AS INTEGER;

ALTER SEQUENCE account_seq OWNER TO postgres;