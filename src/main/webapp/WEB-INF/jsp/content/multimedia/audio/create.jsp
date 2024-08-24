<content:title>
    <fmt:message key="add.audio" />
</content:title>

<content:section cssId="audioCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="audio" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="audio" />
            
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <select id="word" name="word">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}" <c:if test="${word.id == audio.word.id}">selected="selected"</c:if>>${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /></option>
                        </c:forEach>
                    </select>
                    <label for="word"><fmt:message key="word" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="storyBookParagraph" name="storyBookParagraph">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="storyBookParagraph" items="${storyBookParagraphs}">
                            <option value="${storyBookParagraph.id}" <c:if test="${storyBookParagraph.id == audio.storyBookParagraph.id}">selected="selected"</c:if>>${storyBookParagraph.id} (<c:out value="${storyBookParagraph.storyBookChapter.storyBook.title}" />)</option>
                        </c:forEach>
                    </select>
                    <label for="storyBookParagraph"><fmt:message key="storybook.paragraph" /></label>
                </div>
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="transcription" cssErrorClass="error"><fmt:message key='transcription' /></form:label>
                    <form:textarea path="transcription" cssClass="materialize-textarea" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == audio.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense"><fmt:message key="content.license" /></label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <%--
                <div class="col s12 m6">
                    <blockquote>
                        <fmt:message key="what.literacy.skills" />
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(audio.literacySkills, literacySkill)}">checked="checked"</c:if> />
                        <label for="${literacySkill}">
                            <fmt:message key="literacy.skill.${literacySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        <fmt:message key="what.numeracy.skills" />
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(audio.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                --%>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span><fmt:message key='file' /></span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
