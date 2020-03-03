# 2.1.40

ALTER TABLE StoryBook DROP COLUMN readingLevel;
ALTER TABLE StoryBook CHANGE readingLevel readingLevel VARCHAR(255) DEFAULT NULL;
