<content:title>
    <fmt:message key="edit.storybook" />
</content:title>

<content:section cssId="storyBookEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />
            
            <form:hidden path="revisionNumber" value="${storyBook.revisionNumber}" />
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
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
                    <select id="readingLevel" name="readingLevel">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="readingLevel" items="${readingLevels}">
                            <option value="${readingLevel}" <c:if test="${readingLevel == storyBook.readingLevel}">selected="selected"</c:if>><fmt:message key="reading.level.${readingLevel}" /></option>
                        </c:forEach>
                    </select>
                    <label for="readingLevel"><fmt:message key="reading.level" /></label>
                </div>
                
                <div class="input-field col s12" id="coverImageContainer">
                    <select id="coverImage" name="coverImage">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="coverImage" items="${coverImages}">
                            <option value="${coverImage.id}" <c:if test="${coverImage.id == storyBook.coverImage.id}">selected="selected"</c:if>>${coverImage.title}</option>
                        </c:forEach>
                    </select>
                    <label for="coverImage"><fmt:message key="cover.image" /></label>
                    <c:if test="${not empty storyBook.coverImage}">
                        <a href="<spring:url value='/content/multimedia/image/edit/${storyBook.coverImage.id}' />">
                            <img src="<spring:url value='/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}' />" alt="${storyBook.title}" />
                        </a>
                    </c:if>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
    
    <c:forEach var="storyBookChapter" items="${storyBookChapters}" varStatus="status">
        <a name="ch-id-${storyBookChapter.id}"></a>
        <sec:authorize access="hasRole('ADMIN')">
            <a class="storyBookChapterDeleteLink right red-text" style="margin-top: 1em;" href="<spring:url value='/content/storybook/edit/${storyBook.id}/chapter/delete/${storyBookChapter.id}' />"><i class="material-icons" title="<fmt:message key='delete' />">delete</i></a>
        </sec:authorize>
        <h5 style="margin-top: 1em;" class="grey-text"><fmt:message key="chapter" />&nbsp;${storyBookChapter.sortOrder + 1}/${fn:length(storyBookChapters)}</h5>
        <div class="card-panel storyBookChapter">
            <c:if test="${not empty storyBookChapter.image}">
                <a href="<spring:url value='/content/multimedia/image/edit/${storyBookChapter.image.id}' />">
                    <img src="<spring:url value='/image/${storyBookChapter.image.id}_r${storyBookChapter.image.revisionNumber}.${fn:toLowerCase(storyBookChapter.image.imageFormat)}' />" alt="${storyBook.title}" />
                </a>
            </c:if>
            
            <c:forEach var="storyBookParagraph" items="${paragraphsPerStoryBookChapterMap[storyBookChapter.id]}">
                <p class="storyBookParagraph"><sec:authorize access="hasRole('ADMIN')"><a class="storyBookParagraphEditLink right" href="<spring:url value='/content/storybook/paragraph/edit/${storyBookParagraph.id}' />"><i class="material-icons" title="<fmt:message key='edit' />">edit</i></a></sec:authorize>
                    <c:forEach var="wordInOriginalText" items="${fn:split(fn:trim(storyBookParagraph.originalText), ' ')}" varStatus="status">
                        <c:set var="word" value="${storyBookParagraph.words[status.index]}" />
                        <c:choose>
                            <c:when test="${empty word}"><c:out value="${wordInOriginalText}" /><c:out value=" " /></c:when>
                            <c:otherwise><a href="<spring:url value='/content/word/edit/${word.id}' />"><c:out value="${wordInOriginalText}" /></a><c:out value=" " /></c:otherwise>
                        </c:choose>
                    </c:forEach>
                </p>
            </c:forEach>
        </div>
    </c:forEach>
    
    <div id="contributionEvents" class="collection">
        <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
            <div class="collection-item">
                <span class="badge">
                    <fmt:formatDate value="${storyBookContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                    (<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:message key="revision" />: #${storyBookContributionEvent.revisionNumber}
                </span>
                <div class="chip">
                    <img src="<spring:url value='${storyBookContributionEvent.contributor.imageUrl}' />" alt="${storyBookContributionEvent.contributor.firstName}" /> 
                    <c:out value="${storyBookContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${storyBookContributionEvent.contributor.lastName}" />
                </div>
                <blockquote><c:out value="${storyBookContributionEvent.comment}" /></blockquote>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="word.frequency" /></h5>
    
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="word" /></th>
            <th><fmt:message key="frequency" /></th>
        </thead>
        <tbody>
            <c:forEach var="wordFrequencyMapItem" items="${wordFrequencyMap}">
                <tr>
                    <td>
                        <c:set var="wordText" value="${wordFrequencyMapItem.key}" />
                        <c:set var="wordTextLowerCase" value="${fn:toLowerCase(wordText)}" />
                        <c:choose>
                            <c:when test="${empty wordMap[wordTextLowerCase]}">
                                <c:out value="${wordText}" /><br />
                                <a href="<spring:url value='/content/word/create?autoFillText=${wordTextLowerCase}' />" target="_blank"><fmt:message key="add.word" /> <i class="material-icons">launch</i></a>
                            </c:when>
                            <c:otherwise>
                                <c:set var="word" value="${wordMap[wordTextLowerCase]}" />
                                <a href="<spring:url value='/content/word/edit/${word.id}' />" target="_blank">
                                    <c:out value="${word.text}" />
                                </a><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /><br />
                                <span class="grey-text">
                                    /<c:forEach var="allophone" items="${word.allophones}">
                                        ${allophone.valueIpa}
                                    </c:forEach>/
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${wordFrequencyMapItem.value}</td>
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
            <c:forEach var="letterFrequencyMapItem" items="${letterFrequencyMap}">
                <tr>
                    <td>
                        <c:set var="letterText" value="${letterFrequencyMapItem.key}" />
                        <c:choose>
                            <c:when test="${empty letterMap[letterText]}">
                                <c:out value="${letterText}" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="letter" value="${letterMap[letterText]}" />
                                <a href="<spring:url value='/content/letter/edit/${letter.id}' />" target="_blank">
                                    <c:out value="${letter.text}" />
                                </a><br />
                                <span class="grey-text">
                                    /<c:forEach var="allophone" items="${letter.allophones}">
                                        ${allophone.valueIpa}
                                    </c:forEach>/
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${letterFrequencyMapItem.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
