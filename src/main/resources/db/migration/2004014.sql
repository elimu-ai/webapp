# 2.4.14

ALTER TABLE `AudioContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `AudioContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `AudioPeerReviewEvent` DROP COLUMN `timestamp`;
ALTER TABLE `AudioPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `ImageContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `ImageContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `LetterContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `LetterContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `LetterLearningEvent` DROP COLUMN `timestamp`;
ALTER TABLE `LetterLearningEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `LetterSoundContributionEvent` DROP COLUMN IF EXISTS `timestamp`;
ALTER TABLE `LetterSoundContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `LetterSoundPeerReviewEvent` DROP COLUMN IF EXISTS `timestamp`;
ALTER TABLE `LetterSoundPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `NumberContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `NumberContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `NumberPeerReviewEvent` DROP COLUMN `timestamp`;
ALTER TABLE `NumberPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `SoundContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `SoundContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `StoryBookContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `StoryBookContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `StoryBookLearningEvent` DROP COLUMN `timestamp`;
ALTER TABLE `StoryBookLearningEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `StoryBookPeerReviewEvent` DROP COLUMN `timestamp`;
ALTER TABLE `StoryBookPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `WordContributionEvent` DROP COLUMN `timestamp`;
ALTER TABLE `WordContributionEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `WordLearningEvent` DROP COLUMN `timestamp`;
ALTER TABLE `WordLearningEvent` CHANGE `time` `timestamp` DATETIME;

ALTER TABLE `WordPeerReviewEvent` DROP COLUMN `timestamp`;
ALTER TABLE `WordPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;
