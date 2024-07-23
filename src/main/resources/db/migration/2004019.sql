# 2.4.19
ALTER TABLE `Device` DROP COLUMN `deviceId`;
ALTER TABLE `Device` CHANGE `deviceId` `androidId` VARCHAR(255);