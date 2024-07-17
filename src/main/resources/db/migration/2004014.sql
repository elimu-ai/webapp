# 2.4.14

ALTER TABLE `AudioContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `AudioPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `ImageContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `LetterContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `LetterLearningEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `LetterSoundContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `LetterSoundPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `NumberContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `NumberPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `SoundContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `StoryBookContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `StoryBookLearningEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `StoryBookPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `WordContributionEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `WordLearningEvent` CHANGE `time` `timestamp` DATETIME;
ALTER TABLE `WordPeerReviewEvent` CHANGE `time` `timestamp` DATETIME;