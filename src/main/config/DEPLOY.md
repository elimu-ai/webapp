# Deploy 🚀

> Instructions for deploying the webapp on a production server.

## Operating System 🔆

In this example we will be using [CentOS](https://www.centos.org), but the same installation steps should work on most UNIX operating systems.

Version: `CentOS 9 Stream`

> [!NOTE]
> You can find the configuration files for this operating system in the [`centos-stream-9`](./centos-stream-9/) folder.

## Java Virtual Machine (JVM) ☕

Install Java 17 or newer. We will be using [OpenJDK](https://openjdk.org/), but you can also use the JDK from [Oracle](https://www.oracle.com/java/).

Version: `17`

    yum install java-17-openjdk

## Jetty Web Server 🖥️

We use [Eclipse Jetty](https://jetty.org/) as our web server.

Version: `Jetty 10.0.22` (requires Java 11 or newer)

### Jetty Maven Plugin 🪶

If you need to use a different Jetty version than us, the `jetty-maven-plugin` version in [`pom.xml`](../../../pom.xml) should match the Jetty version that you will be using on your production server. See [Using the Jetty Maven Plugin](https://jetty.org/docs/jetty/10/programming-guide/maven-jetty/jetty-maven-plugin.html) for more details.

> [!TIP]
> If you will be modifying any of the webapp code before deploying it on a production server, it's recommended that you create a fork of the GitHub repository first.

### Download Jetty ⬇️

You can find the download links for each release at https://jetty.org/download.html

On your production server, go to the temporary folder, and download the release:

    cd /tmp
    wget https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-home/10.0.22/jetty-home-10.0.22.tar.gz

Extract the archive:

    tar -zxvf jetty-home-10.0.22.tar.gz

Move the folder to `/opt`:

    mv jetty-home-10.0.22 /opt/

### Configure Jetty ⚙️

#### System Startup

Add the `jetty` service:

    ln -s /opt/jetty-home-10.0.22/bin/jetty.sh /etc/rc.d/init.d/jetty

Enable the `jetty` server on system init:
    
    yum install chkconfig
    chkconfig --add jetty

> [!TIP]
> Use `chkconfig --list` to verify that the `jetty` has been added.

#### Create $JETTY_BASE

Create the folder that will contain Jetty configuration and `*.war` files:

    mkdir /opt/jetty-base

Add Jetty modules:

    cd /opt/jetty-base/
    java -jar /opt/jetty-home-10.0.22/start.jar --add-module=server,http,deploy,jsp

#### Set Default Values

Copy the content from [`centos-stream-9/etc/default/jetty`](./centos-stream-9/etc/default/jetty) into the Jetty context file:

    vi /etc/default/jetty

Copy the port number configuration from [`centos-stream-9/opt/jetty-base/start.d/http.ini`](./centos-stream-9/opt/jetty-base/start.d/http.ini) into the configuration file of Jetty's `http` module:

    vi /opt/jetty-base/start.d/http.ini

```diff
## The port the connector listens on.
-# jetty.http.port=8080
+jetty.http.port=80
```

#### Add Web Application Archive (WAR)

Add the latest release of your `*.war` file to `/opt/jetty-base/webapps/`. In our case, we download WAR releases from an external URL:

    wget -O /opt/jetty-base/webapps/webapp.war https://jitpack.io/com/github/elimu-ai/webapp/webapp-2.4.25/webapp-webapp-2.4.25.war

> [!IMPORTANT]
> The WAR file's name must match the context file's name, e.g. `webapp.war` and `webapp.xml`.

#### Add Jetty Context XML File

Copy the content from [`webapp.xml`](./centos-stream-9/opt/jetty-base/webapps/webapp.xml) into the Jetty context file:

    vi /opt/jetty-base/webapps/webapp.xml

> [!NOTE]
> If your deployment is for another language than English (language code `ENG`), set the `content_language` attribute to your language code.

### Start Jetty

Start the `jetty` service:

    systemctl jetty

> [!TIP]
> To verify that everything has been configured correctly, you can run `cd /opt/jetty-base/; java -jar /opt/jetty-home-10.0.22/start.jar --list-config` and `systemctl status jetty`

## MariaDB Database 🛢️

### Install MariaDB

Configure a YUM repo entry for your OS at https://mariadb.org/download/?t=repo-config.

Copy and paste the YUM repo entry into a file under `/etc/yum.repos.d/`:

    vi /etc/yum.repos.d/MariaDB.repo

Version: `11.4.3`

Install MariaDB:

    yum install MariaDB-server MariaDB-client

### Start MariaDB

Start the MariaDB service:

    service mariadb start

> [!TIP]
> To verify that MariaDB is running, use this command: `systemctl status mariadb`

### Enable System Boot

Enable MariaDB to start at boot:

    systemctl enable mariadb

### Secure Installation

Secure your MariaDB installation:

    mariadb-secure-installation

☑️ Set root password: ***
🟪 Switch to unix_socket authentication [Y/n] n
☑️ Change the root password? [Y/n] n
☑️ Remove anonymous users? [Y/n] Y
☑️ Disallow root login remotely? [Y/n] Y
☑️ Remove test database and access to it? [Y/n] Y
☑️ Reload privilege tables now? [Y/n] Y

### Make UTF-8 the Default Character Set

Copy the content from [`my.cnf`](./centos-stream-9/etc/my.cnf) and append it to the MariaDB config file:

    vi /etc/my.cnf

For the character set changes to come into effect, restart the MariaDB server:

    systemctl restart mariadb

> [!TIP]
> To verify that the variables were set correctly, log into the MariaDB Server, and use these commands:
>
>     > SHOW VARIABLES LIKE '%character_set%';
>     > SHOW VARIABLES LIKE '%collation%';
>     > SHOW VARIABLES LIKE '%init_connect%';

### Create Database

Log into the MariaDB Server:

    mariadb -u root -p

Create a new database:

```sql
CREATE DATABASE `webapp-ENG` CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
```

Create a database user:

```sql
USE `webapp-ENG`;
CREATE USER '**********'@'localhost' IDENTIFIED BY '**********';
GRANT ALL ON `webapp-ENG`.* TO '**********'@'localhost';
```

### Backup Database

Create a backup:

```bash
mariadb-dump webapp-ENG > webapp-ENG_`date +%Y"-"%m"-"%d`.sql
```

Restore database from a backup:

```bash
mariadb webapp-ENG < webapp-ENG_2024-08-20.sql
```
