INSERT INTO CAR (VIN, Car_Type, Make, Model, Model_Year, Model_Trim, Color, Mileage, Price)
VALUES ('H0000000000000001', 'Coupe', 'Honda', 'Civic', 1992, 'Base', 'Black', 190000, 2300),
('H0000000000000002', 'Hatchback', 'Honda', 'Civic', 1993, 'S', 'White',153020, 4000),
('H0000000000000003', 'Coupe', 'Honda', 'Civic', 1995, 'LS', 'Red', 165000, 4200),
('H0000000000000004', 'Sedan', 'Honda', 'Civic', 1994, 'Base', 'Silver', 210339, 2000),
('H0000000000000005', 'Sedan', 'Honda', 'Accord', 1995, 'S', 'White', 100324, 6700),
('H0000000000000006', 'Sedan', 'Honda', 'Accord', 1992, 'LS', 'Blue', 124006, 5320),
('H0000000000000007', 'Sedan', 'Honda', 'Accord', 1994, 'Base', 'Black', 84003, 8300),
('H0000000000000008', 'Sedan', 'Honda', 'Accord', 1992, 'Base', 'White', 145623, 4500),
('H0000000000000009', 'Coupe', 'Honda', 'Prelude', 1992, 'SI', 'Green', 203453, 1999),
('H0000000000000010', 'Coupe', 'Honda', 'Prelude', 1996, 'Base', 'White', 111894, 7400);

INSERT INTO EMPLOYEE (SSN, FName, Minit, LName, Title, WPhone, PPhone, Salary, AccessLevel, UserName, Password) values
('657483957', 'Eric', '', 'Poe', 'Salesperson', '9098831221', '7143445674', 45000, 1, "EricPoe1", "Yolo69"),
('659183343', 'Carl', 'R', 'Jermaine', 'Salesperson', '9513234569', '7146263883', 45000, 1, "Broseidon", "HackThis"),
('695823742', 'Jessica', 'J', 'Smith', 'Salesperson', '9514032873', '7149887667', 45000, 1, "JessicaJonesRulez", "Marvel1"),
('534289301', 'Jen', '', 'Artemis', 'Inventory Manager', '9099339733', '7149207773', 60000, 1, "ArtWoman1", "qwerty123"),
('668392012', 'Chase', 'L', 'Steele', 'Sales Manager', '9098623110', '6264822219', 60000, 1, "Chaser2", "MrSnuggles92"),
('776483241', 'Patrick', 'S', 'Tomb', 'General Manager', '9095623190', '7143239887', 80000, 1, "BigBoss", "2quiet"),
('621321190', 'Debra', '', 'Gonzalez', 'CEO', '9518187600', '7143663291', 100000, 5, "BigD", "420SmackLife");

INSERT INTO CUSTOMER (FName, Minit, LName, Phone, Address, Email) values
('James', 'E', 'Jones', '7145622882', '1234 W. Some st. City, State 91111', 'cust1@email.com'),
('Christina', 'H', 'Lopez', '7564800012', '4321 E. Another st. City, State 91111', 'cust2@email.com'),
('Jay', '', 'Bauman', '8183552199', '4955 W. Fake Ave. City, State 91111', 'cust3@email.com'),
('Denise', '', 'Lewis', '9096548888', '2377 S. Different rd. City, State 91111', 'cust4@email.com'),
('Darius', 'J', 'Jacobs', '7146649930', '1111 N. Green st. City, State 91111', 'cust5@email.com'),
('Lizeth', 'B', 'Bernice', '9517440066', '9889 W. Blue st. City, State 91111', 'cust6@email.com');


INSERT INTO SALE (EID, CID, Sold_Car_ID, Date_Year,Date_Month,Date_Day, PriceSold) values
(1, 1, 1, '2015', '12', '24', 3000),
(1, 2, 2, '2015', '08', '11', 4000),
(3, 3, 3, '2015', '04', '9',12000);
