<content:title>
    <fmt:message key="pending.review" /> (${fn:length(numberContributionEventsPendingPeerReview)})
</content:title>

<content:section cssId="numberPeerReviewsPendingPage">
    <div class="section row">
        <c:if test="${empty numberContributionEventsPendingPeerReview}">
            <p>
                You have no pending peer-reviews 🎉
            </p>
        </c:if>
        <c:if test="${not empty numberContributionEventsPendingPeerReview}">
            <p>
                Press a number to peer-review it:
            </p>
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="value" /></th>
                    <th><fmt:message key="symbol" /></th>
                    <th><fmt:message key="number.words" /></th>
                    <th><fmt:message key="sounds" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                </thead>
                <tbody>
                    <c:forEach var="numberContributionEvent" items="${numberContributionEventsPendingPeerReview}">
                        <c:set var="number" value="${numberContributionEvent.number}" />
                        <tr>
                            <td style="font-size: 2em;">
                            <a name="${number.id}"></a>
                            <a href="<spring:url value='/content/number/edit/${number.id}#peer-review' />" target="_blank">${number.value}</a>
                        </td>
                        <td style="font-size: 2em;">
                            ${number.symbol}
                        </td>
                        <td style="font-size: 2em;">
                            <c:forEach var="word" items="${number.words}">
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">${word.text}</a>
                            </c:forEach>
                        </td>
                        
                        <td style="font-size: 2em;">
                            <c:forEach var="word" items="${number.words}">
                                /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                            </c:forEach>
                        </td>
                            <td>
                                <a href="<spring:url value='/content/contributor/${numberContributionEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty numberContributionEvent.contributor.imageUrl}">
                                                <img src="${numberContributionEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty numberContributionEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${numberContributionEvent.contributor.providerIdWeb3}" />.png" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty numberContributionEvent.contributor.firstName}">
                                                <c:out value="${numberContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${numberContributionEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty numberContributionEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(numberContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(numberContributionEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </td>
                            <td>
                                #${numberContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${numberContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${numberContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
