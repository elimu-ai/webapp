#!/bin/bash

# Navigate to the backup directory of the web application
cd /root/.elimu-ai/backup_prod/

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;
