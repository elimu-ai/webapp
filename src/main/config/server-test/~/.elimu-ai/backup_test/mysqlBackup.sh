#!/bin/bash

# Navigate to the backup directory of the web application
cd /root/.elimu-ai/backup_test/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u literacyapp-user -p************ literacyapp > database/webapp_`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the prod server
DUMP_FILE=/root/.elimu-ai/backup_test/database/webapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to prod server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_PROD=/root/.elimu-ai/backup_test/database/webapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying to elimu.ai:$DUMP_FILE_PROD"
scp $DUMP_FILE root@elimu.ai:$DUMP_FILE_PROD
echo "Copy complete"

# Remove files older than 15 days
find database -type f -mtime +14 -exec rm {} \;
