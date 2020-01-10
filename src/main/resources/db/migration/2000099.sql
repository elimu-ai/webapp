# 2.0.99

ALTER TABLE SignOnEvent DROP COLUMN referralId;

ALTER TABLE Contributor DROP COLUMN referralId;
ALTER TABLE Contributor DROP COLUMN referrer;
ALTER TABLE Contributor DROP COLUMN utmSource;
ALTER TABLE Contributor DROP COLUMN utmMedium;
ALTER TABLE Contributor DROP COLUMN utmCampaign;
ALTER TABLE Contributor DROP COLUMN utmTerm;
