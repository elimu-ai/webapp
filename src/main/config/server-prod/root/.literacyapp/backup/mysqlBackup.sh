#!/bin/bash

# Navigate to the "backup" directory of the application
cd /root/.literacyapp/backup/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u literacyapp-user -p************ literacyapp > database/literacyapp_`date +%Y"-"%m"-"%d`.sql

# Copy the backup to the test server
DUMP_FILE=/root/.literacyapp/backup/database/literacyapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to test server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_TEST=/root/.literacyapp/backup_prod/database/literacyapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying to luna344:$DUMP_FILE_TEST"
scp $DUMP_FILE root@luna344:$DUMP_FILE_TEST
echo "Copy complete"

# Remove files older than 7 days
find database -type f -mtime +6 -exec rm {} \;

