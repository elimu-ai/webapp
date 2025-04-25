# 2.5.98

ALTER TABLE `Image` DROP COLUMN `checksumGitHub`;
ALTER TABLE `Image` CHANGE `cid` `checksumGitHub` VARCHAR(255);
