# 2.2.4

# Increase comment length from 255 to 1000
ALTER TABLE WordContributionEvent MODIFY `comment` varchar(1000);
