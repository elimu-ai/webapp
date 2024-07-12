# 2.4.6

# Word "letterSoundCorrespondences" → Word "letterSounds"

ALTER TABLE `Word_LetterSoundCorrespondence` DROP COLUMN `letterSounds_id`;
ALTER TABLE `Word_LetterSoundCorrespondence` CHANGE `letterSoundCorrespondences_id` `letterSounds_id` bigint(20) NOT NULL;

ALTER TABLE `Word_LetterSoundCorrespondence` DROP COLUMN `letterSounds_ORDER`;
ALTER TABLE `Word_LetterSoundCorrespondence` CHANGE `letterSoundCorrespondences_ORDER` `letterSounds_ORDER` int(11) NOT NULL;

# Reset primary key
ALTER TABLE `Word_LetterSoundCorrespondence` DROP PRIMARY KEY, ADD PRIMARY KEY(Word_id, letterSounds_ORDER);

# Delete obsolete foreign key constraint for "letterSoundCorrespondences_id"
ALTER TABLE `Word_LetterSoundCorrespondence` DROP `FKf6r3yfdc6quwa0b13mln5uuc8`;
