# 2.1.230

ALTER TABLE `Word_LetterToAllophoneMapping` DROP COLUMN `letterSoundCorrespondences_id`;
ALTER TABLE `Word_LetterToAllophoneMapping` CHANGE `letterToAllophoneMappings_id` `letterSoundCorrespondences_id` bigint(20) NOT NULL;

ALTER TABLE `Word_LetterToAllophoneMapping` DROP COLUMN `letterSoundCorrespondences_ORDER`;
ALTER TABLE `Word_LetterToAllophoneMapping` CHANGE `letterToAllophoneMappings_ORDER` `letterSoundCorrespondences_ORDER` int(11) NOT NULL;
