# Contributing

## Introduction

As an outside collaborator, you are free to fork the repository and create pull requests from your fork. 

However, as a core contributor who has been added to the [GitHub organization](https://github.com/elimu-ai), 
the work flow looks like this:

## Work Flow

1. Select a GitHub issue to work on. Either select an existing issue from https://github.com/elimu-ai/webapp/projects 
or create a new one.

2. Create a branch for the GitHub issue. For example, if your GitHub issue is titled "Update Java version [2-4h] #567", 
create a new branch with the title "#567 Update Java version" by pressing the branch button (or by using GitHub Desktop):

   ![screen shot 2017-11-11 at 11 09 21](https://user-images.githubusercontent.com/1451036/32688430-d1b9fc88-c6d0-11e7-8e20-10a10c028d0a.png) .  ![screen shot 2017-11-11 at 11 10 56](https://user-images.githubusercontent.com/1451036/32688437-12fc5f6a-c6d1-11e7-9a38-b34479356522.png)

3. Switch to the branch you created and implement your code changes on that branch. Remember to include the GitHub issue 
for each commit to make it easier for future contributors to understand each code change.

4. Once ready for testing, create a pull request for your branch for merging it into the `master` branch. Your pull 
request needs at least one approved review in order to be merged. When assigning reviewers, add one or more of the project's 
maintainers:

   * jo-elimuai
   * sladomic
   
   If the maintainers are too slow to get back to you, contact them in the [Slack channel](https://elimu-ai.slack.com/messages/C0LDBLX3J/details/).
   
5. Once your pull request has been approved by at least one project maintainer, press the "merge" button. This will merge 
your code changes into the `master` branch and deploy them to the test server at http://test.elimu.ai.
