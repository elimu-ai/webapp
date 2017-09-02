<content:title>
    <fmt:message key="publish.your.educational.app" />
</content:title>

<content:section cssId="publishPage">
    <h2><content:gettitle /></h2>
    
    <p>
        Do you have (or wish to develop) an Android app which teaches literacy/numeracy skills to children?
    </p>
    
    <p>
        We are building a platform of educational apps and games that teaches a child to read, write and perform 
        arithmetic fully autonomously and without the aid of a teacher. This will help bring literacy to the 
        millions of children in developing countries who are currently out of school.
    </p>
    
    <div class="row section">
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-nya.png' />" alt="Nya's Space Quest" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacyapp.png' />" alt="elimu.ai" />
        </div>
        <div class="col s12 m4">
            <img src="<spring:url value='/static/img/publish-literacyapp2.png' />" alt="elimu.ai" />
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
        <li>Create a new repository for the project on GitHub</li>
        <li>Upload the source code (we will perform code reviews of your pull requests)</li>
        <li>Add event tracking to the source code</li>
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
            The app/game should support either English, Swahili or both. Our initial target 
            users are Swahili-speaking children aged 5-10 living in Tanzania and Kenya. 
            And we continuously add support for more languages.<br />
            <br />
        </li>
        <li>
            <b>Do I have to submit one APK file per language?</b><br />
            If the code is the same for all languages, you only need to submit one APK file.<br />
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
            No, the tablets will in many cases be used in areas where there is little or no Internet access, 
            so all software has to work offline.<br />
            <br />
        </li>
        <li>
            <b>What devices are being used?</b><br />
            We are building our software for tablets with 5"-10" displays installed with  
            Android API 21 (5.0) or higher.<br />
            <br />
        </li>
    </ul>
</content:aside>
