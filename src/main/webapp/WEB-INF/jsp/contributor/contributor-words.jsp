<div class="card-panel">
    <h5>Contributions (${fn:length(wordContributionEvents)})</h5>
    <c:if test="${empty wordContributionEvents}">
        <p>
            No word contributions.
        </p>
    </c:if>
    <c:if test="${not empty wordContributionEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Word</th>
                <th>Revision</th>
                <th>Time</th>
                <th>Comment</th>
                <th>Peer-reviews</th>
            </thead>
            <tbody>
                <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
                    <c:set var="word" value="${wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${word.id}#contribution-events' />" target="_blank">"<c:out value="${word.text}" />"</a><br />
                            /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                        </td>
                        <td>
                            #${wordContributionEvent.revisionNumber}
                        </td>
                        <td>
                            <fmt:formatDate value="${wordContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />
                        </td>
                        <td>
                            <blockquote><c:out value="${fn:substring(wordContributionEvent.comment, 0, 25)}" />...</blockquote>
                        </td>
                        <td>
                            <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEventsByContributionMap[wordContributionEvent.id]}">
                                <c:if test="${wordPeerReviewEvent.wordContributionEvent.id == wordContributionEvent.id}">
                                    <div class="row peerReviewEvent indent" data-approved="${wordPeerReviewEvent.getApproved()}">
                                        <div class="col s4">
                                            <c:set var="chipContributor" value="${wordPeerReviewEvent.contributor}" />
                                            <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                                        </div>
                                        <div class="col s4">
                                            <code class="peerReviewStatus">
                                                <c:choose>
                                                    <c:when test="${wordPeerReviewEvent.getApproved()}">
                                                        APPROVED
                                                    </c:when>
                                                    <c:otherwise>
                                                        NOT_APPROVED
                                                    </c:otherwise>
                                                </c:choose>
                                            </code>
                                        </div>
                                        <div class="col s4" style="text-align: right;">
                                            <fmt:formatDate value="${wordPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
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
    <h5>Peer-reviews (${fn:length(wordPeerReviewEvents)})</h5>
    <c:if test="${empty wordPeerReviewEvents}">
        <p>
            No word peer-reviews.
        </p>
    </c:if>
    <c:if test="${not empty wordPeerReviewEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Peer-review</th>
                <th>Word</th>
                <th>Contributor</th>
            </thead>
            <tbody>
                <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEvents}">
                    <c:set var="word" value="${wordPeerReviewEvent.wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <div class="row peerReviewEvent" data-approved="${wordPeerReviewEvent.getApproved()}">
                                <div class="col s4">
                                    <c:set var="chipContributor" value="${wordPeerReviewEvent.contributor}" />
                                    <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                                </div>
                                <div class="col s4">
                                    <code class="peerReviewStatus">
                                        <c:choose>
                                            <c:when test="${wordPeerReviewEvent.getApproved()}">
                                                APPROVED
                                            </c:when>
                                            <c:otherwise>
                                                NOT_APPROVED
                                            </c:otherwise>
                                        </c:choose>
                                    </code>
                                </div>
                                <div class="col s4" style="text-align: right;">
                                    <fmt:formatDate value="${wordPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                </div>
                                <c:if test="${not empty wordPeerReviewEvent.comment}">
                                    <div class="col s12 comment"><c:out value="${fn:substring(wordPeerReviewEvent.comment, 0, 25)}" />...</div>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${word.id}#contribution-event_${wordPeerReviewEvent.wordContributionEvent.id}' />" target="_blank">"<c:out value="${word.text}" />"</a><br />
                            /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                        </td>
                        <td>
                            <c:set var="chipContributor" value="${wordPeerReviewEvent.wordContributionEvent.contributor}" />
                            <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
