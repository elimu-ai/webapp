# 1.1.7

UPDATE Allophone SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Allophone SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Allophone SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Allophone SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Allophone DROP COLUMN language;

UPDATE Audio SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Audio SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Audio SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Audio SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Audio DROP COLUMN language;

UPDATE Contributor SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Contributor SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Contributor SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Contributor SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Contributor DROP COLUMN language;

UPDATE Image SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Image SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Image SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Image SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Image DROP COLUMN language;

UPDATE Letter SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Letter SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Letter SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Letter SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Letter DROP COLUMN language;

UPDATE Number SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Number SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Number SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Number SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Number DROP COLUMN language;

UPDATE Word SET locale = 'AR' WHERE language = 'ARABIC';
UPDATE Word SET locale = 'EN' WHERE language = 'ENGLISH';
UPDATE Word SET locale = 'ES' WHERE language = 'SPANISH';
UPDATE Word SET locale = 'SW' WHERE language = 'SWAHILI';
ALTER TABLE Word DROP COLUMN language;
