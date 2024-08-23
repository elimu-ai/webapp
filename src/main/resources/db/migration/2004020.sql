# 2.4.20

ALTER TABLE `Device` DROP COLUMN `androidId`;
ALTER TABLE `Device` CHANGE `deviceId` `androidId` VARCHAR(255) NOT NULL;
ALTER TABLE `Device` ADD CONSTRAINT `UK_c2646199whiqrkjbht7hwyr3v` UNIQUE (`androidId`);
