# 1.0.8
ALTER TABLE Contributor DROP COLUMN firstName;
ALTER TABLE Contributor CHANGE name firstName VARCHAR(255) NOT NULL;