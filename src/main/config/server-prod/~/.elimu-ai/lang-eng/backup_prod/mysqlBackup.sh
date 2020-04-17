#!/bin/bash

# Navigate to the backup directory of the web application
cd ~/.elimu-ai/backup_prod/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u webapp-eng-user -p************ webapp-eng > database/webapp-eng_`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the test server
DUMP_FILE=~/.elimu-ai/backup_prod/database/webapp-eng_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to test server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_TEST=~/.elimu-ai/backup_prod/database/webapp-eng_`date +%Y"-"%m"-"%d`.sql
echo "Copying to eng.test.elimu.ai:$DUMP_FILE_TEST"
scp $DUMP_FILE root@eng.test.elimu.ai:$DUMP_FILE_TEST
echo "Copy complete"

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;
