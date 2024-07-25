# 2.4.22

# "LetterSoundCorrespondence" → "LetterSound"
DROP TABLE IF EXISTS `LetterSound`;
ALTER TABLE `LetterSoundCorrespondence` RENAME TO `LetterSound`;

DROP TABLE IF EXISTS `LetterSound_Letter`;
ALTER TABLE `LetterSoundCorrespondence_Letter` RENAME TO `LetterSound_Letter`;
ALTER TABLE `LetterSound_Letter` CHANGE `LetterSoundCorrespondence_id` `LetterSound_id` bigint(20) NOT NULL;

DROP TABLE IF EXISTS `LetterSound_Sound`;
ALTER TABLE `LetterSoundCorrespondence_Sound` RENAME TO `LetterSound_Sound`;
ALTER TABLE `LetterSound_Sound` CHANGE `LetterSoundCorrespondence_id` `LetterSound_id` bigint(20) NOT NULL;

DROP TABLE IF EXISTS `Word_LetterSound`;
ALTER TABLE `Word_LetterSoundCorrespondence` RENAME TO `Word_LetterSound`;
