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
                <form:hidden path="contributor" value="${application.contributor.id}" />
                <div class="input-field col s6">
                    <fmt:message key='package.name' />: <code>${application.packageName}</code>
                    <form:hidden path="packageName" value="${application.packageName}" />
                </div>
                <div class="input-field col s6">
                    <select id="applicationStatus" name="applicationStatus">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="applicationStatus" items="${applicationStatuses}">
                            <option value="${applicationStatus}" <c:if test="${applicationStatus == application.applicationStatus}">selected="selected"</c:if>><c:out value="${applicationStatus}" /></option>
                        </c:forEach>
                    </select>
                    <label for="applicationStatus"><fmt:message key="status" /></label>
                </div>
            </div>
                
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="app.type" /></label><br />
                    <input type="checkbox" name="infrastructural" id="infrastructural" value="on" <c:if test="${application.infrastructural}">checked="checked"</c:if> />
                    <label for="infrastructural">
                        <fmt:message key="infrastructural" />
                    </label>
                    <script>
                        $(function() {
                            $('#infrastructural').on('change', function() {
                                console.info('#infrastructural on change');
                                
                                if ($(this).is(':checked')) {
                                    $('#skillsContainer').fadeOut();
                                } else {
                                    $('#skillsContainer').fadeIn();
                                }
                            });
                        });
                    </script>
                </div>
            </div>
            
            <div id="skillsContainer" <c:if test="${application.infrastructural}">style="display: none;"</c:if>>
                <div class="row">
                    <div class="col s12 m6">
                        <h5><fmt:message key="literacy.skills" /></h5>
                        <blockquote>
                            What <i>literacy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="literacySkill" items="${literacySkills}">
                            <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(application.literacySkills, literacySkill)}">checked="checked"</c:if> />
                            <label for="${literacySkill}">
                                <fmt:message key="literacy.skill.${literacySkill}" />
                            </label><br />
                        </c:forEach>
                    </div>

                    <div class="col s12 m6">
                        <h5><fmt:message key="numeracy.skills" /></h5>
                        <blockquote>
                            What <i>numeracy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="numeracySkill" items="${numeracySkills}">
                            <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(application.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                            <label for="${numeracySkill}">
                                <fmt:message key="numeracy.skill.${numeracySkill}" />
                            </label><br />
                        </c:forEach>
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>    
    </div>
    
    <div class="card-panel">    
        <div class="row">
            <div class="col s12">
                <a name="versions"></a>
                <h5><fmt:message key="application.versions" /></h5>
                <p>
                    <a href="<spring:url value="/admin/application-version/create?applicationId=${application.id}" />"><i class="material-icons left">file_upload</i><fmt:message key='upload.new.apk.file' /></a>
                </p>
                <c:if test="${not empty applicationVersions}">
                    <table class="bordered highlight">
                        <thead>
                            <th><fmt:message key="label" /></th>
                            <th><fmt:message key="version.code" /></th>
                            <th><fmt:message key="version.name" /></th>
                            <th><fmt:message key="file.size" /></th>
                            <th>minSdkVersion</th>
                            <th><fmt:message key="time.uploaded" /></th>
                            <th><fmt:message key="contributor" /></th>
                            <th><i class="material-icons">vertical_align_bottom</i></th>
                        </thead>
                        <tbody>
                            <c:forEach var="applicationVersion" items="${applicationVersions}">
                                <tr>
                                    <td>${applicationVersion.label}</td>
                                    <td>${applicationVersion.versionCode}</td>
                                    <td>${applicationVersion.versionName}</td>
                                    <td><fmt:formatNumber value="${applicationVersion.fileSizeInKb / 1024}" maxFractionDigits="2" />MB</td>
                                    <td><c:out value="${applicationVersion.minSdkVersion}" /></td>
                                    <td><fmt:formatDate value="${applicationVersion.timeUploaded.time}" type="both" timeStyle="short" /></td>
                                    <td>
                                        <div class="chip">
                                            <img src="<spring:url value='${applicationVersion.contributor.imageUrl}' />" alt="${applicationVersion.contributor.firstName}" /> 
                                            <c:out value="${applicationVersion.contributor.firstName}" />&nbsp;<c:out value="${applicationVersion.contributor.lastName}" />
                                        </div>
                                    </td>
                                    <td>
                                        <a href="<spring:url value='/apk/${application.packageName}-${applicationVersion.versionCode}.apk' />" class="waves-effect waves-light btn-small" title="<fmt:message key="download" />">
                                            <i class="material-icons">vertical_align_bottom</i>
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
</content:section>
