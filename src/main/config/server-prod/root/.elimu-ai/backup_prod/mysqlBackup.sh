#!/bin/bash

# Navigate to the backup directory of the web application
cd /root/.elimu-ai/backup_prod/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u literacyapp-user -p************ literacyapp > database/webapp_`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the test server
DUMP_FILE=/root/.elimu-ai/backup_prod/database/webapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to test server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_TEST=/root/.elimu-ai/backup_prod/database/webapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying to test.elimu.ai:$DUMP_FILE_TEST"
scp $DUMP_FILE root@test.elimu.ai:$DUMP_FILE_TEST
echo "Copy complete"

# Remove files older than 5 days
find database -type f -mtime +4 -exec rm {} \;
