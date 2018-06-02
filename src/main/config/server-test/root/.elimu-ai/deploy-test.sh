echo "JETTY_HOME: $JETTY_HOME"
echo "JAVA_OPTIONS: $JAVA_OPTIONS"
echo "Stopping test server..."
$JETTY_HOME/bin/jetty.sh stop

WARFILE=/var/lib/jenkins/workspace/webapp-2-precompile-jsps/target/webapp-SNAPSHOT.war
echo "Deploying latest WAR file... ($WARFILE)"
echo "Time stamp: $(stat -c %y $WARFILE)"
echo "File size: $(($(stat -c %s $WARFILE)/1024/1024)) MB"
cp $WARFILE $JETTY_HOME/webapps/webapp.war

echo "Starting test server..."
$JETTY_HOME/bin/jetty.sh start
