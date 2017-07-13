WARFILE=/var/lib/jenkins/jobs/webapp-2-precompile-jsps/workspace/target/literacyapp-SNAPSHOT.war
PROD_SERVER=malta2253
echo "Copying latest WAR file to prod server '$PROD_SERVER'... ($WARFILE)"
echo "Time stamp: $(stat -c %y $WARFILE)"

WARFILE_PROD=/tmp/literacyapp-SNAPSHOT.war
scp $WARFILE root@$PROD_SERVER:$WARFILE_PROD
echo "Copy complete"

ssh root@$PROD_SERVER echo "JETTY_HOME: $JETTY_HOME"
ssh root@$PROD_SERVER echo "JAVA_OPTIONS: $JAVA_OPTIONS"
ssh root@$PROD_SERVER echo "Stopping prod server..."
ssh root@$PROD_SERVER $JETTY_HOME/bin/jetty.sh stop
ssh root@$PROD_SERVER echo "Deploying WAR file: $WARFILE_PROD $(stat -c %y $WARFILE)"
ssh root@$PROD_SERVER cp $WARFILE_PROD $JETTY_HOME/webapps/literacyapp.war
ssh root@$PROD_SERVER echo "Starting prod server..."
ssh root@$PROD_SERVER $JETTY_HOME/bin/jetty.sh start

echo "Deploy complete"
