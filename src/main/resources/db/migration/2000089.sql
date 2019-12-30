# 2.0.89

DELETE FROM ApplicationVersion WHERE application_id IN (SELECT id FROM Application WHERE appGroup_id IS NOT NULL);
DELETE FROM Application WHERE appGroup_id IS NOT NULL;
