<content:title>
    <fmt:message key="pending.review" /> (${fn:length(wordContributionEventsPendingPeerReview)})
</content:title>

<content:section cssId="wordPeerReviewsPendingPage">
    <div class="section row">
        <c:if test="${empty wordContributionEventsPendingPeerReview}">
            <p>
                You have no pending peer-reviews 🎉
            </p>
        </c:if>
        <c:if test="${not empty wordContributionEventsPendingPeerReview}">
            <p>
                Press a word to peer-review it:
            </p>
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="text" /></th>
                    <th><fmt:message key="sounds" /></th>
                    <th><fmt:message key="word.type" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                </thead>
                <tbody>
                    <c:forEach var="wordContributionEvent" items="${wordContributionEventsPendingPeerReview}">
                        <c:set var="word" value="${wordContributionEvent.word}" />
                        <tr>
                            <td style="font-size: 2em;">
                                <a href="<spring:url value='/content/word/edit/${word.id}#peer-review' />" target="_blank">"<c:out value="${word.text}" />"</a>
                            </td>
                            <td style="font-size: 2em;">
                                /<c:forEach var="lsc" items="${word.letterSoundCorrespondences}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                            </td>
                            <td>
                                ${word.wordType}<br />
                                <c:out value=" ${emojisByWordId[word.id]}" />
                            </td>
                            <td>
                                <a href="<spring:url value='/content/contributor/${wordContributionEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty wordContributionEvent.contributor.imageUrl}">
                                                <img src="${wordContributionEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty wordContributionEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${wordContributionEvent.contributor.providerIdWeb3}" />.png" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty wordContributionEvent.contributor.firstName}">
                                                <c:out value="${wordContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${wordContributionEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty wordContributionEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(wordContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(wordContributionEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </td>
                            <td>
                                #${wordContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${wordContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${wordContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
