# 2.6.117

UPDATE IMAGE SET dominantColor = SUBSTRING(dominantColor, 5, LENGTH(dominantColor) - 5);
ALTER TABLE `Image` MODIFY `dominantColor` VARCHAR(255) NOT NULL;
