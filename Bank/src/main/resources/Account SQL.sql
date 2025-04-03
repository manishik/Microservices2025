CREATE TABLE Account
(
    accid numeric PRIMARY KEY not null,
    accountname   varchar(100),
    accounttype  varchar(100),
    bankid numeric,
    FOREIGN KEY(bankid) REFERENCES Bank(bankId)
);

Drop table Account;

truncate table Account;

select *
from Account;

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('1', 'BoFAccount', 'Savings Account', 1);

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('2', 'BoFAccount', 'Checkings Account', 1);

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('3', 'CAAccount', 'Savings Account', 2);

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('4', 'CAAccount', 'Checkings Account', 2);

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('5', 'CitiAccount', 'Savings Account', 3);

INSERT INTO Account (accid, accountname, accounttype, bankid)
VALUES ('6', 'CitiAccount', 'Checkings Account', 3);

delete
from Account
where accid = '30';

commit;


