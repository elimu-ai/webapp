#!/bin/bash

# Navigate to the backup directory of the webapp
cd ~/.elimu-ai/lang-ENG/backup_prod/

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;

