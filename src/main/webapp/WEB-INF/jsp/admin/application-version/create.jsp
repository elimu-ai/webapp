<content:title>
    Upload new APK file
</content:title>

<content:section cssId="applicationVersionCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="applicationVersion" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="applicationVersion" />
            
            <p>
                Package name: <code>${applicationVersion.application.packageName}</code>
            </p>

            <div class="row">
                <form:hidden path="application" value="${applicationVersion.application.id}" />
                <form:hidden path="contributor" value="${contributor.id}" />
            </div>
            
            <div class="col s12" style="padding: 3em; background: #F4F4F4; border: 2px dashed #CCC; border-radius: 8px;">
                <p class="center grey-text">
                    Drag & drop your APK file here, or select a file.
                </p>
                <div class="file-field input-field col s8 offset-s2 center">
                    <div class="btn">
                        <span>File</span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                    <script>
                        $(function() {
                            $('#bytes').on('change', function() {
                                console.info('#bytes on change');

                                $(this).parents('form').submit();
                                Materialize.toast('Processing APK. Please wait...', 4000, 'rounded');
                            });
                        });
                    </script>
                </div>
            </div>
            <div style="clear: both;"></div>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        Once uploaded, the APK will become available for download to an Android device through the Appstore application:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/appstore" target="_blank">elimu.ai Appstore</a>
            </li>
        </ol>
    </div>
</content:aside>
