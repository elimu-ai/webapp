echo "JETTY_HOME: $JETTY_HOME"
echo "JAVA_OPTIONS: $JAVA_OPTIONS"
echo "Stopping test server..."
$JETTY_HOME/bin/jetty.sh stop

WARFILE=/var/lib/jenkins/jobs/webapp-2-precompile-jsps/workspace/target/literacyapp-SNAPSHOT.war
echo "Deploying latest WAR file... ($WARFILE)"
echo "Time stamp: $(stat -c %y $WARFILE)"
cp $WARFILE $JETTY_HOME/webapps/literacyapp.war

echo "Starting test server..."
$JETTY_HOME/bin/jetty.sh start
