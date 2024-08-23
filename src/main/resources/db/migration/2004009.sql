# 2.4.9

# "letterSoundCorrespondenceContributionEvent" → "letterSoundContributionEvent"
ALTER TABLE `LetterSoundCorrespondencePeerReviewEvent` DROP COLUMN IF EXISTS `letterSoundContributionEvent_id`;
ALTER TABLE `LetterSoundCorrespondencePeerReviewEvent` CHANGE `letterSoundCorrespondenceContributionEvent_id` `letterSoundContributionEvent_id` bigint(20) NOT NULL;
