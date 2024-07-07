# Deploy 🚀

> Instructions for deploying the webapp on a production server.

## Operating System 🔆

In this example we will be using [CentOS](https://www.centos.org), but the same installation steps should work on most UNIX operating systems.

Version: `CentOS 9 Stream`

## Jetty Web Server 🖥️

We use [Eclipse Jetty](https://jetty.org/) as our web server.

Version: `Jetty 10.0.21`

## Jetty Maven Plugin 🪶

If you need to use a different Jetty version than us, the `jetty-maven-plugin` version in [`pom.xml`](./pom.xml) should match the Jetty version that you will be using on your production server. See [Using the Jetty Maven Plugin](https://jetty.org/docs/jetty/10/programming-guide/maven-jetty/jetty-maven-plugin.html) for more details.

> [!TIP]
> If you will be modifying any of the webapp code before deploying it on a production server, it's recommended that you create a fork of the GitHub repository first.

## Download Jetty ⬇️

You can find the download links for each release at https://jetty.org/download.html

On your production server, go to the temporary folder, and download the release:

    cd /tmp
    wget https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-home/10.0.21/jetty-home-10.0.21.tar.gz
