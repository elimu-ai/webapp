<content:title>
    <fmt:message key="edit.paragraph" />
</content:title>

<content:section cssId="storyBookParagraphEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <c:if test="${not empty storyBookParagraph.storyBookChapter.image}">
            <a href="<spring:url value='/content/multimedia/image/edit/${storyBookParagraph.storyBookChapter.image.id}' />">
                <img src="<spring:url value='/image/${storyBookParagraph.storyBookChapter.image.id}_r${storyBookParagraph.storyBookChapter.image.revisionNumber}.${fn:toLowerCase(storyBookParagraph.storyBookChapter.image.imageFormat)}' />" alt="${storyBookParagraph.storyBookChapter.storyBook.title}" />
            </a>
        </c:if>
        
        <form:form modelAttribute="storyBookParagraph">
            <tag:formErrors modelAttribute="storyBookParagraph" />
            
            <form:hidden path="storyBookChapter" value="${storyBookParagraph.storyBookChapter.id}" />
            <form:hidden path="sortOrder" value="${storyBookParagraph.sortOrder}" />
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="originalText" cssErrorClass="error"><fmt:message key='original.text' /></form:label>
                    <form:textarea path="originalText" cssClass="materialize-textarea" cssErrorClass="error" />
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <select id="audio" name="audio">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="audio" items="${audios}">
                            <option value="${audio.id}" <c:if test="${audio.id == storyBookParagraph.audio.id}">selected="selected"</c:if>>${audio.title}</option>
                        </c:forEach>
                    </select>
                    <label for="audio"><fmt:message key="audio" /></label>
                    
                    <c:if test="${not empty storyBookParagraph.audio}">
                        <audio controls="true" autoplay="true">
                            <source src="<spring:url value='/audio/${storyBookParagraph.audio.id}_r${storyBookParagraph.audio.revisionNumber}.${fn:toLowerCase(storyBookParagraph.audio.audioFormat)}' />" />
                        </audio><br />
                    </c:if>
                    
                    <a href="<spring:url value='/content/multimedia/audio/create' />?autoFillTitle=storybook-${storyBookParagraph.storyBookChapter.storyBook.id}-ch-${storyBookParagraph.storyBookChapter.sortOrder + 1}-par-${storyBookParagraph.sortOrder + 1}&autoFillTranscription=${storyBookParagraph.originalText}" target="_blank">
                        <fmt:message key="add.audio" /> <i class="material-icons">launch</i>
                    </a>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <sec:authorize access="hasRole('ROLE_EDITOR')">
                <a href="<spring:url value='/content/storybook/paragraph/delete/${storyBookParagraph.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
            </sec:authorize>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key='original.text' /></h5>
    <div class="card-panel grey lighten-5">
        <code class="language-markup">
            white-space: pre-line;<br />
            word-spacing: 1em;<br />
            letter-spacing: 0.4em;
        </code>
        <blockquote class="flow-text" style="white-space: pre-line; word-spacing: 1em; letter-spacing: 0.4em;"><c:out value="${storyBookParagraph.originalText}" /></blockquote>
    </div>
</content:aside>
