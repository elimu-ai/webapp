#!/bin/bash
set -e

mariadb-dump webapp-ENG > ~/.elimu-ai/lang-ENG/backups/webapp-ENG_`date +%Y-%m-%d`.sql

# Delete backups older than 30 days
find ~/.elimu-ai/lang-ENG/backups -name "webapp-ENG_*.sql" -mtime +30 -delete
