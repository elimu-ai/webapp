# 2.2.14

# Change timeSpentMs from NULL to NOT NULL
UPDATE `StoryBookContributionEvent` SET `timeSpentMs` = 0 WHERE `timeSpentMs` IS NULL;
ALTER TABLE `StoryBookContributionEvent` MODIFY `timeSpentMs` bigint(20) NOT NULL;

# Add platform to pre-existing contribution events
UPDATE `NumberContributionEvent` SET `platform` = 'WEBAPP';
UPDATE `StoryBookContributionEvent` SET `platform` = 'WEBAPP';
UPDATE `WordContributionEvent` SET `platform` = 'WEBAPP';
