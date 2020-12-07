# 2.1.161

# Replace Audio transcription with title
ALTER TABLE `Audio` SET `title` = `transcription`;
ALTER TABLE `Audio` SET `transcription` = NULL;
