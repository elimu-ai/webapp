# 2.1.13

DROP TABLE WordRevisionEvent;
DROP TABLE VideoRevisionEvent;
DROP TABLE SignOnEvent;
DELETE FROM Contributor WHERE id NOT IN (SELECT Contributor_id FROM Contributor_roles WHERE roles='ADMIN' OR roles='ANALYST');
