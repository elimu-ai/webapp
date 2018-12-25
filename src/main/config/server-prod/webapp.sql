# mysql -u root -p
# mysql> SOURCE webapp.sql;

CREATE DATABASE `literacyapp` CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `literacyapp`;
# The password is fetched from the Jetty context file
CREATE USER 'literacyapp-user'@'localhost' IDENTIFIED BY '************';
GRANT ALL ON `literacyapp`.* TO 'literacyapp-user'@'localhost';
