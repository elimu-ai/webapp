# 2.4.12

# "LetterSoundCorrespondence" → "LetterSound"
DROP TABLE IF EXISTS `LetterSound`;
ALTER TABLE `LetterSoundCorrespondence` RENAME `LetterSound`;

DROP TABLE IF EXISTS `LetterSound_Letter`;
ALTER TABLE `LetterSoundCorrespondence_Letter` RENAME `LetterSound_Letter`;

DROP TABLE IF EXISTS `LetterSound_Sound`;
ALTER TABLE `LetterSoundCorrespondence_Sound` RENAME `LetterSound_Sound`;

DROP TABLE IF EXISTS `Word_LetterSound`;
ALTER TABLE `Word_LetterSoundCorrespondence` RENAME `Word_LetterSound`;
