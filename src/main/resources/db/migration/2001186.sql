# 2.1.186

# Rename from "usageCount2" to "usageCount"

ALTER TABLE Allophone DROP COLUMN usageCount;
ALTER TABLE Allophone CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Emoji DROP COLUMN usageCount;
ALTER TABLE Emoji CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Letter DROP COLUMN usageCount;
ALTER TABLE Letter CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Number DROP COLUMN usageCount;
ALTER TABLE Number CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE StoryBook DROP COLUMN usageCount;
ALTER TABLE StoryBook CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Syllable DROP COLUMN usageCount;
ALTER TABLE Syllable CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Word DROP COLUMN usageCount;
ALTER TABLE Word CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Audio DROP COLUMN usageCount;
ALTER TABLE Audio CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Image DROP COLUMN usageCount;
ALTER TABLE Image CHANGE usageCount2 usageCount int(11) NOT NULL;

ALTER TABLE Video DROP COLUMN usageCount;
ALTER TABLE Video CHANGE usageCount2 usageCount int(11) NOT NULL;
