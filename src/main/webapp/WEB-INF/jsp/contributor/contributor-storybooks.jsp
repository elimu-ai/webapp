<div class="card-panel">
    <h5>Contributions (${fn:length(storyBookContributionEvents)})</h5>
    <c:if test="${empty storyBookContributionEvents}">
        <p>
            No storybook contributions.
        </p>
    </c:if>
    <c:if test="${not empty storyBookContributionEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Storybook</th>
                <th>Revision</th>
                <th>Time</th>
                <th>Comment</th>
                <th>Peer-reviews</th>
            </thead>
            <tbody>
                <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
                    <c:set var="storyBook" value="${storyBookContributionEvent.storyBook}" />
                    <tr>
                        <td>
                            <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-event_${storyBookContributionEvent.id}' />" target="_blank">
                                <c:out value="${storyBook.title}" />
                            </a><br/>
                            <c:choose>
                                <c:when test="${storyBook.readingLevel == 'LEVEL1'}">Level 1. Beginning to Read</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL2'}">Level 2. Learning to Read</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL3'}">Level 3. Reading Independently</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL4'}">Level 4. Reading Proficiently</c:when>
                            </c:choose>
                        </td>
                        <td>
                            #${storyBookContributionEvent.revisionNumber}
                        </td>
                        <td>
                            <fmt:formatDate value="${storyBookContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />
                        </td>
                        <td>
                            <blockquote><c:out value="${fn:substring(storyBookContributionEvent.comment, 0, 25)}" />...</blockquote>
                        </td>
                        <td>
                            <c:forEach var="storyBookPeerReviewEvent" items="${storyBookPeerReviewEventsByContributionMap[storyBookContributionEvent.id]}">
                                <c:if test="${storyBookPeerReviewEvent.storyBookContributionEvent.id == storyBookContributionEvent.id}">
                                    <div class="row peerReviewEvent indent" data-approved="${storyBookPeerReviewEvent.getApproved()}">
                                        <div class="col s4">
                                            <a href="<spring:url value='/contributor/${storyBookPeerReviewEvent.contributor.id}' />">
                                                <div class="chip">
                                                    <c:choose>
                                                        <c:when test="${not empty storyBookPeerReviewEvent.contributor.imageUrl}">
                                                            <img src="${storyBookPeerReviewEvent.contributor.imageUrl}" />
                                                        </c:when>
                                                        <c:when test="${not empty storyBookPeerReviewEvent.contributor.providerIdWeb3}">
                                                            <img src="https://effigy.im/a/<c:out value="${storyBookPeerReviewEvent.contributor.providerIdWeb3}" />.svg" />
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
                                            <div class="col s12 comment"><c:out value="${fn:substring(storyBookPeerReviewEvent.comment, 0, 25)}" />...</div>
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
    <h5>Peer-reviews (${fn:length(storyBookPeerReviewEvents)})</h5>
    <c:if test="${empty storyBookPeerReviewEvents}">
        <p>
            No storybook peer-reviews.
        </p>
    </c:if>
    <c:if test="${not empty storyBookPeerReviewEvents}">
        <table class="bordered highlight">
            <thead>
                <th>Peer-review</th>
                <th>Storybook</th>
                <th>Contributor</th>
            </thead>
            <tbody>
                <c:forEach var="storyBookPeerReviewEvent" items="${storyBookPeerReviewEvents}">
                    <c:set var="storyBook" value="${storyBookPeerReviewEvent.storyBookContributionEvent.storyBook}" />
                    <tr>
                        <td>
                            <div class="row peerReviewEvent" data-approved="${storyBookPeerReviewEvent.getApproved()}">
                                <div class="col s4">
                                    <a href="<spring:url value='/contributor/${storyBookPeerReviewEvent.contributor.id}' />">
                                        <div class="chip">
                                            <c:choose>
                                                <c:when test="${not empty storyBookPeerReviewEvent.contributor.imageUrl}">
                                                    <img src="${storyBookPeerReviewEvent.contributor.imageUrl}" />
                                                </c:when>
                                                <c:when test="${not empty storyBookPeerReviewEvent.contributor.providerIdWeb3}">
                                                    <img src="https://effigy.im/a/<c:out value="${storyBookPeerReviewEvent.contributor.providerIdWeb3}" />.svg" />
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
                                    <div class="col s12 comment"><c:out value="${fn:substring(storyBookPeerReviewEvent.comment, 0, 25)}" />...</div>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-event_${storyBookPeerReviewEvent.storyBookContributionEvent.id}' />" target="_blank">
                                <c:out value="${storyBook.title}" />
                            </a><br />
                            <c:choose>
                                <c:when test="${storyBook.readingLevel == 'LEVEL1'}">Level 1. Beginning to Read</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL2'}">Level 2. Learning to Read</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL3'}">Level 3. Reading Independently</c:when>
                                <c:when test="${storyBook.readingLevel == 'LEVEL4'}">Level 4. Reading Proficiently</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <a href="<spring:url value='/contributor/${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.id}' />">
                                <div class="chip">
                                    <c:choose>
                                        <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.imageUrl}">
                                            <img src="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.imageUrl}" />
                                        </c:when>
                                        <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3}">
                                            <img src="https://effigy.im/a/<c:out value="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3}" />.svg" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<spring:url value='/static/img/placeholder.png' />" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.firstName}">
                                            <c:out value="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.lastName}" />
                                        </c:when>
                                        <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3}">
                                            ${fn:substring(storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3, 38, 42)}
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
