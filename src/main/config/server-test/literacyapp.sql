# mysql -u root -p
# mysql> SOURCE literacyapp.sql;

CREATE DATABASE `literacyapp`;
USE `literacyapp`;
CREATE USER 'literacyapp-user'@'localhost' IDENTIFIED BY '************';
GRANT ALL ON `literacyapp`.* TO 'literacyapp-user'@'localhost';
