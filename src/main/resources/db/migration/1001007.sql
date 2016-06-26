# 1.1.7

ALTER TABLE Audio DROP COLUMN language;

UPDATE Contributor SET locale = 'AR' WHERE language = 0;
UPDATE Contributor SET locale = 'EN' WHERE language = 1;
UPDATE Contributor SET locale = 'ES' WHERE language = 2;
UPDATE Contributor SET locale = 'SW' WHERE language = 3;
ALTER TABLE Contributor DROP COLUMN language;

UPDATE Image SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Image SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Image SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Image SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Image DROP COLUMN language;

ALTER TABLE Letter DROP COLUMN language;

UPDATE Number SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Number SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Number SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Number SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Number DROP COLUMN language;

ALTER TABLE Word DROP COLUMN language;
