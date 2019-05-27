<content:title>
    <fmt:message key="edit.application" />
</content:title>

<content:section cssId="applicationEditPage">
    <h4><content:gettitle /></h4>
    <c:if test="${empty applicationVersions}">
        <div class="card-panel amber lighten-3">
            <b>Note:</b> The application will not be active until you upload a corresponding APK file.
        </div>
    </c:if>
    <div class="card-panel">
        <form:form modelAttribute="application">
            <tag:formErrors modelAttribute="application" />

            <div class="row">
                <form:hidden path="id" value="${application.id}" />
                <form:hidden path="locale" value="${application.locale}" />
                <form:hidden path="contributor" value="${application.contributor.id}" />
                <div class="input-field col s6">
                    <fmt:message key='package.name' />: ${application.packageName}
                    <form:hidden path="packageName" value="${application.packageName}" />
                </div>
                <div class="input-field col s4">
                    <select id="applicationStatus" name="applicationStatus">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="applicationStatus" items="${applicationStatuses}">
                            <option value="${applicationStatus}" <c:if test="${applicationStatus == application.applicationStatus}">selected="selected"</c:if>><c:out value="${applicationStatus}" /></option>
                        </c:forEach>
                    </select>
                    <label for="applicationStatus"><fmt:message key="status" /></label>
                </div>
                <div class="input-field col s2">
                    <button id="submitButton" disabled="disabled" class="btn disabled waves-effect waves-light" type="submit">
                        <fmt:message key="edit" /> <i class="material-icons right">send</i>
                    </button>
                    <script>
                        $(function() {
                            $('#applicationStatus').on("change", function() {
                                console.info('applicationStatus on change');
                                
                                var applicationStatus = $(this).val();
                                console.info('applicationStatus: ' + applicationStatus);
                                if (applicationStatus == "DELETED") {
                                    $('#submitButton').removeAttr("disabled");
                                    $('#submitButton').removeClass("disabled");
                                }
                            });
                        });
                    </script>
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <h5><fmt:message key="application.versions" /></h5>
                    <p>
                        <a href="<spring:url value="/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/app/create?applicationId=${application.id}" />"><i class="material-icons left">file_upload</i><fmt:message key='upload.new.apk.file' /></a>
                    </p>
                    <c:if test="${not empty applicationVersions}">
                        <table class="bordered highlight">
                            <thead>
                                <th><fmt:message key="label" /></th>
                                <th><fmt:message key="version.code" /></th>
                                <th><fmt:message key="version.name" /></th>
                                <th><fmt:message key="file.size" /></th>
                                <th>minSdkVersion</th>
                                <th><fmt:message key="status" /></th>
                                <th><fmt:message key="time.uploaded" /></th>
                                <th><fmt:message key="contributor" /></th>
                                <th><i class="material-icons">cloud_download</i></th>
                            </thead>
                            <tbody>
                                <c:forEach var="applicationVersion" items="${applicationVersions}">
                                    <tr>
                                        <td>${applicationVersion.label}</td>
                                        <td>${applicationVersion.versionCode}</td>
                                        <td>${applicationVersion.versionName}</td>
                                        <td><fmt:formatNumber value="${applicationVersion.fileSizeInKb / 1024}" maxFractionDigits="2" />MB</td>
                                        <td><c:out value="${applicationVersion.minSdkVersion}" /></td>
                                        <td>${applicationVersion.applicationVersionStatus}</td>
                                        <td><fmt:formatDate value="${applicationVersion.timeUploaded.time}" type="both" timeStyle="short" /></td>
                                        <td>
                                            <div class="chip">
                                                <img src="<spring:url value='${applicationVersion.contributor.imageUrl}' />" alt="${applicationVersion.contributor.firstName}" /> 
                                                <c:out value="${applicationVersion.contributor.firstName}" />&nbsp;<c:out value="${applicationVersion.contributor.lastName}" />
                                            </div>
                                        </td>
                                        <td>
                                            <a href="<spring:url value='/project-apk/${application.packageName}-${applicationVersion.versionCode}.apk?deviceId=&locale=${application.locale}&deviceModel=&osVersion=&appVersionCode=&applicationId=${application.id}' />" class="waves-effect waves-light btn-small" title="<fmt:message key="download" />">
                                                <i class="material-icons">cloud_download</i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </form:form>
    </div>
</content:section>
