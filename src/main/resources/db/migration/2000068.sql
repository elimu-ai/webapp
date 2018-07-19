# 2.0.68

UPDATE AppCategory a SET project_id = (SELECT Project_id FROM Project_AppCategory WHERE appCategories_id = a.id);
