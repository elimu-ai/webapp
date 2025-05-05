<content:title>
    Publish new APK file
</content:title>

<content:section cssId="applicationVersionCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="applicationVersion">
            <tag:formErrors modelAttribute="applicationVersion" />

            <form:hidden path="application" value="${applicationVersion.application.id}" />
            <form:hidden path="contributor" value="${contributor.id}" />
            
            <p>
                Package name: <code>${applicationVersion.application.packageName}</code>
            </p>

            <div class="divider" style="margin: 1.5em 0;"></div>

            <div class="row">
                <div class="input-field col s12">
                    <label for="fileUrl" class="active">APK file URL</label>
                    <input id="fileUrl" name="fileUrl" 
                            type="url"
                            placeholder="https://github.com/elimu-ai/sound-cards/releases/download/2.1.0/ai.elimu.soundcards-2.1.0.apk"
                            required="required" />
                </div>
            </div>
            
            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Publish <i class="material-icons right">send</i>
            </button>
        </form:form>
        <script>
            $(function() {
                $('form').on('submit', function() {
                    console.info('form on submit');
                    Materialize.toast('Processing APK. Please wait...', 4000, 'rounded');
                });
            });
        </script>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            Once published, the APK will become available for download through the <a href="https://github.com/elimu-ai/appstore" target="_blank">elimu.ai Appstore</a>.
        </p>
        <a href="https://github.com/elimu-ai/appstore" target="_blank">
            <img src="https://user-images.githubusercontent.com/15718174/84632262-39fec180-af21-11ea-8a8a-215120744f05.png" />
        </a>
    </div>
</content:aside>
