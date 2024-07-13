# 2.4.10

# "LetterSoundCorrespondencePeerReviewEvent" → "LetterSoundPeerReviewEvent"
DROP TABLE `LetterSoundPeerReviewEvent`;
ALTER TABLE `LetterSoundCorrespondencePeerReviewEvent` RENAME `LetterSoundPeerReviewEvent`;
