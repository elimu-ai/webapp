# 2.2.68

# LetterSoundCorrespondence "allophones" --> "sounds"
ALTER TABLE `LetterSoundCorrespondence_Allophone` DROP COLUMN `sounds_id`;
ALTER TABLE `LetterSoundCorrespondence_Allophone` CHANGE `allophones_id` `sounds_id` bigint(20) NOT NULL;

# LetterSoundCorrespondence "allophones" --> "sounds"
ALTER TABLE `LetterSoundCorrespondence_Allophone` DROP COLUMN `sounds_ORDER`;
ALTER TABLE `LetterSoundCorrespondence_Allophone` CHANGE `allophones_ORDER` `sounds_ORDER` int(11) NOT NULL;
