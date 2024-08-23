#!/bin/bash

VERSION=$1
echo "VERSION: $VERSION"

echo "Stopping Jetty..."
systemctl stop jetty

WAR_FILE_URL=https://jitpack.io/com/github/elimu-ai/webapp/webapp-$VERSION/webapp-webapp-$VERSION.war
echo "Downloading WAR file from $WAR_FILE_URL"
wget -O /opt/jetty-base/webapps/webapp.war $WAR_FILE_URL

echo "Starting Jetty"
systemctl start jetty
