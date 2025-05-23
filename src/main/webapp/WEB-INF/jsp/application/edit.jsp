<content:title>
    Edit application
</content:title>

<content:section cssId="applicationEditPage">
    <h4><content:gettitle /></h4>
    <c:if test="${empty applicationVersions}">
        <div class="card-panel amber lighten-3">
            <b>Note:</b> The application will not be active until you publish a corresponding APK file.
        </div>
    </c:if>
    <div class="card-panel">
        <form:form modelAttribute="application">
            <tag:formErrors modelAttribute="application" />

            <div class="row">
                <form:hidden path="contributor" value="${application.contributor.id}" />
                <div class="input-field col s6">
                    <form:label path="packageName" cssErrorClass="error">Package name</form:label>
                    <form:input disabled="true" path="packageName" cssErrorClass="error" placeholder="ai.elimu.soundcards" />
                    <form:hidden path="packageName" value="${application.packageName}" />
                </div>
                <div class="col s12 m6 input-field">
                    <form:label path="repoName" cssErrorClass="error">Repo name</form:label>
                    <form:input path="repoName" cssErrorClass="error" placeholder="soundcards" />
                </div>
                <div class="input-field col s6">
                    <select id="applicationStatus" name="applicationStatus">
                        <option value="">-- Select --</option>
                        <c:forEach var="applicationStatus" items="${applicationStatuses}">
                            <option value="${applicationStatus}" <c:if test="${applicationStatus == application.applicationStatus}">selected="selected"</c:if>><c:out value="${applicationStatus}" /></option>
                        </c:forEach>
                    </select>
                    <label for="applicationStatus">Status</label>
                </div>
            </div>
                
            <div class="row">
                <div class="col s12">
                    <label>App type</label><br />
                    <input type="checkbox" name="infrastructural" id="infrastructural" value="on" <c:if test="${application.infrastructural}">checked="checked"</c:if> />
                    <label for="infrastructural">
                        Infrastructural
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
                        <h5>Literacy skills</h5>
                        <blockquote>
                            What <i>literacy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="literacySkill" items="${literacySkills}">
                            <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(application.literacySkills, literacySkill)}">checked="checked"</c:if> />
                            <label for="${literacySkill}">
                                <c:choose>
                                    <c:when test="${literacySkill == 'CONCEPTS_ABOUT_PRINT'}">Concepts about print</c:when>
                                    <c:when test="${literacySkill == 'PHONEMIC_AWARENESS'}">Phonemic awareness</c:when>
                                    <c:when test="${literacySkill == 'ORAL_VOCABULARY'}">Oral vocabulary</c:when>
                                    <c:when test="${literacySkill == 'LISTENING_COMPREHENSION'}">Listening comprehension</c:when>
                                    <c:when test="${literacySkill == 'LETTER_IDENTIFICATION'}">Letter identification</c:when>
                                    <c:when test="${literacySkill == 'SYLLABLE_NAMING'}">Syllable naming</c:when>
                                    <c:when test="${literacySkill == 'NONWORD_READING'}">Nonword Reading</c:when>
                                    <c:when test="${literacySkill == 'FAMILIAR_WORD_READING'}">Familiar word reading</c:when>
                                    <c:when test="${literacySkill == 'ORAL_READING_FLUENCY'}">Oral reading fluency</c:when>
                                    <c:when test="${literacySkill == 'DICTATION'}">Dictation (Sentence writing)</c:when>
                                    <c:when test="${literacySkill == 'MAZE_CLOZE'}">Maze/Cloze (Reading comprehension)</c:when>                                    
                                </c:choose>
                            </label><br />
                        </c:forEach>
                    </div>

                    <div class="col s12 m6">
                        <h5>Numeracy skills</h5>
                        <blockquote>
                            What <i>numeracy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="numeracySkill" items="${numeracySkills}">
                            <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(application.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                            <label for="${numeracySkill}">
                                <c:choose>
                                    <c:when test="${numeracySkill == 'ORAL_COUNTING'}">Oral counting</c:when>
                                    <c:when test="${numeracySkill == 'ONE_TO_ONE_CORRESPONDENCE'}">One-to-one correspondence</c:when>
                                    <c:when test="${numeracySkill == 'NUMBER_IDENTIFICATION'}">Number identification</c:when>
                                    <c:when test="${numeracySkill == 'QUANTITY_DISCRIMINATION'}">Quantity discrimination</c:when>                                    
                                    <c:when test="${numeracySkill == 'MISSING_NUMBER'}">Missing number</c:when>                                    
                                    <c:when test="${numeracySkill == 'ADDITION'}">Addition</c:when>                                    
                                    <c:when test="${numeracySkill == 'SUBTRACTION'}">Subtraction</c:when>                                    
                                    <c:when test="${numeracySkill == 'MULTIPLICATION'}">Multiplication</c:when>                                    
                                    <c:when test="${numeracySkill == 'WORD_PROBLEMS'}">Word problems</c:when>                                    
                                    <c:when test="${numeracySkill == 'SHAPE_IDENTIFICATION'}">Shape identification</c:when>                                    
                                    <c:when test="${numeracySkill == 'SHAPE_NAMING'}">Shape naming</c:when>                                    
                                </c:choose>
                            </label><br />
                        </c:forEach>
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
        </form:form>    
    </div>
    
    <div class="card-panel">    
        <div class="row">
            <div class="col s12">
                <a name="versions"></a>
                <h5>Application versions</h5>
                <p>
                    <a class="publishLink" href="<spring:url value="/application/application-version/create?applicationId=${application.id}" />"><i class="material-icons left">new_releases</i>Publish new APK file</a>
                </p>
                <c:if test="${not empty applicationVersions}">
                    <table class="bordered highlight">
                        <thead>
                            <th>Label</th>
                            <th>Version code</th>
                            <th>Version name</th>
                            <th>File size</th>
                            <th>minSdkVersion</th>
                            <th>Time uploaded</th>
                            <th>Contributor</th>
                            <th><i class="material-icons">vertical_align_bottom</i></th>
                        </thead>
                        <tbody>
                            <c:forEach var="applicationVersion" items="${applicationVersions}">
                                <tr>
                                    <td>"${applicationVersion.label}"</td>
                                    <td><code>${applicationVersion.versionCode}</code></td>
                                    <td><code>${applicationVersion.versionName}</code></td>
                                    <td><fmt:formatNumber value="${applicationVersion.fileSizeInKb / 1024}" maxFractionDigits="2" />MB</td>
                                    <td>API ${applicationVersion.minSdkVersion}</td>
                                    <td><fmt:formatDate value="${applicationVersion.timeUploaded.time}" pattern="yyyy-MM-dd" /></td>
                                    <td>
                                        <c:set var="chipContributor" value="${applicationVersion.contributor}" />
                                        <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                                    </td>
                                    <td>
                                        <a href="<spring:url value='${applicationVersion.fileUrl}' />" class="waves-effect waves-light btn-small" title="Download">
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

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            GitHub repo:
        </p>
        <a href="https://github.com/elimu-ai/${application.repoName}" target="_blank">
            github.com/elimu-ai/<b>${application.repoName}</b>
        </a>
    </div>
</content:aside>
