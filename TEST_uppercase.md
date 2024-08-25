## Test Instructions

### Unit testing ☑️

Run all tests:

    mvn clean test

Run individual tests:

    mvn clean test -D test=WordDaoTest

#### Code coverage 📊

[![codecov](https://codecov.io/gh/elimu-ai/webapp/branch/main/graph/badge.svg?token=T1F9OTQVOH)](https://codecov.io/gh/elimu-ai/webapp)

[![](https://codecov.io/gh/elimu-ai/webapp/branch/main/graphs/tree.svg?token=T1F9OTQVOH)](https://codecov.io/gh/elimu-ai/webapp)

    mvn test
    open target/site/jacoco/index.html

### Regression testing

#### REST API 

First, launch the webapp on localhost:

    mvn jetty:run

Then, in another terminal window run all the regression tests against the REST API:
    
    mvn verify -P regression-testing-rest

> [!TIP]
> If you want to run the tests against another URL, set the `base.url` system property:
> 
>     mvn verify -P regression-testing-rest -D base.url=https://eng.elimu.ai

#### UI

First, launch the webapp on localhost:

    mvn jetty:run

Then, in another terminal window run all the regression tests against the UI:
    
    mvn verify -P regression-testing-ui

> [!TIP]
> If you want to run the tests against another URL, set the `base.url` system property:
> 
>     mvn verify -P regression-testing-ui -D base.url=https://eng.elimu.ai

##### Headless 😶‍🌫️

If you don't want the automated test software to open browser windows, you can disable that by setting the `headless` system property:

    mvn verify -P regression-testing-ui -D headless=true

![](https://private-user-images.githubusercontent.com/1451036/361187317-35e99a19-f42d-4934-a0ba-f3d1e06ed6f6.png)

---

<p align="center">
  <img src="https://github.com/elimu-ai/webapp/blob/main/src/main/webapp/static/img/logo-text-256x78.png" />
</p>
<p align="center">
  elimu.ai - Free open-source learning software for out-of-school children 🚀✨
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

