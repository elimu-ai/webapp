#!/bin/bash

LANGUAGE="ENG"

# Navigate to the backup directory of the webapp
cd ~/.elimu-ai/lang-${LANGUAGE}/backup_test/

# Remove files older than 30 days
find database -type f -mtime +29 -exec rm {} \;

