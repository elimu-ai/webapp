# 2.2.69

# "Allophone" --> "Sound"

DROP TABLE `Sound`;
ALTER TABLE `Allophone` RENAME `Sound`;

DROP TABLE `LetterSoundCorrespondence_Sound`;
ALTER TABLE `LetterSoundCorrespondence_Allophone` RENAME `LetterSoundCorrespondence_Sound`;

DROP TABLE `Syllable_Sound`;
ALTER TABLE `Syllable_Allophone` RENAME `Syllable_Sound`;
