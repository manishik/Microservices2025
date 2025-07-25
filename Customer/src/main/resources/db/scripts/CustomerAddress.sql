DROP TABLE IF EXISTS customeraddress CASCADE;

CREATE TABLE customeraddress
(
    custemail   VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    doorNo      INTEGER,
    street1     VARCHAR(255),
    street2     VARCHAR(255),
    area        VARCHAR(255),
    zipCode     INTEGER,
    state       VARCHAR(100),
    country     VARCHAR(100),
    addresstype VARCHAR(50),
    phonenumber VARCHAR(20),
    email       VARCHAR(255)
);

INSERT INTO customeraddress
VALUES ('john.doe@example.com', 101, 'Maple Street', 'Apt 4B', 'Greenwood', 90210, 'California', 'USA', 'Home',
        '1234567890', 'contact.john@example.com');

INSERT INTO customeraddress
VALUES ('jane.smith@example.com', 202, 'Oak Avenue', 'Suite 12', 'Riverdale', 10001, 'New York', 'USA', 'Work',
        '9876543210', 'j.smith@workmail.com');

INSERT INTO customeraddress
VALUES ('mohan.rao@example.com', 34, 'MG Road', 'Block A', 'Jayanagar', 560041, 'Karnataka', 'India', 'Home',
        '9123456780', 'mohan.rao@personal.com');

INSERT INTO customeraddress
VALUES ('lisa.wong@example.com', 88, 'Queen St', '', 'Downtown', 2000, 'NSW', 'Australia', 'Work', '0456789123',
        'lisa.wong@workmail.au');

INSERT INTO customeraddress
VALUES ('kumar.shah@example.com', 16, 'Nehru Street', '2nd Floor', 'T Nagar', 600017, 'Tamil Nadu', 'India', 'Home',
        '9840011122', 'kumar.shah@home.in');

INSERT INTO customeraddress
VALUES ('emily.jones@example.com', 77, 'Main Road', 'Building C', 'Brookfield', 30301, 'Georgia', 'USA', 'Work',
        '6781234567', 'emily@company.com');

INSERT INTO customeraddress
VALUES ('maria.garcia@example.com', 59, 'Calle Ocho', '', 'Little Havana', 33135, 'Florida', 'USA', 'Home',
        '3055550199', 'm.garcia@personal.com');

INSERT INTO customeraddress
VALUES ('takashi.nakamura@example.com', 9, 'Shibuya Crossing', 'Room 305', 'Shibuya', 1500002, 'Tokyo', 'Japan', 'Work',
        '81312345678', 'takashi@work.co.jp');

INSERT INTO customeraddress
VALUES ('anna.kowalska@example.com', 12, 'Nowy Świat', '', 'Warsaw Center', 00950, 'Masovian', 'Poland', 'Home',
        '48501122334', 'anna.k@private.pl');

INSERT INTO customeraddress
VALUES ('fatima.khan@example.com', 43, 'DHA Phase 6', 'Street 9', 'Lahore Cantt', 54792, 'Punjab', 'Pakistan', 'Work',
        '923001112233', 'fatima.khan@org.pk');
