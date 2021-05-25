# 2.1.191

UPDATE `AudioContributionEvent` SET `platform` = 'WEBAPP' WHERE `platform` IS NOT NULL;
UPDATE `AudioPeerReviewEvent` SET `platform` = 'WEBAPP' WHERE `platform` IS NOT NULL;
