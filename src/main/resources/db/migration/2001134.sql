# 2.1.134

# Delete unused Image labels
DELETE FROM Image_Letter WHERE Image_id NOT IN (SELECT id FROM Image);
DELETE FROM Image_Number WHERE Image_id NOT IN (SELECT id FROM Image);
DELETE FROM Image_Word WHERE Image_id NOT IN (SELECT id FROM Image);
