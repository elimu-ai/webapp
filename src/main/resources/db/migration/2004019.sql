ALTER TABLE `Device` DROP COLUMN `deviceId`;
ALTER TABLE `Device` CHANGE `deviceId` `androidId` VARCHAR(255);

ALTER TABLE `Device` DROP INDEX `UK_ktkbd0xm3q2nddw1xxtdaxjy7`;
ALTER TABLE `Device` ADD  CONSTRAINT `UK_ktkbd0xm3q2nddw1xxtdaxjy7` UNIQUE (`androidId`);