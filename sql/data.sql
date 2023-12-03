create database if not exists Tables;
use Tables;

--
-- insert data into table `client`
--
INSERT INTO client (clientID, authentication, type)
VALUES (102,'ca996','Distributor');

INSERT INTO client (clientID, authentication, type)
VALUES (356, 'ca997','not Distributor');

INSERT INTO client (clientID, authentication, type)
VALUES (98, 'ca998','not Distributor');

INSERT INTO client (clientID, authentication, type)
VALUES (5, 'ca999','Distributor');


--
-- insert data into table `facility`
--
INSERT INTO facility (facilityID, associated_distributorID, latitude, longitude, public, email, phone, hours)
VALUES (1, 102, 50.0, 60.1, 0, '43678@gmail.com','300-400-500', '0600-1800');

INSERT INTO facility (facilityID, associated_distributorID, latitude, longitude, public, email, phone, hours)
VALUES (2, 102, 57.0, 60.1, 0, '988279@gmail.com','800-450-500', '0700-1500');

INSERT INTO facility (facilityID, associated_distributorID, latitude, longitude, public, email, phone, hours)
VALUES (3, 5, 66.0, 60.1, 0, 'hksuy@gmail.com','309-930-700', '0630-1800');

INSERT INTO facility (facilityID, associated_distributorID, latitude, longitude, public, email, phone, hours)
VALUES (4, 5, 58.9, 60.1, 0, 'frd68@gmail.com','608-700-510', '0610-1900');


--
-- insert data into table `listings`
--
INSERT INTO listings (listingID, associated_facilityID, public, group_code, item_list, age_requirement, veteran_status)
VALUES (663, 1, 0, 891, 'floor mat|chair', 20, 1);

INSERT INTO listings (listingID, associated_facilityID, public, group_code, item_list, age_requirement, veteran_status, gender)
VALUES (664, 2, 0, 892, 'emergency call button|first aid kit', 25, 0, 'f');

INSERT INTO listings (listingID, associated_facilityID, public, group_code, item_list, age_requirement, veteran_status)
VALUES (665, 1, 0, 891, 'fruit|vegetable', 20, 0);

INSERT INTO listings (listingID, associated_facilityID, public, group_code, item_list, age_requirement, veteran_status)
VALUES (667, 3, 1, 893, 'canned food', 35, 1);

INSERT INTO listings (listingID, associated_facilityID, public, group_code, item_list, age_requirement, veteran_status)
VALUES (669, 4, 1, 894, 'board games|chess', 25, 1);








