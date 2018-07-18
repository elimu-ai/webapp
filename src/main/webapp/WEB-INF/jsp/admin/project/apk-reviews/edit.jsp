<content:title>
    APK review
</content:title>

<content:section cssId="apkReviewPage">
    <h4><content:gettitle /></h4>

    <div class="card-panel">
        <div class="row">
            <div class="col s12">
                <table class="bordered highlight">
                    <thead>
                        <th><fmt:message key="package.name" /></th>
                        <th><fmt:message key="version.code" /></th>
                        <th><fmt:message key="file.size" /></th>
                        <th><fmt:message key="time.uploaded" /></th>
                        <th><fmt:message key="contributor" /></th>
                        <th><fmt:message key="status" /></th>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <a href="<spring:url value="/project/${applicationVersion.application.project.id}/app-category/${applicationVersion.application.appGroup.appCategory.id}/app-group/${applicationVersion.application.appGroup.id}/app/${applicationVersion.application.id}/edit" />">
                                    ${applicationVersion.application.packageName}
                                </a>
                            </td>
                            <td>${applicationVersion.versionCode}</td>
                            <td><fmt:formatNumber value="${applicationVersion.fileSizeInKb / 1024}" maxFractionDigits="2" />MB</td>
                            <td><fmt:formatDate value="${applicationVersion.timeUploaded.time}" type="both" timeStyle="short" /></td>
                            <td>
                                <div class="chip">
                                    <img src="<spring:url value='${applicationVersion.contributor.imageUrl}' />" alt="${applicationVersion.contributor.firstName}" /> 
                                    <c:out value="${applicationVersion.contributor.firstName}" />&nbsp;<c:out value="${applicationVersion.contributor.lastName}" />
                                </div>
                            </td>
                            <td>${applicationVersion.applicationVersionStatus}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
                        
        <blockquote>
            Select a new APK status representing the result of your review. If you do not approve an APK file, 
            you also need to type a comment explaining why not.
        </blockquote>
        <form method="POST">
            <div class="row">
                <div class="input-field col s12">
                    <select id="applicationVersionStatus" name="applicationVersionStatus">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="applicationVersionStatus" items="${applicationVersionStatuses}">
                            <option value="${applicationVersionStatus}" <c:if test="${applicationVersionStatus == param.applicationVersionStatus}">selected="selected"</c:if>>${applicationVersionStatus}</option>
                        </c:forEach>
                    </select>
                    <label for="applicationVersionStatus"><fmt:message key="status" /></label>
                    <script>
                        $(function() {
                            $('#applicationVersionStatus').on('change', function() {
                                console.info('#applicationVersionStatus on change');
                                
                                var applicationVersionStatus = $(this).val();
                                console.info("applicationVersionStatus: " + applicationVersionStatus);
                                
                                if (applicationVersionStatus == "NOT_APPROVED") {
                                    $('#commentContainer').fadeIn();
                                } else {
                                    $('#commentContainer').fadeOut();
                                }
                            });
                        });
                    </script>
                </div>

                <div id="commentContainer" class="input-field col s12" style="display: none;">
                    <textarea id="comment" name="comment" class="materialize-textarea" maxlength="255"><c:if test="${not empty param.comment}">${param.comment}</c:if></textarea>
                    <label for="comment"><fmt:message key="comment" /> *</label>
                </div>
            </div>
            
            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="save" /> <i class="material-icons right">send</i>
            </button>
        </form>
    </div>
</content:section>
