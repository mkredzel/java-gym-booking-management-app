
/* Database to be used for the Booking Management System */
DROP DATABASE gym;
CREATE DATABASE gym;
USE gym;

/* Creates Member Table */
CREATE TABLE Member(
	id VARCHAR(4),
	fName VARCHAR(24) NOT NULL,
	lName VARCHAR(24) NOT NULL,
	PRIMARY KEY (id)
);
	
/* Creates PT Table */
CREATE TABLE PT(
	staffId VARCHAR(4),
	fName VARCHAR(24) NOT NULL,
	lName VARCHAR(24) NOT NULL,
	PRIMARY KEY (staffId)
);

/* Creates Speciality Table */
CREATE TABLE Speciality(
    staffId VARCHAR(4),
	focus VARCHAR(25) NOT NULL,
	PRIMARY KEY (focus, staffId),
	FOREIGN KEY (staffId) REFERENCES PT(staffId)
);

/* Creates Booking Table */
CREATE TABLE Booking(
    confirmation INT AUTO_INCREMENT, 
	id VARCHAR(4) NOT NULL,
	staffId VARCHAR(4) NOT NULL,
	date DATE NOT NULL,
	timeframe VARCHAR(5) NOT NULL,
	focus VARCHAR(25) NOT NULL,
	PRIMARY KEY (confirmation),
	FOREIGN KEY (id) REFERENCES Member(id),
    FOREIGN KEY (staffId) REFERENCES PT(staffId),
    FOREIGN KEY (focus) REFERENCES Speciality(focus)
);

/* Auto Increment Value Starts From 1000 */
ALTER TABLE Booking AUTO_INCREMENT=1000;
	
/* Inserts Data Into Member Table */	
INSERT INTO Member
VALUES ('0001', 'John', 'Doe'),
 ('0002', 'Martin', 'Gamez'),
 ('0003', 'Martina', 'Balzary'),
 ('0004', 'John', 'Frusciante'),
 ('0005', 'Alice', 'Evans'),
 ('0006', 'Chris', 'Nalewako'),
 ('0007', 'Boris', 'Johnson'),
 ('0008', 'Frank', 'Lampard'),
 ('0009', 'Maisie', 'Trump'),
 ('0010', 'Josh', 'Kavanagh');

/* Inserts Data Into PT Table */
INSERT INTO PT
VALUES ('0001', 'John', 'White'),
 ('0002', 'Julie', 'Lee'),
 ('0003', 'Trudy', 'Monk'),
 ('0004', 'Tom', 'Jones');
        
/* Inserts Data Into Speciality Table */		
INSERT INTO Speciality
VALUES ('0001', 'weight loss'),
 ('0002', 'flexibility'),
 ('0004', 'zumba'),
 ('0002', 'muscle gain'),
 ('0003', 'muscle gain');
 
 /* Inserts Data Into Booking Table */	
INSERT INTO Booking (id, staffId, date, timeframe, focus)
VALUES ('0001', '0002', '20200229', '14-15', 'flexibility'),
('0006', '0003', '20200227', '16-17', 'muscle gain'),
('0002', '0001', '20250302', '10-11', 'weight loss'),
('0005', '0004', '20240306', '8-9', 'zumba'),
('0003', '0001', '20210225', '9-10', 'weight loss'),
('0003', '0002', '20220227', '10-11', 'muscle gain'),
('0002', '0004', '20210228', '11-12', 'zumba'),
('0005', '0002', '20220226', '6-7', 'flexibility'),
('0010', '0004', '20220221', '11-12', 'zumba'),
('0007', '0001', '20220222', '13-14', 'weight loss'),
('0008', '0004', '20230215', '11-12', 'zumba'),
('0005', '0001', '20221129', '13-14', 'weight loss'),
('0004', '0002', '20210629', '17-18', 'flexibility');
