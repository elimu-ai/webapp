<content:title>
    <c:choose>
        <c:when test="${not empty contributor2.firstName}">
            <c:out value="${contributor2.firstName}" />&nbsp;<c:out value="${contributor2.lastName}" />
        </c:when>
        <c:when test="${not empty contributor2.providerIdWeb3}">
            ${fn:substring(contributor2.providerIdWeb3, 0, 6)}...${fn:substring(contributor2.providerIdWeb3, 38, 42)}
        </c:when>
    </c:choose>
</content:title>

<content:section cssId="contributorStoryBooksPage">
    <%@ include file="/WEB-INF/jsp/content/contributor/contributor-summarized.jsp" %>
    
    <ul class="tabs tabs-transparent deep-purple lighten-1 z-depth-1" style="border-radius: 8px;">
        <li class="tab"><a class="active" href="<spring:url value='/content/contributor/${contributor2.id}/storybooks' />">Storybooks (${storyBookContributionsCount + storyBookPeerReviewsCount})</a></li>
        <li class="tab"><a href="<spring:url value='/content/contributor/${contributor2.id}/audios' />">Audios (${audioContributionsCount + audioPeerReviewsCount})</a></li>
        <li class="tab"><a href="<spring:url value='/content/contributor/${contributor2.id}/words' />">Words (${wordContributionsCount + wordPeerReviewsCount})</a></li>
        <li class="tab"><a href="<spring:url value='/content/contributor/${contributor2.id}/numbers' />">Numbers (${numberContributionsCount + numberPeerReviewsCount})</a></li>
    </ul>
    
    <div class="card-panel">
        <h5><fmt:message key="contributions" /> (${fn:length(storyBookContributionEvents)})</h5>
        <c:if test="${empty storyBookContributionEvents}">
            <p>
                No storybook contributions.
            </p>
        </c:if>
        <c:if test="${not empty storyBookContributionEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="title" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                    <th><fmt:message key="comment" /></th>
                    <th><fmt:message key="peer.review" />(s)</th>
                </thead>
                <tbody>
                    <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
                        <c:set var="storyBook" value="${storyBookContributionEvent.storyBook}" />
                        <tr>
                            <td>
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />" target="_blank">
                                    <c:out value="${storyBook.title}" />
                                </a>
                            </td>
                            <td>
                                #${storyBookContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${storyBookContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <blockquote>
                                    <c:out value="${storyBookContributionEvent.comment}" />
                                </blockquote>
                            </td>
                            <td>
                                // TODO
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="card-panel">
        <h5><fmt:message key="peer.reviews" /> (${fn:length(storyBookPeerReviewEvents)})</h5>
        ...
    </div>
</content:section>
