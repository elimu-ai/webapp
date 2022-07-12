# How to Download and Run the Project 👩🏽‍💻

## Download project

Clone `https://github.com/elimu-ai/webapp.git`

## Run application locally

To be able to compile and execute the web application locally, you will first need to install the following:

* Java Development Kit (JDK)

  * macOS: `brew install openjdk`

* Maven

  * macOS: `brew install maven`

Go to the project's folder:

    cd webapp/

Then, to run the application, type:
    
    mvn clean test jetty:run

Next, to access the application in your browser, go to [http://localhost:8080/webapp](http://localhost:8080/webapp)

### Debug 🪲

To run the application in debug mode, replace `mvn` with `mvnDebug` in the command above ☝️


## Code Coverage

[![](https://codecov.io/gh/elimu-ai/webapp/branch/main/graphs/tree.svg?token=T1F9OTQVOH)](https://codecov.io/gh/elimu-ai/webapp)

```
mvn test
open target/site/jacoco/index.html 
```


## Test server

    https://<language>.test.elimu.ai


## Prod server

    https://<language>.elimu.ai


## Supported languages 🇺🇸🇵🇭🇮🇳🇹🇿

A list of the currently supported languages is available at https://github.com/elimu-ai/model/blob/main/src/main/java/ai/elimu/model/enums/Language.java

The default language used during development is English (`ENG`). To switch to another language, edit the `content.language` property in [src/main/resources/config.properties](src/main/resources/config.properties).

## Contributing guidelines

For guidelines on how to work on issues, see [CONTRIBUTING.md](CONTRIBUTING.md)
