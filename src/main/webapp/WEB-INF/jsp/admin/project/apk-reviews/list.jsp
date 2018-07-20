<content:title>
    Pending APK reviews
</content:title>

<content:section cssId="apkReviewsListPage">
    <h4><content:gettitle /></h4>
    <c:if test="${empty applicationVersions}">
        <p>
            There are currently no APK files pending review 👍
        </p>
    </c:if>
    <c:if test="${not empty applicationVersions}">
        <div class="card-panel">
            <div class="row">
                <div class="col s12">
                    <c:if test="${not empty applicationVersions}">
                        <table class="bordered highlight">
                            <thead>
                                <th><fmt:message key="package.name" /></th>
                                <th><fmt:message key="version.code" /></th>
                                <th><fmt:message key="file.size" /></th>
                                <th><fmt:message key="time.uploaded" /></th>
                                <th><fmt:message key="contributor" /></th>
                                <th><fmt:message key="status" /></th>
                                <th><fmt:message key="review" /></th>
                            </thead>
                            <tbody>
                                <c:forEach var="applicationVersion" items="${applicationVersions}">
                                    <tr>
                                        <td>
                                            <a href="<spring:url value="/project/${applicationVersion.application.appGroup.appCategory.project.id}/app-category/${applicationVersion.application.appGroup.appCategory.id}/app-group/${applicationVersion.application.appGroup.id}/app/${applicationVersion.application.id}/edit" />">
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
                                        <td>
                                            <a href="<spring:url value='/admin/project/apk-reviews/${applicationVersion.id}' />">
                                                <i class="material-icons" style="vertical-align: bottom;">how_to_reg</i> <fmt:message key="initiate.review" />
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>
</content:section>
