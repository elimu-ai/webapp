# 2.4.20

ALTER TABLE `Device` DROP COLUMN `androidId`;
ALTER TABLE `Device` CHANGE `deviceId` `androidId` VARCHAR(255);
