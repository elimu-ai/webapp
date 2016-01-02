#!/bin/bash

# Navigate to the "backup" directory of the application
cd /root/.literacyapp/backup/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u literacyapp-user -p************ literacyapp > database/literacyapp_`date +%Y"-"%m"-"%d`.sql

# Remove files older than 15 days
find database -type f -mtime +14 -exec rm {} \;

# Copy the backup to the test server
DUMP_FILE=/root/.literacyapp/backup/database/literacyapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying latest DUMP file to test server... ($DUMP_FILE)"
echo "Time stamp: $(stat -c %y $DUMP_FILE)"
DUMP_FILE_TEST=/root/.literacyapp/backup_prod/database/literacyapp_`date +%Y"-"%m"-"%d`.sql
echo "Copying to astra2263:$DUMP_FILE_TEST"
scp $DUMP_FILE root@astra2263:$DUMP_FILE_TEST
echo "Copy complete"
