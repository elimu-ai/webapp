# 2.2.57

# Add platform to pre-existing peer-review events
UPDATE `WordPeerReviewEvent` SET `platform` = 'WEBAPP';
UPDATE `StoryBookPeerReviewEvent` SET `platform` = 'WEBAPP';
