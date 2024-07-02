# 2.1.232

ALTER TABLE `LetterSoundCorrespondence_Allophone` DROP COLUMN `LetterSoundCorrespondence_id`;
ALTER TABLE `LetterSoundCorrespondence_Allophone` CHANGE `LetterToAllophoneMapping_id` `LetterSoundCorrespondence_id` bigint(20) NOT NULL;

ALTER TABLE `LetterSoundCorrespondence_Letter` DROP COLUMN `LetterSoundCorrespondence_id`;
ALTER TABLE `LetterSoundCorrespondence_Letter` CHANGE `LetterToAllophoneMapping_id` `LetterSoundCorrespondence_id` bigint(20) NOT NULL;
