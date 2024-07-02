# 2.2.97

# Delete orphaned number words
DELETE FROM Number_Word WHERE number_id NOT IN (SELECT id FROM Number);
