# 2.1.40

ALTER TABLE StoryBook DROP COLUMN readingLevel;
ALTER TABLE StoryBook CHANGE gradeLevel readingLevel VARCHAR(255) DEFAULT NULL;
