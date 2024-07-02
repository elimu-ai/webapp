# 2.2.10

# Increase comment length from 255 to 1000
ALTER TABLE AudioPeerReviewEvent MODIFY `comment` varchar(1000);
ALTER TABLE AudioContributionEvent MODIFY `comment` varchar(1000);
ALTER TABLE NumberContributionEvent MODIFY `comment` varchar(1000);
ALTER TABLE StoryBookContributionEvent MODIFY `comment` varchar(1000);
ALTER TABLE WordPeerReviewEvent MODIFY `comment` varchar(1000);
