# 2.1.230

ALTER TABLE `LetterToAllophoneMapping` DROP COLUMN `letterSoundCorrespondences_id`;
ALTER TABLE `LetterToAllophoneMapping` CHANGE `letterToAllophoneMappings_id` `letterSoundCorrespondences_id` bigint(20) NOT NULL;

ALTER TABLE `LetterToAllophoneMapping` DROP COLUMN `letterSoundCorrespondences_ORDER`;
ALTER TABLE `LetterToAllophoneMapping` CHANGE `letterToAllophoneMappings_ORDER` `letterSoundCorrespondences_ORDER` int(11) NOT NULL;
