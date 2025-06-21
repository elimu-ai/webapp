# 2.6.72

UPDATE LetterSoundAssessmentEvent SET letterSound_id = letterSoundId WHERE letterSoundId IS NOT NULL AND letterSound_id IS NULL AND EXISTS (SELECT 1 FROM LetterSound WHERE id = letterSoundId);
UPDATE LetterSoundLearningEvent SET letterSound_id = letterSoundId WHERE letterSoundId IS NOT NULL AND letterSound_id IS NULL AND EXISTS (SELECT 1 FROM LetterSound WHERE id = letterSoundId);
UPDATE WordAssessmentEvent SET word_id = wordId WHERE wordId IS NOT NULL AND word_id IS NULL AND EXISTS (SELECT 1 FROM Word WHERE id = wordId);
UPDATE WordLearningEvent SET word_id = wordId WHERE wordId IS NOT NULL AND word_id IS NULL AND EXISTS (SELECT 1 FROM Word WHERE id = wordId);
UPDATE NumberLearningEvent SET number_id = numberId WHERE numberId IS NOT NULL AND number_id IS NULL AND EXISTS (SELECT 1 FROM Number WHERE id = numberId);
UPDATE StoryBookLearningEvent SET storyBook_id = storyBookId WHERE storyBookId IS NOT NULL AND storyBook_id IS NULL AND EXISTS (SELECT 1 FROM StoryBook WHERE id = storyBookId);
UPDATE VideoLearningEvent SET video_id = videoId WHERE videoId IS NOT NULL AND video_id IS NULL AND EXISTS (SELECT 1 FROM Video WHERE id = videoId);
