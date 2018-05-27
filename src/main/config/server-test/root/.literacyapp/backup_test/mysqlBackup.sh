#!/bin/bash

# Navigate to the "backup" directory of the application
cd /root/.literacyapp/backup_test/

# Create directory 'database' if it does not already exist
if [ ! -d "database" ]; then
    mkdir database
    echo "directory '$(pwd)/database' was created"
fi

mysqldump -c -u literacyapp-user -p************ literacyapp > database/literacyapp_`date +%Y"-"%m"-"%d`.sql

# Remove files older than 15 days
find database -type f -mtime +14 -exec rm {} \;
