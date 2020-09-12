# README

The folders "server-prod" and "server-test" contain configuration files needed 
to run the web application. The directory structure here reflects the structure 
on the test/prod server.

When modifying these files on the test/prod server, remember to also update the 
corresponding files in this project.

For documentation of how to install Jetty/MySQL, see the Wiki.

To configure the web application to run on a new server, perform the following:

1. Add context file (e.g. webapp-eng.xml) to:

    * `$JETTY_HOME/webapps/`
    
2. Create database:

    1. `mysql -u root -p`
    1. ``MariaDB [(none)]> CREATE DATABASE `webapp-eng` CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;``
    1. ``MariaDB [(none)]> USE `webapp-eng`;``
    1. Create a database user.
    1. Grant all rights on the database to the newly created user.
    1. Import backup of existing database content (if any): `SOURCE webapp-eng-<date>.sql`
    
3. Configure CRON job for backup of database:
`
    1. Upload the files in `~/.elimu-ai/lang-eng/backup_<env>/`
    1. Add `mysql-backup-eng.sh` to crontab
    1. Add `remove-old-backups-eng.sh` to crontab

Scripts for deployment to production are located in 
`server-test/~/.elimu-ai/` (`deploy-test.sh` and `deploy-prod.sh`)
