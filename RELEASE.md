# How to perform a release 📦

See [.github/workflows/maven-release.yml](.github/workflows/maven-release.yml)

> [!NOTE]
> To perform a release, go to https://github.com/elimu-ai/webapp/actions/workflows/maven-release.yml, and press "Run workflow."

![Run workflow](https://github.com/elimu-ai/wiki/assets/1451036/5bbfe03f-724c-4582-bc3c-411b763316db)

### Create release notes 🗒️

After the release job has completed, go to https://github.com/elimu-ai/webapp/tags and press "Create release."

![](https://github.com/user-attachments/assets/c1900762-6189-489a-a3b4-0678be468b92)

Then press "Generate release notes" to automatically include the merged pull requests. And press "Publish release."

## Usage

[![](https://jitpack.io/v/elimu-ai/webapp.svg)](https://jitpack.io/#elimu-ai/webapp)

After each release, the resulting `.war` file gets uploaded to https://jitpack.io/#elimu-ai/webapp

### Execute deployment script 🚀

1. Connect to the server via SSH
2. Execute the [`deploy-webapp.sh`](https://github.com/elimu-ai/webapp/blob/main/src/main/config/centos-stream-9/~/.elimu-ai/deploy-webapp.sh) script:
   ```
   # ~/.elimu-ai/deploy-webapp.sh 2.5.11
   ```

This will restart the web server with the new release version. To confirm that the deployment succeeded, you can check the release version in the webapp's footer.

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
