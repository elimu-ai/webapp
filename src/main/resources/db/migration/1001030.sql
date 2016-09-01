# 1.1.30

UPDATE Content SET contentStatus = 'ACTIVE';

UPDATE Application_literacySkills SET literacySkills = 'PHONEMIC_AWARENESS' WHERE literacySkills = 'SOUND_IDENTIFICATION';
UPDATE Application_literacySkills SET literacySkills = 'LETTER_IDENTIFICATION' WHERE literacySkills = 'GRAPHEME_KNOWLEDGE';
UPDATE Application_literacySkills SET literacySkills = 'NONWORD_READING' WHERE literacySkills = 'INVENTED_WORD_DECODING';
UPDATE Application_literacySkills SET literacySkills = 'ORAL_READING_FLUENCY' WHERE literacySkills = 'ORAL_PASSAGE_READING';
UPDATE Application_literacySkills SET literacySkills = 'DICTATION' WHERE literacySkills = 'SENTENCE_WRITING';
UPDATE Application_literacySkills SET literacySkills = 'MAZE_CLOZE' WHERE literacySkills = 'READING_COMPREHENSION';

UPDATE Multimedia_literacySkills SET literacySkills = 'PHONEMIC_AWARENESS' WHERE literacySkills = 'SOUND_IDENTIFICATION';
UPDATE Multimedia_literacySkills SET literacySkills = 'LETTER_IDENTIFICATION' WHERE literacySkills = 'GRAPHEME_KNOWLEDGE';
UPDATE Multimedia_literacySkills SET literacySkills = 'NONWORD_READING' WHERE literacySkills = 'INVENTED_WORD_DECODING';
UPDATE Multimedia_literacySkills SET literacySkills = 'ORAL_READING_FLUENCY' WHERE literacySkills = 'ORAL_PASSAGE_READING';
UPDATE Multimedia_literacySkills SET literacySkills = 'DICTATION' WHERE literacySkills = 'SENTENCE_WRITING';
UPDATE Multimedia_literacySkills SET literacySkills = 'MAZE_CLOZE' WHERE literacySkills = 'READING_COMPREHENSION';
