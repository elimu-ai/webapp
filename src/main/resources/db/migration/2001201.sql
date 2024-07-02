# 2.1.201

# Increase comment length from 255 to 1000
ALTER TABLE StoryBookPeerReviewEvent MODIFY `comment` varchar(1000);
