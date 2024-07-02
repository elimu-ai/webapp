# 2.2.64

# Add packageName to pre-existing StoryBookLearningEvents
UPDATE StoryBookLearningEvent event SET packageName = (SELECT application.packageName FROM Application application WHERE application.id = event.application_id);

# Add storyBookId to pre-existing StoryBookLearningEvents
UPDATE StoryBookLearningEvent event SET storyBookId = (SELECT sb.id FROM StoryBook sb WHERE sb.id = event.storyBook_id);

# Add storyBookTitle to pre-existing StoryBookLearningEvents
UPDATE StoryBookLearningEvent event SET storyBookTitle = (SELECT sb.title FROM StoryBook sb WHERE sb.id = event.storyBook_id);
