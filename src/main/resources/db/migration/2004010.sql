# 2.4.10

# "LetterSoundCorrespondencePeerReviewEvent" → "LetterSoundPeerReviewEvent"
DROP TABLE IF EXISTS `LetterSoundPeerReviewEvent`;
ALTER TABLE `LetterSoundCorrespondencePeerReviewEvent` RENAME `LetterSoundPeerReviewEvent`;
