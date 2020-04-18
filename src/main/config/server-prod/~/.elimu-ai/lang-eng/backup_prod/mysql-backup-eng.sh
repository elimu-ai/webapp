#!/bin/bash

LANGUAGE="eng"
echo "LANGUAGE: $LANGUAGE"

# Navigate to the backup directory of the webapp
cd ~/.elimu-ai/lang-$LANGUAGE/backup_prod/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u webapp-$LANGUAGE-user -p********** webapp-$LANGUAGE > database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the test server
DUMP_FILE=~/.elimu-ai/lang-$LANGUAGE/backup_prod/database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to test server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_TEST=~/.elimu-ai/lang-$LANGUAGE/backup_prod/database/webapp-$LANGUAGE-`date +%Y"-"%m"-"%d`.sql
echo "Copying to $LANGUAGE.test.elimu.ai:$DUMP_FILE_TEST"
scp $DUMP_FILE root@$LANGUAGE.test.elimu.ai:$DUMP_FILE_TEST
echo "Copy complete"

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;

