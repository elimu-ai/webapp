# 1.1.59

ALTER TABLE Video_Letter DROP PRIMARY KEY;
ALTER TABLE Video_Letter ADD PRIMARY KEY (Video_id, letters_id);

ALTER TABLE Video_Number DROP PRIMARY KEY;
ALTER TABLE Video_Number ADD PRIMARY KEY (Video_id, numbers_id);

ALTER TABLE Video_Word DROP PRIMARY KEY;
ALTER TABLE Video_Word ADD PRIMARY KEY (Video_id, words_id);
