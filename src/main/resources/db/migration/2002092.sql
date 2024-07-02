# 2.2.92

# Delete orphaned Word entities
DELETE FROM `StoryBookParagraph_Word` WHERE `words_id` NOT IN (SELECT `id` FROM `Word`);
