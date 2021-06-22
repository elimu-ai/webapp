<content:title>
    <fmt:message key="pending.review" /> (${fn:length(wordContributionEvents)})
</content:title>

<content:section cssId="wordPeerReviewsPendingPage">
    <div class="section row">
        <c:if test="${empty wordContributionEvents}">
            <p>
                You have no pending peer reviews 🎉
            </p>
        </c:if>
        <c:if test="${not empty wordContributionEvents}">
            <p>
                Press a word to peer-review it:
            </p>
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="text" /></th>
                    <th><fmt:message key="allophones" /></th>
                    <th><fmt:message key="word.type" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                </thead>
                <tbody>
                    <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
                        <c:set var="word" value="${wordContributionEvent.word}" />
                        <tr>
                            <td style="font-size: 2em;">
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">"<c:out value="${word.text}" />"</a>
                            </td>
                            <td style="font-size: 2em;">
                                /<c:forEach var="ltam" items="${word.letterToAllophoneMappings}">&nbsp;<a href="<spring:url value='/content/letter-to-allophone-mapping/edit/${ltam.id}' />"><c:forEach var="allophone" items="${ltam.allophones}">${allophone.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                            </td>
                            <td>
                                ${word.wordType}<br />
                                <c:out value=" ${emojisByWordId[word.id]}" />
                            </td>
                            <td>
                                <div class="chip">
                                    <img src="<spring:url value='${wordContributionEvent.contributor.imageUrl}' />" alt="${wordContributionEvent.contributor.firstName}" /> 
                                    <c:out value="${wordContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${wordContributionEvent.contributor.lastName}" />
                                </div>
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
