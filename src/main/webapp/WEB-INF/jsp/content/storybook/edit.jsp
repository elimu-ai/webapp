<content:title>
    <fmt:message key="edit.storybook" />
</content:title>

<content:section cssId="storyBookEditPage">
    <c:choose>
        <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
        </c:when>
        <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
        </c:when>
        <c:otherwise>
            <c:set var="peerReviewStatusColor" value="" />
        </c:otherwise>
    </c:choose>
    <div class="chip right ${peerReviewStatusColor}" style="margin-top: 1.14rem;">
        <a href="#contribution-events">
            <fmt:message key="peer.review" />: ${storyBook.peerReviewStatus}
        </a>
    </div>
    
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
        <sec:authorize access="hasRole('ROLE_EDITOR')">
            <a class="storyBookChapterDeleteLink right red-text" style="margin-top: 1em;" href="<spring:url value='/content/storybook/edit/${storyBook.id}/chapter/delete/${storyBookChapter.id}' />"><i class="material-icons" title="<fmt:message key='delete' />">delete</i></a>
        </sec:authorize>
        <h5 style="margin-top: 1em;" class="grey-text"><fmt:message key="chapter" />&nbsp;${storyBookChapter.sortOrder + 1}/${fn:length(storyBookChapters)}</h5>
        <div class="card-panel storyBookChapter">
            <c:if test="${not empty storyBookChapter.image}">
                <a href="<spring:url value='/content/multimedia/image/edit/${storyBookChapter.image.id}' />">
                    <img src="<spring:url value='/image/${storyBookChapter.image.id}_r${storyBookChapter.image.revisionNumber}.${fn:toLowerCase(storyBookChapter.image.imageFormat)}' />" alt="${storyBook.title}" />
                </a>
                <sec:authorize access="hasRole('ROLE_EDITOR')">
                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}/chapter/${storyBookChapter.id}/deleteImage' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
                </sec:authorize>
            </c:if>
            
            <c:forEach var="storyBookParagraph" items="${paragraphsPerStoryBookChapterMap[storyBookChapter.id]}">
                <p class="storyBookParagraph"><a class="storyBookParagraphEditLink right" href="<spring:url value='/content/storybook/paragraph/edit/${storyBookParagraph.id}' />"><i class="material-icons" title="<fmt:message key='edit' />">edit</i></a><c:out value="" />
                    <c:forEach var="wordInOriginalText" items="${fn:split(fn:trim(storyBookParagraph.originalText), ' ')}" varStatus="status">
                        <c:set var="word" value="${storyBookParagraph.words[status.index]}" />
                        <c:choose>
                            <c:when test="${empty word}"><c:out value="${wordInOriginalText}" /><c:out value=" " /></c:when>
                            <c:otherwise><a href="<spring:url value='/content/word/edit/${word.id}' />"><c:out value="${wordInOriginalText}" /></a><c:out value="${emojisByWordId[word.id]}" /><c:out value=" " /></c:otherwise>
                        </c:choose>
                    </c:forEach>
                </p>
            </c:forEach>
            
            <div class="center">
                <a href="<spring:url value="/content/storybook/edit/${storyBook.id}/chapter/${storyBookChapter.id}/paragraph/create" />" 
                   class="btn-floating waves-effect waves-light grey"
                   style="margin-top: 1rem;"
                   title="<fmt:message key="add.paragraph" />">
                    <i class="material-icons">add</i>
                </a>
            </div>
        </div>
    </c:forEach>
    
    <div class="center">
        <a href="<spring:url value="/content/storybook/edit/${storyBook.id}/chapter/create" />" class="btn waves-effect waves-light grey">
            <fmt:message key="add.storybook.chapter" /> <i class="material-icons right">add</i>
        </a>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty storyBookContributionEvents) 
                  && (storyBookContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5><fmt:message key="peer.review" /> 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/storybook-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                <fmt:message key="do.you.approve.quality.of.this.storybook?" />
            </p>
            
            <input type="hidden" name="storyBookContributionEventId" value="${storyBookContributionEvents[0].id}" />
            
            <input type="radio" id="approved_true" name="approved" value="true" />
            <label for="approved_true"><fmt:message key="yes" /> (approve)</label><br />

            <input type="radio" id="approved_false" name="approved" value="false" />
            <label for="approved_false"><fmt:message key="no" /> (request changes)</label><br />
            
            <script>
                $(function() {
                    $('[name="approved"]').on('change', function() {
                        console.info('[name="approved"] on change');
                        
                        var isApproved = $('#approved_true').is(':checked');
                        console.info('isApproved: ' + isApproved);
                        if (isApproved) {
                            console.info('isApproved');
                            $('#comment').removeAttr('required');
                        } else {
                            $('#comment').attr('required', 'required');
                            console.info('!isApproved');
                        }
                        
                        $('#peerReviewSubmitContainer').fadeIn();
                    });
                });
            </script>
            
            <div id="peerReviewSubmitContainer" style="display: none;">
                <label for="comment"><fmt:message key="comment" /></label>
                <textarea id="comment" name="comment" class="materialize-textarea" maxlength="1000"></textarea>

                <button class="btn waves-effect waves-light" type="submit">
                    <fmt:message key="submit" /> <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
        
        <div class="divider" style="margin: 2em 0;"></div>
    </c:if>
    
    <a name="contribution-events"></a>
    <h5><fmt:message key="contributions" /> 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
            <a name="contribution-event_${storyBookContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    <fmt:message key="revision" /> #${storyBookContributionEvent.revisionNumber} 
                    (<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:formatDate value="${storyBookContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                </span>
                <a href="<spring:url value='/content/contributor/${storyBookContributionEvent.contributor.id}' />">
                    <div class="chip">
                        <c:choose>
                            <c:when test="${not empty storyBookContributionEvent.contributor.imageUrl}">
                                <img src="${storyBookContributionEvent.contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty storyBookContributionEvent.contributor.providerIdWeb3}">
                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${storyBookContributionEvent.contributor.providerIdWeb3}" />" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty storyBookContributionEvent.contributor.firstName}">
                                <c:out value="${storyBookContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${storyBookContributionEvent.contributor.lastName}" />
                            </c:when>
                            <c:when test="${not empty storyBookContributionEvent.contributor.providerIdWeb3}">
                                ${fn:substring(storyBookContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(storyBookContributionEvent.contributor.providerIdWeb3, 38, 42)}
                            </c:when>
                        </c:choose>
                    </div>
                </a>
                <c:if test="${not empty storyBookContributionEvent.comment}">
                    <blockquote><c:out value="${storyBookContributionEvent.comment}" /></blockquote>
                </c:if>
                <c:if test="${not empty storyBookContributionEvent.paragraphTextBefore}">
                    <p id="textDiffContainer_${storyBookContributionEvent.id}"></p>
                    <script>
                        $(function() {
                            // Visualize before/after diff
                            var textBefore = ['${fn:join(fn:split(fn:replace(storyBookContributionEvent.paragraphTextBefore, newLineCharRn, '<br/>'), ' '), '\', \'')}'];
                            var textAfter = ['${fn:join(fn:split(fn:replace(storyBookContributionEvent.paragraphTextAfter, newLineCharRn, '<br/>'), ' '), '\', \'')}'];
                            var unifiedDiff = difflib.unifiedDiff(textBefore, textAfter);
                            console.info('unifiedDiff: \n' + unifiedDiff);
                            for (var i = 2; i < unifiedDiff.length; i++) {
                                var diff = unifiedDiff[i];
                                if (diff.startsWith('@@')) {
                                    diff = '<span class="grey-text">' + diff + '</span><br />';
                                    if (i > 2) {
                                        diff = '<br /><br />' + diff;
                                    }
                                } else if (diff.startsWith('-')) {
                                    diff = '<span class="diff-deletion">' + diff.substring(1) + '<span>';
                                } else if (diff.startsWith('+')) {
                                    diff = '<span class="diff-addition">' + diff.substring(1) + '<span>';
                                }
                                $('#textDiffContainer_${storyBookContributionEvent.id}').append(diff);
                            }
                        });
                    </script>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="storyBookPeerReviewEvent" items="${storyBookPeerReviewEvents}">
                    <c:if test="${storyBookPeerReviewEvent.storyBookContributionEvent.id == storyBookContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${storyBookPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <a href="<spring:url value='/content/contributor/${storyBookPeerReviewEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty storyBookPeerReviewEvent.contributor.imageUrl}">
                                                <img src="${storyBookPeerReviewEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty storyBookPeerReviewEvent.contributor.providerIdWeb3}">
                                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${storyBookPeerReviewEvent.contributor.providerIdWeb3}" />" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty storyBookPeerReviewEvent.contributor.firstName}">
                                                <c:out value="${storyBookPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${storyBookPeerReviewEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty storyBookPeerReviewEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(storyBookPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(storyBookPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${storyBookPeerReviewEvent.isApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${storyBookPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty storyBookPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${storyBookPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
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
                                    /<c:forEach var="lsc" items="${word.letterSoundCorrespondences}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
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
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${letterFrequencyMapItem.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
