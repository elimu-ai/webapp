READ ME
=======

The folders "server-prod" and "server-test" contain configuration files needed 
to run the web application. The directory structure here reflects the structure 
on the test/prod server.

When modifying these files on the test/prod server, remember to update the 
corresponding files in this project.

For documentation of how to install Jetty/MySQL, see Google Drive.

To configure the web application to run on a new server, perform the following:

    1. Add context file literacyapp.xml to:
        1.1. Jetty: 8: <Jetty installation path>/contexts/
        1.2. Jetty: 9: <Jetty installation path>/webapps/
    2. Create database:
        2.1 Execute commands in literacyapp.sql
        2.2 Import existing database (if any): SOURCE literacyapp-<date>.sql
    3. Configure CRON job for backup of database:
        3.1 Upload the files in /root/.literacyapp/backup/
        3.2 Add mysqlBackup.cron to crontab

Scripts for deployment to production are located in 
server-test/root/.literacyapp (deploy-test.sh and deploy-prod.sh)
