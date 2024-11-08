<content:title>
    <fmt:message key="pending.review" /> (${fn:length(storyBookContributionEventsPendingPeerReview)})
</content:title>

<content:section cssId="storyBookPeerReviewsPendingPage">
    <div class="section row">
        <c:if test="${empty storyBookContributionEventsPendingPeerReview}">
            <p>
                You have no pending peer-reviews 🎉
            </p>
        </c:if>
        <c:if test="${not empty storyBookContributionEventsPendingPeerReview}">
            <p>
                Press a storybook to peer-review it:
            </p>
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="title" /></th>
                    <th><fmt:message key="cover.image" /></th>
                    <th><fmt:message key="description" /></th>
                    <th><fmt:message key="reading.level" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                </thead>
                <tbody>
                    <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEventsPendingPeerReview}">
                        <c:set var="storyBook" value="${storyBookContributionEvent.storyBook}" />
                        <tr>
                            <td>
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#peer-review' />" target="_blank">
                                    <c:out value="${storyBook.title}" />
                                </a>
                            </td>
                            <td>
                                <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                                <c:if test="${not empty storyBook.coverImage}">
                                    <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                                </c:if>
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#peer-review' />" target="_blank">
                                    <img 
                                        src="<spring:url value='${coverImageUrl}' />" 
                                        style="max-width: 64px; border-radius: 8px;"/>
                                </a>
                            </td>
                            <td>
                                <c:if test="${not empty storyBook.description}">
                                    <c:out value="${fn:substring(storyBook.description, 0, 25)}" />...
                                </c:if>
                            </td>
                            <td>
                                <fmt:message key="reading.level.${storyBook.readingLevel}" />
                            </td>
                            <td>
                                <a href="<spring:url value='/contributor/${storyBookContributionEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty storyBookContributionEvent.contributor.imageUrl}">
                                                <img src="${storyBookContributionEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty storyBookContributionEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${storyBookContributionEvent.contributor.providerIdWeb3}" />.png" />
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
                            </td>
                            <td>
                                #${storyBookContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${storyBookContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
