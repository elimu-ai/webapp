[![jitpack](https://jitpack.io/v/ai.elimu/webapp.svg)](https://jitpack.io/#ai.elimu/webapp)
[![maven build](https://github.com/elimu-ai/webapp/actions/workflows/maven-build.yml/badge.svg)](https://github.com/elimu-ai/webapp/actions/workflows/maven-build.yml)
[![codecov](https://codecov.io/gh/elimu-ai/webapp/branch/main/graph/badge.svg?token=T1F9OTQVOH)](https://codecov.io/gh/elimu-ai/webapp)
[![commits](https://img.shields.io/github/commit-activity/m/elimu-ai/webapp)](https://github.com/elimu-ai/webapp/commits)
[![last commit](https://img.shields.io/github/last-commit/elimu-ai/webapp)](https://github.com/elimu-ai/webapp/commits)
[![contributors](https://img.shields.io/github/contributors/elimu-ai/webapp)](https://github.com/elimu-ai/webapp/graphs/contributors)
[![closed issues](https://img.shields.io/github/issues-closed/elimu-ai/webapp)](https://github.com/elimu-ai/webapp/issues?q=is%3Aissue+is%3Aclosed)

# elimu.ai Webapp

Deployments in production:

 * http://hin.elimu.ai - Hindi database content
 * http://tgl.elimu.ai - Tagalog database content
 * http://tha.elimu.ai - Thai database content
 * http://vie.elimu.ai - Vietnamese database content
 * http://eng.elimu.ai - English database content

[<kbd>![](https://github.com/elimu-ai/webapp/assets/15718174/32f3c339-aacc-4dc1-9692-c9435bc63d57)</kbd>](http://hin.elimu.ai)

This web application hosts Android apps and educational content (e.g. texts, words, videos, storybooks) for each supported language. Via the [elimu.ai Appstore](https://github.com/elimu-ai/appstore) application, the educational apps and content are downloaded from the website and installed on Android devices.

## REST API

See [`src/main/java/ai/elimu/rest/`](src/main/java/ai/elimu/rest/)

The webapp's REST API is used by three Android applications:
  1. [elimu.ai Appstore](https://github.com/elimu-ai/appstore)
  1. [elimu.ai Content Provider](https://github.com/elimu-ai/content-provider)
  1. [elimu.ai Analytics](https://github.com/elimu-ai/analytics)

## Software architecture

See [elimu.ai Wiki: `SOFTWARE_ARCHITECTURE.md`](https://github.com/elimu-ai/wiki/blob/main/SOFTWARE_ARCHITECTURE.md)

## Run webapp locally 👩🏽‍💻

See [`INSTALL.md`](./INSTALL.md)

## Deploy webapp on production server 🚀

See [`src/main/config/DEPLOY.md`](./src/main/config/DEPLOY.md)

## Release 📦

See [`src/main/config/RELEASE.md`](./RELEASE.md)

## Localization

See [`LOCALIZE.md`](./LOCALIZE.md)

## Database migration

See [`DB_MIGRATION.md`](./DB_MIGRATION.md)

## Contributing guidelines

See [`CONTRIBUTING.md`](./CONTRIBUTING.md)

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
