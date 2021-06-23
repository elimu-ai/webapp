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
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />" target="_blank">
                                    <c:out value="${storyBook.title}" />
                                </a>
                            </td>
                            <td>
                                <c:if test="${not empty storyBook.coverImage}">
                                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />" target="_blank">
                                        <img 
                                            src="<spring:url value='/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}' />" 
                                            style="max-width: 64px; border-radius: 8px;"/>
                                    </a>
                                </c:if>
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
                                <div class="chip">
                                    <img src="<spring:url value='${storyBookContributionEvent.contributor.imageUrl}' />" alt="${storyBookContributionEvent.contributor.firstName}" /> 
                                    <c:out value="${storyBookContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${storyBookContributionEvent.contributor.lastName}" />
                                </div>
                            </td>
                            <td>
                                #${storyBookContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${storyBookContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
