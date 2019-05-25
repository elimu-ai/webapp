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
        arithmetic fully autonomously and without the aid of a qualified teacher. This will help bring quality basic education to the 
        millions of children who are currently out of school.
    </p>
    
    <div class="row section">
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-nya.png' />" alt="Nya's Space Quest" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacy.png' />" alt="elimu.ai" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacy2.png' />" alt="elimu.ai" />
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
        <li>Add <a href="https://github.com/elimu-ai/analytics/tree/master/eventtracker">learning event tracking</a> to the source code</li>
        <li>Add build and release instructions for generating APK files</li>
        <li>Once quality approved, we release the app and initiate usage data collection</li>
        <li>Analyze usage data and update the software as needed</li>
    </ol>
    
    <p>
        If you have any questions, contact us at <a href="mailto:info@elimu.ai">info@elimu.ai</a> or via our <a href="http://slack.elimu.ai">chat room</a>.
    </p>
</content:section>

<content:aside>
    <h5 class="center">FAQ</h5>
    
    <ul class="browser-default">
        <li>
            <b>What type of license is required?</b><br />
            We require all code to be licensed under the <a href="https://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a> or equivalent, 
            creating a global public good available for anyone to use.<br />
            <br />
        </li>
        <li>
            <b>What languages should the app/game support?</b><br />
            The app/game should support English, Hindi and/or Swahili. We target 
            primary age children (about 6 to 11 years) who are out of school. 
            And we continuously <a href="https://elimu-ai.atlassian.net/wiki/spaces/DE/pages/191136000/How+to+Add+Support+For+a+New+Language" target="_blank">add support for more languages</a>.<br />
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
            We offer a <a href="https://github.com/elimu-ai/authentication/tree/master/contentprovider">content provider library</a> that will automatically provide educational content 
            matching the current skill level of each child.<br />
            <br />
        </li>
        <li>
            <b>Is Internet access allowed?</b><br />
            No, the devices will in many cases be used in areas where there is little or no Internet access, 
            so all software has to work offline.<br />
            <br />
        </li>
        <li>
            <b>What devices are being used?</b><br />
            We are building our software for Android devices with 6"-10" displays installed with  
            Android API version 21 (5.0) or higher.<br />
            <br />
        </li>
    </ul>
</content:aside>
