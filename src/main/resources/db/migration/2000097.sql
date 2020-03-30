# 2.0.97

DELETE FROM VideoRevisionEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'ES');
DELETE FROM VideoRevisionEvent WHERE video_id IN (SELECT id FROM Video WHERE locale = 'ES');
DELETE FROM WordRevisionEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'ES');
DELETE FROM WordRevisionEvent WHERE word_id IN (SELECT id FROM Word WHERE locale = 'ES');
DELETE FROM SignOnEvent WHERE contributor_id IN (SELECT id FROM Contributor WHERE locale = 'ES');
DELETE FROM Contributor WHERE locale = 'ES';
DELETE FROM Device WHERE locale = 'ES';
DELETE FROM Letter WHERE locale = 'ES';
DELETE FROM Number WHERE locale = 'ES';
