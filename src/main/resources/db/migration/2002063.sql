# 2.2.63

# Auto-fill missing packageNames
UPDATE StoryBookLearningEvent event SET packageName = (SELECT application.packageName FROM Application application WHERE application.id = event.application_id) WHERE packageName IS NULL;
