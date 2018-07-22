<content:title>
    <fmt:message key="upload.new.apk.file" />
</content:title>

<content:section cssId="appCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="applicationVersion" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="applicationVersion" />
            
            <c:if test="${not empty applicationVersion.application}">
                <p>
                    <fmt:message key='package.name' />: ${applicationVersion.application.packageName}
                </p>
                <form:hidden path="application" value="${applicationVersion.application.id}" />
            </c:if>
            
            <div class="row">
                <div class="col s12">
                    <blockquote>
                        Once an APK file has been uploaded, reviewed and approved, it will become available for download via the <a href="https://github.com/elimu-ai/appstore" target="_blank">Appstore</a>.<br />
                        <b>Note:</b> APKs without measurement of <a href="<spring:url value='/publish' />" target="_blank">learning events</a> will not be approved.
                    </blockquote>
                    
                    <label>Learning event tracking</label><br />
                    <input type="checkbox" name="eventTrackingAdded" id="eventTrackingAdded" value="on" />
                    <label for="eventTrackingAdded">
                        Learning event tracking has been added to the APK
                    </label>
                    <script>
                        $(function() {
                            $('#eventTrackingAdded').on('change', function() {
                                console.info('#eventTrackingAdded on change');
                                
                                if ($(this).is(':checked')) {
                                    $('#fileUploadContainer').fadeIn();
                                } else {
                                    $('#fileUploadContainer').fadeOut();
                                }
                            });
                        });
                    </script>
                </div>
            </div>
            
            <div id="fileUploadContainer" style="padding: 3em 0; display: none;">
                <div class="col s6 offset-s3" style="padding: 3em; background: #F4F4F4; border: 2px dashed #CCC;">
                    <p class="center grey-text">
                        <fmt:message key="drop.your.apk.here.or.select.a.file" />
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
            </div>
        </form:form>
    </div>
</content:section>
