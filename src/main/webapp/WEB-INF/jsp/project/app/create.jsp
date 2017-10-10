<content:title>
    <fmt:message key="upload.new.apk.file" />
</content:title>

<content:section cssId="appCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel" style="padding: 3em 0;">
        <form:form modelAttribute="applicationVersion" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="applicationVersion" />
            
            <div class="col s6 offset-s3" style="padding: 3em; background: #F4F4F4; border: 2px dashed #CCC;">
                <p class="center grey-text">
                    Drop your APK file here, or select a file.
                </p>
                <div class="file-field input-field col s8 offset-s2 center">
                    <div class="btn deep-purple">
                        <span><fmt:message key='file' /></span>
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
                            });
                        });
                    </script>
                </div>
                
            </div>
            <div style="clear: both;"></div>
        </form:form>
    </div>
</content:section>
