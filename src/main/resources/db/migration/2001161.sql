# 2.1.161

# Replace Audio transcription with title
UPDATE `Audio` SET `title` = `transcription`;
UPDATE `Audio` SET `transcription` = NULL;
