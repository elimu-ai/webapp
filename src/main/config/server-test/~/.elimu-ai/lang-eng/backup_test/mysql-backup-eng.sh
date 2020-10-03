#!/bin/bash

LANGUAGE="ENG"
echo "LANGUAGE: $LANGUAGE"

# Navigate to the backup directory of the webapp
cd ~/.elimu-ai/lang-$LANGUAGE/backup_test/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u webapp-$LANGUAGE-user -p********** webapp-$LANGUAGE > database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the prod server
DUMP_FILE=~/.elimu-ai/lang-$LANGUAGE/backup_test/database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to prod server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
echo "File size: $(($(stat -c%s $DUMP_FILE)/1024/1024)) MB"
DUMP_FILE_PROD=~/.elimu-ai/lang-$LANGUAGE/backup_test/database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql
echo "Copying to $LANGUAGE.elimu.ai:$DUMP_FILE_PROD"
scp $DUMP_FILE root@$LANGUAGE.elimu.ai:$DUMP_FILE_PROD
echo "Copy complete"

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;

