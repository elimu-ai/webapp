<content:title>
    <fmt:message key="edit.paragraph" />
</content:title>

<content:section cssId="storyBookParagraphEditPage">
    <h4><content:gettitle /></h4>
    
    <c:if test="${not empty storyBookParagraph.storyBookChapter.image}">
        <div class="card-panel">
            <a href="<spring:url value='/content/multimedia/image/edit/${storyBookParagraph.storyBookChapter.image.id}' />">
                <img src="<spring:url value='/image/${storyBookParagraph.storyBookChapter.image.id}_r${storyBookParagraph.storyBookChapter.image.revisionNumber}.${fn:toLowerCase(storyBookParagraph.storyBookChapter.image.imageFormat)}' />" alt="${storyBookParagraph.storyBookChapter.storyBook.title}" />
            </a>
        </div>
    </c:if>
    
    <div class="card-panel">
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

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <sec:authorize access="hasRole('ROLE_EDITOR')">
                <a href="<spring:url value='/content/storybook/paragraph/delete/${storyBookParagraph.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
            </sec:authorize>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="audios" /></h5>
    <c:choose>
        <c:when test="${empty audios}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This paragraph has no corresponding audio.<br />
                <a href="<spring:url value='/content/multimedia/audio/create?paragraphId=${storyBookParagraph.id}&autoFillTitle=storybook-${storyBookParagraph.storyBookChapter.storyBook.id}-ch-${storyBookParagraph.storyBookChapter.sortOrder + 1}-par-${storyBookParagraph.sortOrder + 1}&autoFillTranscription=${storyBookParagraph.originalText}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="audio" items="${audios}" varStatus="status">
                <audio controls="true"<c:if test="${status.index == 0}"> autoplay="true"</c:if>>
                    <source src="<spring:url value='/audio/${audio.id}_r${audio.revisionNumber}.${fn:toLowerCase(audio.audioFormat)}' />" />
                </audio>
                <div style="margin-bottom: 1rem; font-size: 0.8rem;">
                    <a href="<spring:url value='/content/multimedia/audio/edit/${audio.id}' />" target="_blank">
                        <fmt:formatDate value="${audio.timeLastUpdate.time}" pattern="yyyy-MM-dd HH:mm" />
                    </a>
                </div>
                <div style="clear: both;"></div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</content:aside>
