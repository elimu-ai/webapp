# 1.0.19
ALTER TABLE Contributor_teams DROP FOREIGN KEY FK_e80jgwq7foqvoiiesu32d972e;
ALTER TABLE Contributor_teams DROP INDEX FK_e80jgwq7foqvoiiesu32d972e;

ALTER TABLE Image DROP FOREIGN KEY FK_w6qjn592wrcwh7bqbamccorq;
ALTER TABLE Image DROP INDEX FK_w6qjn592wrcwh7bqbamccorq;

ALTER TABLE Number DROP FOREIGN KEY FK_palu7in8f8fajnbe7tutguydx;
ALTER TABLE Number DROP INDEX FK_palu7in8f8fajnbe7tutguydx;

ALTER TABLE Contributor_teams ENGINE=MyISAM;
ALTER TABLE Image ENGINE=MyISAM;
ALTER TABLE Number ENGINE=MyISAM;