# 2.1.231

DROP TABLE `LetterSoundCorrespondence`;
ALTER TABLE `LetterToAllophoneMapping` RENAME `LetterSoundCorrespondence`;

DROP TABLE `LetterSoundCorrespondence_Allophone`;
ALTER TABLE `LetterToAllophoneMapping_Allophone` RENAME `LetterSoundCorrespondence_Allophone`;

DROP TABLE `LetterSoundCorrespondence_Letter`;
ALTER TABLE `LetterToAllophoneMapping_Letter` RENAME `LetterSoundCorrespondence_Letter`;
