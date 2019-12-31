# 2.0.92

ALTER TABLE Contributor DROP COLUMN project_id;
ALTER TABLE Contributor DROP FOREIGN KEY FK_5i2wtk8pjasrobqyyou5sqqda;

DROP TABLE Project_Contributor;
DROP TABLE Project;
