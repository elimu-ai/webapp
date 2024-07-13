# 2.4.11

# "LetterSoundCorrespondenceContributionEvent" → "LetterSoundContributionEvent"
DROP TABLE IF EXISTS `LetterSoundContributionEvent`;
ALTER TABLE `LetterSoundCorrespondenceContributionEvent` RENAME `LetterSoundContributionEvent`;