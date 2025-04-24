#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status

VERSION=$1

if [[ -z "$VERSION" ]]; then
  echo "Error: VERSION is required."
  exit 1
fi

echo "VERSION: $VERSION"

WAR_FILE_URL="https://jitpack.io/ai/elimu/webapp/webapp-$VERSION/webapp-webapp-$VERSION.war"
TMP_WAR_FILE="/tmp/webapp-$VERSION.war"
TARGET_WAR_FILE="/opt/jetty-base/webapps/webapp.war"

echo "Downloading WAR file from $WAR_FILE_URL"

# Download the WAR file to a temporary location
if ! wget --tries=3 --timeout=10 -O "$TMP_WAR_FILE" "$WAR_FILE_URL"; then
  echo "Error: Failed to download WAR file. Jetty is not stopped."
  exit 1
fi

echo "Download completed successfully."

echo "Stopping Jetty..."
systemctl stop jetty

echo "Deploying new WAR file..."
mv -f "$TMP_WAR_FILE" "$TARGET_WAR_FILE"

echo "Starting Jetty..."
systemctl start jetty

echo "Deployment completed successfully."
