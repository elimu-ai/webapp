#!/bin/bash

# Navigate to the backup directory of the webapp
cd ~/.elimu-ai/backup_test/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u webapp-eng-user -p************ webapp-eng > database/webapp-eng_`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the prod server
DUMP_FILE=~/.elimu-ai/backup_test/database/webapp-eng_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to prod server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_PROD=~/.elimu-ai/backup_test/database/webapp-eng_`date +%Y"-"%m"-"%d`.sql
echo "Copying to eng.elimu.ai:$DUMP_FILE_PROD"
scp $DUMP_FILE root@eng.elimu.ai:$DUMP_FILE_PROD
echo "Copy complete"

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;
