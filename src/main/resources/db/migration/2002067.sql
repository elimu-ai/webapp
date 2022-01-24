# 2.2.67

# Syllable "allophones" --> "sounds"
ALTER TABLE `Syllable_Allophone` DROP COLUMN `sounds_id`;
ALTER TABLE `Syllable_Allophone` CHANGE `allophones_id` `sounds_id` bigint(20) NOT NULL;

# Syllable "allophones" --> "sounds"
ALTER TABLE `Syllable_Allophone` DROP COLUMN `sounds_ORDER`;
ALTER TABLE `Syllable_Allophone` CHANGE `allophones_ORDER` `sounds_ORDER` int(11) NOT NULL;
