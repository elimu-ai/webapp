# 2.0.96

DELETE FROM VideoRevisionEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'AR');
DELETE FROM WordRevisionEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'AR');
DELETE FROM SignOnEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'AR');
DELETE FROM Contributor WHERE locale = 'AR';
