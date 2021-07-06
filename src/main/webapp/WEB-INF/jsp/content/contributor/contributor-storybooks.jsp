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
                    <th><fmt:message key="storybook" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="time" /></th>
                    <th><fmt:message key="comment" /></th>
                    <th><fmt:message key="peer.reviews" /></th>
                </thead>
                <tbody>
                    <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
                        <c:set var="storyBook" value="${storyBookContributionEvent.storyBook}" />
                        <tr>
                            <td>
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-event_${storyBookContributionEvent.id}' />" target="_blank">
                                    <c:out value="${storyBook.title}" />
                                </a>
                            </td>
                            <td>
                                #${storyBookContributionEvent.revisionNumber}<br />
                                <span class="grey-text">(<fmt:formatNumber maxFractionDigits="0" value="${storyBookContributionEvent.timeSpentMs / 1000 / 60}" /> min)</span>
                            </td>
                            <td>
                                <fmt:formatDate value="${storyBookContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <blockquote>
                                    <c:out value="${storyBookContributionEvent.comment}" />
                                </blockquote>
                                <c:if test="${not empty storyBookContributionEvent.paragraphTextBefore}">
                                    <p id="textDiffContainer_${storyBookContributionEvent.id}"></p>
                                    <script>
                                        $(function() {
                                            // Visualize before/after diff
                                            var textBefore = ['${fn:join(fn:split(fn:replace(storyBookContributionEvent.paragraphTextBefore, newLineCharRn, '<br/>'), ' '), '\', \'')}'];
                                            var textAfter = ['${fn:join(fn:split(fn:replace(storyBookContributionEvent.paragraphTextAfter, newLineCharRn, '<br/>'), ' '), '\', \'')}'];
                                            var unifiedDiff = difflib.unifiedDiff(textBefore, textAfter);
                                            console.info('unifiedDiff: \n' + unifiedDiff);
                                            for (var i = 2; i < unifiedDiff.length; i++) {
                                                var diff = unifiedDiff[i];
                                                if (diff.startsWith('@@')) {
                                                    diff = '<span class="grey-text">' + diff + '</span><br />';
                                                    if (i > 2) {
                                                        diff = '<br /><br />' + diff;
                                                    }
                                                } else if (diff.startsWith('-')) {
                                                    diff = '<span class="diff-deletion">' + diff.substring(1) + '<span>';
                                                } else if (diff.startsWith('+')) {
                                                    diff = '<span class="diff-addition">' + diff.substring(1) + '<span>';
                                                }
                                                $('#textDiffContainer_${storyBookContributionEvent.id}').append(diff);
                                            }
                                        });
                                    </script>
                                </c:if>
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
        <c:if test="${empty storyBookPeerReviewEvents}">
            <p>
                No storybook contributions.
            </p>
        </c:if>
        <c:if test="${not empty storyBookPeerReviewEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="peer.review" /></th>
                    <th><fmt:message key="storybook" /></th>
                    <th><fmt:message key="contributor" /></th>
                </thead>
                <tbody>
                    <c:forEach var="storyBookPeerReviewEvent" items="${storyBookPeerReviewEvents}">
                        <c:set var="storyBook" value="${storyBookPeerReviewEvent.storyBookContributionEvent.storyBook}" />
                        <tr>
                            <td>
                                <div class="row peerReviewEvent" data-approved="${storyBookPeerReviewEvent.isApproved()}">
                                    <div class="col s4">
                                        <a href="<spring:url value='/content/contributor/${storyBookPeerReviewEvent.contributor.id}' />">
                                            <div class="chip">
                                                <c:choose>
                                                    <c:when test="${not empty storyBookPeerReviewEvent.contributor.imageUrl}">
                                                        <img src="${storyBookPeerReviewEvent.contributor.imageUrl}" />
                                                    </c:when>
                                                    <c:when test="${not empty storyBookPeerReviewEvent.contributor.providerIdWeb3}">
                                                        <img src="http://62.75.236.14:3000/identicon/<c:out value="${storyBookPeerReviewEvent.contributor.providerIdWeb3}" />" />
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
                                                <c:when test="${storyBookPeerReviewEvent.isApproved()}">
                                                    APPROVED
                                                </c:when>
                                                <c:otherwise>
                                                    NOT_APPROVED
                                                </c:otherwise>
                                            </c:choose>
                                        </code>
                                    </div>
                                    <div class="col s4" style="text-align: right;">
                                        <fmt:formatDate value="${storyBookPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                                    </div>
                                    <c:if test="${not empty storyBookPeerReviewEvent.comment}">
                                        <div class="col s12 comment"><c:out value="${storyBookPeerReviewEvent.comment}" /></div>
                                    </c:if>
                                </div>
                            </td>
                            <td>
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-event_${storyBookPeerReviewEvent.id}' />" target="_blank">
                                    <c:out value="${storyBook.title}" />
                                </a>
                            </td>
                            <td>
                                <a href="<spring:url value='/content/contributor/${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.imageUrl}">
                                                <img src="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3}">
                                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${storyBookPeerReviewEvent.storyBookContributionEvent.contributor.providerIdWeb3}" />" />
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
</content:section>
