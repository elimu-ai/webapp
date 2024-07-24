# 2.4.21

# "LetterSoundCorrespondence" → "LetterSound"
DROP TABLE IF EXISTS `LetterSound`;
ALTER TABLE `LetterSoundCorrespondence` RENAME TO `LetterSound`;

DROP TABLE IF EXISTS `LetterSound_Letter`;
ALTER TABLE `LetterSoundCorrespondence_Letter` RENAME TO `LetterSound_Letter`;

DROP TABLE IF EXISTS `LetterSound_Sound`;
ALTER TABLE `LetterSoundCorrespondence_Sound` RENAME TO `LetterSound_Sound`;

DROP TABLE IF EXISTS `Word_LetterSound`;
ALTER TABLE `Word_LetterSoundCorrespondence` RENAME TO `Word_LetterSound`;