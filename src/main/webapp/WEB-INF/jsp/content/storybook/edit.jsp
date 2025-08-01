<content:title>
    Edit storybook
</content:title>

<content:section cssId="storyBookEditPage">
    <a href="#contribution-events" class="right" style="margin-top: 1.75rem;">
        <span class="peerReviewStatusContainer" data-status="${storyBook.peerReviewStatus}">
            Peer-review: <code>${storyBook.peerReviewStatus}</code>
        </span>
    </a>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />
            <c:if test="${not empty existingStoryBook}">
                <div class="row">
                    <div class="col s12 m8 l6">
                        <div class="storyBook card">
                            <div class="headband"></div>
                            <c:set var="coverImageUrl" value="" />
                            <c:if test="${not empty existingStoryBook.coverImage}">
                                <c:set var="coverImageUrl" value="${existingStoryBook.coverImage.url}" />
                            </c:if>
                            <a class="editLink" href="<spring:url value='/content/storybook/edit/${existingStoryBook.id}' />">
                                <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                                    <span class="card-title"><c:out value="${existingStoryBook.title}" /></span>
                                </div>
                            </a>
                            <div class="card-content">
                                <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${existingStoryBook.description}" /></p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <form:hidden path="revisionNumber" value="${storyBook.revisionNumber}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error">Title</form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="description" cssErrorClass="error">Description</form:label>
                    <form:input path="description" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- Select --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == storyBook.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense">Content license</label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error">Attribution URL</form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="input-field col s12">
                    <div class="card-panel amber lighten-5">
                        🤖 AI predicted reading level: <b>${predictedReadingLevel}</b>
                    </div>
                    <select id="readingLevel" name="readingLevel">
                        <option value="">-- Select --</option>
                        <c:forEach var="readingLevel" items="${readingLevels}">
                            <option value="${readingLevel}" <c:if test="${readingLevel == storyBook.readingLevel}">selected="selected"</c:if>>
                                <c:choose>
                                    <c:when test="${readingLevel == 'LEVEL1'}">Level 1. Beginning to Read</c:when>
                                    <c:when test="${readingLevel == 'LEVEL2'}">Level 2. Learning to Read</c:when>
                                    <c:when test="${readingLevel == 'LEVEL3'}">Level 3. Reading Independently</c:when>
                                    <c:when test="${readingLevel == 'LEVEL4'}">Level 4. Reading Proficiently</c:when>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                    <label for="readingLevel">Reading level</label>
                </div>
                
                <div class="input-field col s12" id="coverImageContainer">
                    <select id="coverImage" name="coverImage">
                        <option value="">-- Select --</option>
                        <c:forEach var="coverImage" items="${coverImages}">
                            <option value="${coverImage.id}" <c:if test="${coverImage.id == storyBook.coverImage.id}">selected="selected"</c:if>>${coverImage.title}</option>
                        </c:forEach>
                    </select>
                    <label for="coverImage">Cover image</label>
                    <c:if test="${not empty storyBook.coverImage}">
                        <a href="<spring:url value='/content/multimedia/image/edit/${storyBook.coverImage.id}' />">
                            <img class="checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" src="<spring:url value='${storyBook.coverImage.url}' />" alt="${storyBook.title}" />
                        </a>
                    </c:if>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
    
    <c:forEach var="storyBookChapter" items="${storyBookChapters}" varStatus="status">
        <a name="ch-id-${storyBookChapter.id}"></a>
        <c:if test="${fn:contains(contributor.roles, 'EDITOR')}">
            <a class="storyBookChapterDeleteLink right red-text" style="margin-top: 1em;" href="<spring:url value='/content/storybook/edit/${storyBook.id}/chapter/delete/${storyBookChapter.id}' />"><i class="material-icons" title="Delete">delete</i></a>
        </c:if>
        <h5 style="margin-top: 1em;" class="grey-text">Chapter&nbsp;${storyBookChapter.sortOrder + 1}/${fn:length(storyBookChapters)}</h5>
        <div class="card-panel storyBookChapter">
            <c:if test="${not empty storyBookChapter.image}">
                <a href="<spring:url value='/content/multimedia/image/edit/${storyBookChapter.image.id}' />">
                    <img class="checksumGitHub-${storyBookChapter.image.checksumGitHub != null}" src="<spring:url value='${storyBookChapter.image.url}' />" alt="${storyBook.title}" />
                </a>
            </c:if>
            
            <c:forEach var="storyBookParagraph" items="${paragraphsPerStoryBookChapterMap[storyBookChapter.id]}">
                <p class="storyBookParagraph"><a class="storyBookParagraphEditLink right" href="<spring:url value='/content/storybook/paragraph/edit/${storyBookParagraph.id}' />"><i class="material-icons" title="Edit">edit</i></a><c:out value="" />
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
                   title="Add paragraph">
                    <i class="material-icons">add</i>
                </a>
            </div>
        </div>
    </c:forEach>
    
    <div class="center">
        <a href="<spring:url value="/content/storybook/edit/${storyBook.id}/chapter/create" />" class="btn waves-effect waves-light grey">
            Add storybook chapter <i class="material-icons right">add</i>
        </a>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty storyBookContributionEvents) 
                  && (storyBookContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5>Peer-review 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/storybook-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                Do you approve the quality of this storybook?
            </p>
            
            <input type="hidden" name="storyBookContributionEventId" value="${storyBookContributionEvents[0].id}" />
            
            <input type="radio" id="approved_true" name="approved" value="true" />
            <label for="approved_true">Yes (approve)</label><br />

            <input type="radio" id="approved_false" name="approved" value="false" />
            <label for="approved_false">No (request changes)</label><br />
            
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
                <label for="comment">Comment</label>
                <textarea id="comment" name="comment" class="materialize-textarea" maxlength="1000"></textarea>

                <button class="btn waves-effect waves-light" type="submit">
                    Submit <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
        
        <div class="divider" style="margin: 2em 0;"></div>
    </c:if>
    
    <a name="contribution-events"></a>
    <h5>Contributions 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
            <a name="contribution-event_${storyBookContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${storyBookContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${storyBookContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${storyBookContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
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
                        <div class="row peerReviewEvent indent" data-approved="${storyBookPeerReviewEvent.getApproved()}">
                            <div class="col s4">
                                <c:set var="chipContributor" value="${storyBookPeerReviewEvent.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${storyBookPeerReviewEvent.getApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${storyBookPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
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
    <h5 class="center">Word frequency</h5>
    
    <table class="bordered highlight">
        <thead>
            <th>Word</th>
            <th>Frequency</th>
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
                                <a href="<spring:url value='/content/word/create?autoFillText=${wordTextLowerCase}' />" class="autoFillWordLink" target="_blank">Add word <i class="material-icons">launch</i></a>
                            </c:when>
                            <c:otherwise>
                                <c:set var="word" value="${wordMap[wordTextLowerCase]}" />
                                <a href="<spring:url value='/content/word/edit/${word.id}' />" target="_blank">
                                    <c:out value="${word.text}" />
                                </a><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /><br />
                                <span class="grey-text">
                                    /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
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
    
    <h5 class="center">Letter frequency</h5>
    
    <table class="bordered highlight">
        <thead>
            <th>Letter</th>
            <th>Frequency</th>
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
