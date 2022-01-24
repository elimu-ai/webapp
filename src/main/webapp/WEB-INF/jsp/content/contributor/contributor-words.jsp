<div class="card-panel">
    <h5><fmt:message key="contributions" /> (${fn:length(wordContributionEvents)})</h5>
    <c:if test="${empty wordContributionEvents}">
        <p>
            No word contributions.
        </p>
    </c:if>
    <c:if test="${not empty wordContributionEvents}">
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="word" /></th>
                <th><fmt:message key="revision" /></th>
                <th><fmt:message key="time" /></th>
                <th><fmt:message key="comment" /></th>
                <th><fmt:message key="peer.reviews" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
                    <c:set var="word" value="${wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${word.id}#contribution-events' />" target="_blank">"<c:out value="${word.text}" />"</a><br />
                            /<c:forEach var="lsc" items="${word.letterSoundCorrespondences}">&nbsp;<a href="<spring:url value='/content/letter-sound-correspondence/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                        </td>
                        <td>
                            #${wordContributionEvent.revisionNumber}<br />
                            <span class="grey-text">(<fmt:formatNumber maxFractionDigits="0" value="${wordContributionEvent.timeSpentMs / 1000 / 60}" /> min)</span>
                        </td>
                        <td>
                            <fmt:formatDate value="${wordContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                        </td>
                        <td>
                            <blockquote><c:out value="${fn:substring(wordContributionEvent.comment, 0, 25)}" />...</blockquote>
                        </td>
                        <td>
                            <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEventsByContributionMap[wordContributionEvent.id]}">
                                <c:if test="${wordPeerReviewEvent.wordContributionEvent.id == wordContributionEvent.id}">
                                    <div class="row peerReviewEvent indent" data-approved="${wordPeerReviewEvent.isApproved()}">
                                        <div class="col s4">
                                            <a href="<spring:url value='/content/contributor/${wordPeerReviewEvent.contributor.id}' />">
                                                <div class="chip">
                                                    <c:choose>
                                                        <c:when test="${not empty wordPeerReviewEvent.contributor.imageUrl}">
                                                            <img src="${wordPeerReviewEvent.contributor.imageUrl}" />
                                                        </c:when>
                                                        <c:when test="${not empty wordPeerReviewEvent.contributor.providerIdWeb3}">
                                                            <img src="http://62.75.236.14:3000/identicon/<c:out value="${wordPeerReviewEvent.contributor.providerIdWeb3}" />" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<spring:url value='/static/img/placeholder.png' />" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${not empty wordPeerReviewEvent.contributor.firstName}">
                                                            <c:out value="${wordPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${wordPeerReviewEvent.contributor.lastName}" />
                                                        </c:when>
                                                        <c:when test="${not empty wordPeerReviewEvent.contributor.providerIdWeb3}">
                                                            ${fn:substring(wordPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(wordPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </a>
                                        </div>
                                        <div class="col s4">
                                            <code class="peerReviewStatus">
                                                <c:choose>
                                                    <c:when test="${wordPeerReviewEvent.isApproved()}">
                                                        APPROVED
                                                    </c:when>
                                                    <c:otherwise>
                                                        NOT_APPROVED
                                                    </c:otherwise>
                                                </c:choose>
                                            </code>
                                        </div>
                                        <div class="col s4" style="text-align: right;">
                                            <fmt:formatDate value="${wordPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                        </div>
                                        <c:if test="${not empty wordPeerReviewEvent.comment}">
                                            <div class="col s12 comment"><c:out value="${fn:substring(wordPeerReviewEvent.comment, 0, 25)}" />...</div>
                                        </c:if>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<div class="card-panel">
    <h5><fmt:message key="peer.reviews" /> (${fn:length(wordPeerReviewEvents)})</h5>
    <c:if test="${empty wordPeerReviewEvents}">
        <p>
            No word peer-reviews.
        </p>
    </c:if>
    <c:if test="${not empty wordPeerReviewEvents}">
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="peer.review" /></th>
                <th><fmt:message key="word" /></th>
                <th><fmt:message key="contributor" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEvents}">
                    <c:set var="word" value="${wordPeerReviewEvent.wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <div class="row peerReviewEvent" data-approved="${wordPeerReviewEvent.isApproved()}">
                                <div class="col s4">
                                    <a href="<spring:url value='/content/contributor/${wordPeerReviewEvent.contributor.id}' />">
                                        <div class="chip">
                                            <c:choose>
                                                <c:when test="${not empty wordPeerReviewEvent.contributor.imageUrl}">
                                                    <img src="${wordPeerReviewEvent.contributor.imageUrl}" />
                                                </c:when>
                                                <c:when test="${not empty wordPeerReviewEvent.contributor.providerIdWeb3}">
                                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${wordPeerReviewEvent.contributor.providerIdWeb3}" />" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${not empty wordPeerReviewEvent.contributor.firstName}">
                                                    <c:out value="${wordPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${wordPeerReviewEvent.contributor.lastName}" />
                                                </c:when>
                                                <c:when test="${not empty wordPeerReviewEvent.contributor.providerIdWeb3}">
                                                    ${fn:substring(wordPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(wordPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </a>
                                </div>
                                <div class="col s4">
                                    <code class="peerReviewStatus">
                                        <c:choose>
                                            <c:when test="${wordPeerReviewEvent.isApproved()}">
                                                APPROVED
                                            </c:when>
                                            <c:otherwise>
                                                NOT_APPROVED
                                            </c:otherwise>
                                        </c:choose>
                                    </code>
                                </div>
                                <div class="col s4" style="text-align: right;">
                                    <fmt:formatDate value="${wordPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                </div>
                                <c:if test="${not empty wordPeerReviewEvent.comment}">
                                    <div class="col s12 comment"><c:out value="${fn:substring(wordPeerReviewEvent.comment, 0, 25)}" />...</div>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${word.id}#contribution-event_${wordPeerReviewEvent.wordContributionEvent.id}' />" target="_blank">"<c:out value="${word.text}" />"</a><br />
                            /<c:forEach var="lsc" items="${word.letterSoundCorrespondences}">&nbsp;<a href="<spring:url value='/content/letter-sound-correspondence/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                        </td>
                        <td>
                            <a href="<spring:url value='/content/contributor/${wordPeerReviewEvent.wordContributionEvent.contributor.id}' />">
                                <div class="chip">
                                    <c:choose>
                                        <c:when test="${not empty wordPeerReviewEvent.wordContributionEvent.contributor.imageUrl}">
                                            <img src="${wordPeerReviewEvent.wordContributionEvent.contributor.imageUrl}" />
                                        </c:when>
                                        <c:when test="${not empty wordPeerReviewEvent.wordContributionEvent.contributor.providerIdWeb3}">
                                            <img src="http://62.75.236.14:3000/identicon/<c:out value="${wordPeerReviewEvent.wordContributionEvent.contributor.providerIdWeb3}" />" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<spring:url value='/static/img/placeholder.png' />" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty wordPeerReviewEvent.wordContributionEvent.contributor.firstName}">
                                            <c:out value="${wordPeerReviewEvent.wordContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${wordPeerReviewEvent.wordContributionEvent.contributor.lastName}" />
                                        </c:when>
                                        <c:when test="${not empty wordPeerReviewEvent.wordContributionEvent.contributor.providerIdWeb3}">
                                            ${fn:substring(wordPeerReviewEvent.wordContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(wordPeerReviewEvent.wordContributionEvent.contributor.providerIdWeb3, 38, 42)}
                                        </c:when>
                                    </c:choose>
                                </div>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
