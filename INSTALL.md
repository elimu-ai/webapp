# How to Download and Run the Project 👩🏽‍💻

## Download project ⬇️

Clone `https://github.com/elimu-ai/webapp.git`

## Run application locally 🛠️

To be able to compile and execute the web application locally, you will first need to install the following:

* Java Development Kit (JDK)

  * macOS: `brew install openjdk`
  * Windows: Download from https://learn.microsoft.com/en-us/java/openjdk/download

* Maven

  * macOS: `brew install maven`
  * Windows: Download ZIP file from https://maven.apache.org/download.cgi

Go to the project's folder:

    cd webapp/

Compile the source code:

    mvn compile

Then, to run the application, type:
    
    mvn jetty:run

Next, to access the application in your browser, go to [http://localhost:8080/webapp](http://localhost:8080/webapp)

### Debug 🪲

> [!TIP]
> To run the application in debug mode, replace `mvn` with `mvnDebug` in the command above ☝️

## Test 🚨

See [`TEST.md`](./TEST.md)

## Prod server

    https://<language>.elimu.ai

> [!NOTE]
> See instructions for deploying the webapp on a production server in [`src/main/config/DEPLOY.md`](./src/main/config/DEPLOY.md).

## Supported languages 🌐

A list of the currently supported languages is available at https://github.com/elimu-ai/model/blob/main/src/main/java/ai/elimu/model/v2/enums/Language.java

> [!NOTE]
> The default language used during development is English (`ENG`). To switch to another language, edit the `content.language` property in [src/main/resources/config.properties](src/main/resources/config.properties).

---

<p align="center">
  <img src="https://github.com/elimu-ai/webapp/blob/main/src/main/webapp/static/img/logo-text-256x78.png" />
</p>
<p align="center">
  elimu.ai - Free open-source learning software for out-of-school children ✨🚀
</p>
<p align="center">
  <a href="https://elimu.ai">Website 🌐</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#readme">Wiki 📃</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/orgs/elimu-ai/projects?query=is%3Aopen">Projects 👩🏽‍💻</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki/milestones">Milestones 🎯</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#open-source-community">Community 👋🏽</a>
  &nbsp;•&nbsp;
  <a href="https://www.drips.network/app/drip-lists/41305178594442616889778610143373288091511468151140966646158126636698">Support 💜</a>
</p>
