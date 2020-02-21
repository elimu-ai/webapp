<content:title>
    <fmt:message key="edit.storybook" />
</content:title>

<content:section cssId="storyBookEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />

            <div class="row">
                <form:hidden path="language" value="${applicationScope.configProperties['content.language']}" />
                <form:hidden path="revisionNumber" value="${storyBook.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="description" cssErrorClass="error"><fmt:message key='description' /></form:label>
                    <form:input path="description" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == storyBook.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense"><fmt:message key="content.license" /></label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="input-field col s12">
                    <select id="gradeLevel" name="gradeLevel">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="gradeLevel" items="${gradeLevels}">
                            <option value="${gradeLevel}" <c:if test="${gradeLevel == storyBook.gradeLevel}">selected="selected"</c:if>><fmt:message key="grade.level.${gradeLevel}" /></option>
                        </c:forEach>
                    </select>
                    <label for="gradeLevel"><fmt:message key="grade.level" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="coverImage" name="coverImage">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="coverImage" items="${coverImages}">
                            <option value="${coverImage.id}" <c:if test="${coverImage.id == storyBook.coverImage.id}">selected="selected"</c:if>>${coverImage.title}</option>
                        </c:forEach>
                    </select>
                    <label for="coverImage"><fmt:message key="cover.image" /></label>
                    <c:if test="${not empty storyBook.coverImage}">
                        <img src="<spring:url value='/image/${storyBook.coverImage.id}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}' />" alt="${storyBook.title}" />
                    </c:if>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
    
    <c:forEach var="storyBookChapter" items="${storyBookChapters}" varStatus="status">
        <c:if test="${status.index == (fn:length(storyBookChapters) - 1)}">
            <a class="storyBookChapterDeleteLink right red-text" style="margin-top: 1em;" href="<spring:url value='/content/storybook/edit/${storyBook.id}/chapter/delete/${storyBookChapter.id}' />"><i class="material-icons" title="<fmt:message key='delete' />">delete</i></a>
        </c:if>
        <h5 style="margin-top: 1em;"><fmt:message key="chapter" />&nbsp;${storyBookChapter.sortOrder + 1}/${fn:length(storyBookChapters)}</h5>
        <div class="card-panel">
            <c:forEach var="storyBookParagraph" items="${paragraphsPerStoryBookChapterMap[storyBookChapter.id]}">
                <p>
                    <c:forEach var="wordInOriginalText" items="${fn:split(fn:trim(storyBookParagraph.originalText), ' ')}" varStatus="status">
                        <c:set var="word" value="${storyBookParagraph.words[status.index]}" />
                        <c:choose>
                            <c:when test="${empty word}">
                                <c:out value="${wordInOriginalText}" /><c:out value=" " />
                            </c:when>
                            <c:otherwise>
                                <a href="<spring:url value='/content/word/edit/${word.id}' />"><c:out value="${wordInOriginalText}" /></a><c:out value=" " />
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </p>
            </c:forEach>
        </div>
    </c:forEach>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="word.frequency" /></h5>
    
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="word" /></th>
            <th><fmt:message key="frequency" /></th>
        </thead>
        <tbody>
            <c:forEach var="wordFrequency" items="${wordFrequencyMap}">
                <tr>
                    <td>${wordFrequency.key}</td>
                    <td>${wordFrequency.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <p>&nbsp;</p>
    
    <h5 class="center"><fmt:message key="letter.frequency" /></h5>
    
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="letter" /></th>
            <th><fmt:message key="frequency" /></th>
        </thead>
        <tbody>
            <c:forEach var="letterFrequency" items="${letterFrequencyMap}">
                <tr>
                    <td>${letterFrequency.key}</td>
                    <td>${letterFrequency.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
