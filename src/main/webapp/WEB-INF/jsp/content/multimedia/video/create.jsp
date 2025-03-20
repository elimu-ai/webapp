<content:title>
    Add video
</content:title>

<content:section cssId="videoCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="video" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="video" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error">Title</form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- Select --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == video.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense">Content license</label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error">Attribution URL</form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        What <i>literacy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(video.literacySkills, literacySkill)}">checked="checked"</c:if> />
                        <label for="${literacySkill}">
                            <fmt:message key="literacy.skill.${literacySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        What <i>numeracy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(video.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span>File (M4V/MP4)</span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span>Thumbnail (PNG)</span>
                        <form:input path="thumbnail" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
