<content:title>
    <fmt:message key="pending.review" /> (${fn:length(letterSoundCorrespondenceContributionEventsPendingPeerReview)})
</content:title>

<content:section cssId="letterSoundCorrespondencePeerReviewsPendingPage">
    <div class="section row">
        <c:if test="${empty letterSoundCorrespondenceContributionEventsPendingPeerReview}">
            <p>
                You have no pending peer-reviews 🎉
            </p>
        </c:if>
        <c:if test="${not empty letterSoundCorrespondenceContributionEventsPendingPeerReview}">
            <p>
                Press "Peer-review" to peer-review a letter-sound correspondence:
            </p>
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="letters" /></th>
                    <th></th>
                    <th><fmt:message key="sounds" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                    <th><fmt:message key="peer.review" /></th>
                </thead>
                <tbody>
                    <c:forEach var="letterSoundCorrespondenceContributionEvent" items="${letterSoundCorrespondenceContributionEventsPendingPeerReview}">
                        <c:set var="letterSoundCorrespondence" value="${letterSoundCorrespondenceContributionEvent.letterSoundCorrespondence}" />
                        <tr>
                            <td style="font-size: 2em;">
                                " <c:forEach var="letter" items="${letterSoundCorrespondence.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />">${letter.text} </a> </c:forEach> "
                            </td>
                            <td style="font-size: 2em;">
                                ➞
                            </td>
                            <td style="font-size: 2em;">
                                / <c:forEach var="sound" items="${letterSoundCorrespondence.sounds}"><a href="<spring:url value='/content/sound/edit/${sound.id}' />">${sound.valueIpa}</a> </c:forEach> /
                            </td>
                            <td>
                                <a href="<spring:url value='/content/contributor/${letterSoundCorrespondenceContributionEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty letterSoundCorrespondenceContributionEvent.contributor.imageUrl}">
                                                <img src="${letterSoundCorrespondenceContributionEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty letterSoundCorrespondenceContributionEvent.contributor.providerIdWeb3}">
                                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${letterSoundCorrespondenceContributionEvent.contributor.providerIdWeb3}" />" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty letterSoundCorrespondenceContributionEvent.contributor.firstName}">
                                                <c:out value="${letterSoundCorrespondenceContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${letterSoundCorrespondenceContributionEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty letterSoundCorrespondenceContributionEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(letterSoundCorrespondenceContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(letterSoundCorrespondenceContributionEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </td>
                            <td>
                                #${letterSoundCorrespondenceContributionEvent.revisionNumber} (<fmt:formatNumber maxFractionDigits="0" value="${letterSoundCorrespondenceContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                            </td>
                            <td>
                                <fmt:formatDate value="${letterSoundCorrespondenceContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <a href="<spring:url value='/content/letter-sound-correspondence/edit/${letterSoundCorrespondenceContributionEvent.letterSoundCorrespondence.id}#peer-review' />" target="_blank"><fmt:message key="peer.review" /></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
