# README

The folders "server-prod" and "server-test" contain configuration files needed 
to run the web application. The directory structure here reflects the structure 
on the test/prod server.

When modifying these files on the test/prod server, remember to update the 
corresponding files in this project.

For documentation of how to install Jetty/MySQL, see the Wiki.

To configure the web application to run on a new server, perform the following:

1. Add context file (e.g. webapp-eng.xml) to:

    * Jetty: 8: `$JETTY_HOME/contexts/`
    * Jetty: 9: `$JETTY_HOME/webapps/`
    
2. Create database:

    1. ``CREATE DATABASE `webapp-eng` CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;``
    1. Create a database user.
    1. Grant all rights on the database to the newly created user.
    1. Import existing database content (if any): `SOURCE webapp-<date>.sql`
    
3. Configure CRON job for backup of database:

    1. Upload the files in `~/.elimu-ai/backup_<env>/`
    1. Add `mysqlBackup.cron` to crontab

Scripts for deployment to production are located in 
`server-test/~/.elimu-ai/` (`deploy-test.sh` and `deploy-prod.sh`)
