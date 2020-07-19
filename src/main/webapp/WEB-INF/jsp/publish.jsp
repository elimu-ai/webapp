<content:title>
    <fmt:message key="publish.your.educational.app" />
</content:title>

<content:section cssId="publishPage">
    <h2><content:gettitle /></h2>
    
    <p>
        Do you have (or wish to develop) an Android app which teaches literacy/numeracy skills to out-of-school children?
    </p>
    
    <p>
        We are building a platform of educational apps and games that teaches a child to read, write and perform 
        arithmetic fully autonomously and without the aid of a qualified teacher. This will help bring quality basic 
        education to the <a href="http://uis.unesco.org/en/news/new-education-data-sdg-4-and-more" target="_blank">64 
        million children</a> who are currently out of school.
    </p>
    
    <div class="row section">
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-nya.png' />" alt="Nya's Space Quest" style="border-radius: 8px;" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacy.png' />" alt="elimu.ai" style="border-radius: 8px;" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacy2.png' />" alt="elimu.ai" style="border-radius: 8px;" />
        </div>
    </div>
    
    <p>
        <i class="left material-icons medium deep-purple-text">grade</i>
        For each literacy and numeracy skill our machine learning algorithms detect the apps that are most 
        effective at teaching the children. The best-performing apps are then automatically promoted.
    </p>
    
    <h4><fmt:message key="instructions" /></h4>
    
    <ol class="browser-default">
        <li>Select <b>one</b> literacy/numeracy skill that your app/game will be teaching</li>
        <li>Create a new repository for the project on <a href="https://github.com/elimu-ai" target="_blank">GitHub</a></li>
        <li>Upload the source code (we will perform code reviews of your pull requests)</li>
        <li>Add <a href="https://github.com/elimu-ai/analytics">learning event reporting</a> to the source code</li>
        <li>Add build and release instructions for generating APK files</li>
        <li>Once quality approved, we release the app and initiate user testing and usage data collection</li>
        <li>Analyze usage data and update the software as needed</li>
    </ol>
    
    <p>
        If you have any questions, contact us at <a href="mailto:info@elimu.ai">info@elimu.ai</a> or via our 
        <a href="https://join.slack.com/t/elimu-ai/shared_invite/zt-eoc921ow-0cfjATlIF2X~zHhSgSyaAw">chat room (Slack)</a>.
    </p>
</content:section>

<content:aside>
    <h5 class="center">FAQ</h5>
    
    <ul class="browser-default">
        <li>
            <b>What type of license is required?</b><br />
            We require all code to be licensed under the <a href="https://opensource.org/licenses/MIT">MIT License</a> 
            or equivalent, creating a global public good available for anyone to use and build upon.<br />
            <br />
        </li>
        <li>
            <b>What languages should the app/game support?</b><br />
            As a minimum, the app/game should support English, and at least one of the following languages: 
            Bengali/Filipino/Hindi/Swahili/Urdu. 
            We target primary age children (about 6 to 11 years) who are out of school and who speak one of these 
            languages as their mother tongue. And we continuously <a href="https://elimu-ai.atlassian.net/wiki/spaces/DE/pages/191136000/How+to+Add+Support+for+a+New+Language" target="_blank">add support for more languages</a>.<br />
            <br />
        </li>
        <li>
            <b>Do I have to submit one APK file per language?</b><br />
            No. If the code is the same for all languages, you only need to submit one APK file. 
            For scalability (and ease of future maintenance), we encourage you to design your code to work with 
            multiple types of languages.<br />
            <br />
        </li>
        <li>
            <b>What content should I use in my app?</b><br />
            The elimu.ai platform offers a <a href="https://github.com/elimu-ai/content-provider">content provider 
            library</a> that will automatically provide educational content matching the current skill level of each 
            child.<br />
            <br />
        </li>
        <li>
            <b>Is Internet access allowed?</b><br />
            No, the devices will in many cases be used in areas where there is little or no Internet access, 
            so all software has to work offline. When configuring a new Android device, the 
            <a href="https://github.com/elimu-ai/appstore">elimu.ai Appstore</a> will take care of downloading and 
            installing all the necessary apps and content.<br />
            <br />
        </li>
        <li>
            <b>What devices are being used?</b><br />
            We are building our software for Android devices with 6"-10" displays installed with  
            Android API version 24 (7.0) or higher.<br />
            <br />
        </li>
    </ul>
</content:aside>
