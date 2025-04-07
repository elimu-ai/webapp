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
                                            <a href="<spring:url value='/contributor/${numberPeerReviewEvent.contributor.id}' />">
                                                <div class="chip">
                                                    <c:choose>
                                                        <c:when test="${not empty numberPeerReviewEvent.contributor.imageUrl}">
                                                            <img src="${numberPeerReviewEvent.contributor.imageUrl}" />
                                                        </c:when>
                                                        <c:when test="${not empty numberPeerReviewEvent.contributor.providerIdWeb3}">
                                                            <img src="https://effigy.im/a/<c:out value="${numberPeerReviewEvent.contributor.providerIdWeb3}" />.svg" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<spring:url value='/static/img/placeholder.png' />" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${not empty numberPeerReviewEvent.contributor.firstName}">
                                                            <c:out value="${numberPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${numberPeerReviewEvent.contributor.lastName}" />
                                                        </c:when>
                                                        <c:when test="${not empty numberPeerReviewEvent.contributor.providerIdWeb3}">
                                                            ${fn:substring(numberPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(numberPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </a>
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
                                    <a href="<spring:url value='/contributor/${numberPeerReviewEvent.contributor.id}' />">
                                        <div class="chip">
                                            <c:choose>
                                                <c:when test="${not empty numberPeerReviewEvent.contributor.imageUrl}">
                                                    <img src="${numberPeerReviewEvent.contributor.imageUrl}" />
                                                </c:when>
                                                <c:when test="${not empty numberPeerReviewEvent.contributor.providerIdWeb3}">
                                                    <img src="https://effigy.im/a/<c:out value="${numberPeerReviewEvent.contributor.providerIdWeb3}" />.svg" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${not empty numberPeerReviewEvent.contributor.firstName}">
                                                    <c:out value="${numberPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${numberPeerReviewEvent.contributor.lastName}" />
                                                </c:when>
                                                <c:when test="${not empty numberPeerReviewEvent.contributor.providerIdWeb3}">
                                                    ${fn:substring(numberPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(numberPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </a>
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
                            <a href="<spring:url value='/contributor/${numberPeerReviewEvent.numberContributionEvent.contributor.id}' />">
                                <div class="chip">
                                    <c:choose>
                                        <c:when test="${not empty numberPeerReviewEvent.numberContributionEvent.contributor.imageUrl}">
                                            <img src="${numberPeerReviewEvent.numberContributionEvent.contributor.imageUrl}" />
                                        </c:when>
                                        <c:when test="${not empty numberPeerReviewEvent.numberContributionEvent.contributor.providerIdWeb3}">
                                            <img src="https://effigy.im/a/<c:out value="${numberPeerReviewEvent.numberContributionEvent.contributor.providerIdWeb3}" />.svg" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<spring:url value='/static/img/placeholder.png' />" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty numberPeerReviewEvent.numberContributionEvent.contributor.firstName}">
                                            <c:out value="${numberPeerReviewEvent.numberContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${numberPeerReviewEvent.numberContributionEvent.contributor.lastName}" />
                                        </c:when>
                                        <c:when test="${not empty numberPeerReviewEvent.numberContributionEvent.contributor.providerIdWeb3}">
                                            ${fn:substring(numberPeerReviewEvent.numberContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(numberPeerReviewEvent.numberContributionEvent.contributor.providerIdWeb3, 38, 42)}
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
