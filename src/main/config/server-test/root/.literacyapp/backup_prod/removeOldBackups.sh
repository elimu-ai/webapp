#!/bin/bash

# Navigate to the "backup" directory of the application
cd /root/.literacyapp/backup_prod/

# Remove files older than 14 days
find database -type f -mtime +13 -exec rm {} \;
