# 2.0.90

ALTER TABLE Application DROP COLUMN appGroup_id;
ALTER TABLE Application DROP FOREIGN KEY FK_myy9hkp97blf86sy543j9ufxd;

DROP TABLE AppGroup_Application;
DROP TABLE AppCategory_AppGroup;
DROP TABLE AppGroup;
