# 1.1.9

ALTER TABLE Contributor_teams MODIFY teams VARCHAR(255);
UPDATE Contributor_teams SET teams = 'ANALYTICS' WHERE teams = '0';
UPDATE Contributor_teams SET teams = 'CONTENT_CREATION' WHERE teams = '1';
UPDATE Contributor_teams SET teams = 'DEVELOPMENT' WHERE teams = '2';
UPDATE Contributor_teams SET teams = 'MARKETING' WHERE teams = '3';
UPDATE Contributor_teams SET teams = 'TESTING' WHERE teams = '4';
UPDATE Contributor_teams SET teams = 'TRANSLATION' WHERE teams = '5';
UPDATE Contributor_teams SET teams = 'OTHER' WHERE teams = '6';