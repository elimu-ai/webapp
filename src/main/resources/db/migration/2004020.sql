# 2.4.20
ALTER TABLE `Device` DROP COLUMN `deviceId`;
ALTER TABLE `Device` CHANGE `deviceId` `androidId` VARCHAR(255);