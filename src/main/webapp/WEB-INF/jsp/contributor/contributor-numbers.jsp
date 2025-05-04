<div class="card-panel">
    <h5>Contributions (${fn:length(numberContributionEvents)})</h5>
    <c:if test="${empty numberContributionEvents}">
        <p>
            No number contributions.
        </p>
    </c:if>
    <c:if test="${not empty numberContributionEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Number</th>
                <th>Revision</th>
                <th>Time</th>
                <th>Comment</th>
                <th>Peer-reviews</th>
            </thead>
            <tbody>
                <c:forEach var="numberContributionEvent" items="${numberContributionEvents}">
                    <c:set var="number" value="${numberContributionEvent.number}" />
                    <tr>
                        <td>
                            <a href="<spring:url value='/content/number/edit/${number.id}#contribution-events' />" target="_blank">${number.value}</a><br />
                            <c:forEach var="numberWord" items="${number.words}">
                                <a href="<spring:url value='/content/word/edit/${numberWord.id}' />">
                                    <c:out value="${numberWord.text}" />
                                </a>
                            </c:forEach>
                        </td>
                        <td>
                            #${numberContributionEvent.revisionNumber}
                        </td>
                        <td>
                            <fmt:formatDate value="${numberContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />
                        </td>
                        <td>
                            <blockquote><c:out value="${fn:substring(numberContributionEvent.comment, 0, 25)}" />...</blockquote>
                        </td>
                        <td>
                            <c:forEach var="numberPeerReviewEvent" items="${numberPeerReviewEventsByContributionMap[numberContributionEvent.id]}">
                                <c:if test="${numberPeerReviewEvent.numberContributionEvent.id == numberContributionEvent.id}">
                                    <div class="row peerReviewEvent indent" data-approved="${numberPeerReviewEvent.getApproved()}">
                                        <div class="col s4">
                                            <c:set var="chipContributor" value="${numberPeerReviewEvent.contributor}" />
                                            <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                                        </div>
                                        <div class="col s4">
                                            <code class="peerReviewStatus">
                                                <c:choose>
                                                    <c:when test="${numberPeerReviewEvent.getApproved()}">
                                                        APPROVED
                                                    </c:when>
                                                    <c:otherwise>
                                                        NOT_APPROVED
                                                    </c:otherwise>
                                                </c:choose>
                                            </code>
                                        </div>
                                        <div class="col s4" style="text-align: right;">
                                            <fmt:formatDate value="${numberPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                        </div>
                                        <c:if test="${not empty numberPeerReviewEvent.comment}">
                                            <div class="col s12 comment"><c:out value="${fn:substring(numberPeerReviewEvent.comment, 0, 25)}" />...</div>
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
    <h5>Peer-reviews (${fn:length(numberPeerReviewEvents)})</h5>
    <c:if test="${empty numberPeerReviewEvents}">
        <p>
            No number peer-reviews.
        </p>
    </c:if>
    <c:if test="${not empty numberPeerReviewEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Peer-review</th>
                <th>Number</th>
                <th>Contributor</th>
            </thead>
            <tbody>
                <c:forEach var="numberPeerReviewEvent" items="${numberPeerReviewEvents}">
                    <c:set var="number" value="${numberPeerReviewEvent.numberContributionEvent.number}" />
                    <tr>
                        <td>
                            <div class="row peerReviewEvent" data-approved="${numberPeerReviewEvent.getApproved()}">
                                <div class="col s4">
                                    <c:set var="chipContributor" value="${numberPeerReviewEvent.contributor}" />
                                    <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                                </div>
                                <div class="col s4">
                                    <code class="peerReviewStatus">
                                        <c:choose>
                                            <c:when test="${numberPeerReviewEvent.getApproved()}">
                                                APPROVED
                                            </c:when>
                                            <c:otherwise>
                                                NOT_APPROVED
                                            </c:otherwise>
                                        </c:choose>
                                    </code>
                                </div>
                                <div class="col s4" style="text-align: right;">
                                    <fmt:formatDate value="${numberPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                </div>
                                <c:if test="${not empty numberPeerReviewEvent.comment}">
                                    <div class="col s12 comment"><c:out value="${fn:substring(numberPeerReviewEvent.comment, 0, 25)}" />...</div>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/number/edit/${number.id}#contribution-event_${numberPeerReviewEvent.numberContributionEvent.id}' />" target="_blank">${number.value}</a><br />
                            <c:forEach var="numberWord" items="${number.words}">
                                <a href="<spring:url value='/content/word/edit/${numberWord.id}' />">
                                    <c:out value="${numberWord.text}" />
                                </a>
                            </c:forEach>
                        </td>
                        <td>
                            <c:set var="chipContributor" value="${numberPeerReviewEvent.numberContributionEvent.contributor}" />
                            <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
