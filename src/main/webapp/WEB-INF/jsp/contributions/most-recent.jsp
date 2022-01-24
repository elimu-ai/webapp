<content:title>
    <fmt:message key="contributions" />
</content:title>

<content:section cssId="contributionsPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <h5>Most Recent Storybook Contributions</h5>
        <div class="row">
            <c:forEach var="storyBookContributionEvent" items="${storyBookContributionEvents}">
                <c:set var="storyBook" value="${storyBookContributionEvent.storyBook}" />
                <div class="col s12 m6 l4">
                    <div class="storyBook card">
                        <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                        <c:if test="${not empty storyBook.coverImage}">
                            <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                        </c:if>
                        <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                            <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                                <span class="card-title"><c:out value="${storyBook.title}" /></span>
                            </div>
                        </a>
                        <div class="card-content">
                            <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                            <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                            <p><fmt:message key="revision" />: #${storyBookContributionEvent.revisionNumber}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="divider" style="margin: 1.5em 0 2em 0;"></div>
        
        <h5>Most Recent Word Contributions</h5>
        <table class="bordered striped highlight">
            <thead>
                <th><fmt:message key="text" /></th>
                <th><fmt:message key="sounds" /></th>
                <th><fmt:message key="word.type" /></th>
                <th><fmt:message key="revision" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
                    <c:set var="word" value="${wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />">"${word.text}"</a>
                        </td>
                        <td>
                            /<c:forEach var="lsc" items="${word.letterSoundCorrespondences}">&nbsp;<a href="<spring:url value='/content/letter-sound-correspondence/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.allophones}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                        </td>
                        <td>
                            ${word.wordType}<br />
                            <c:out value=" ${emojisByWordId[word.id]}" />
                        </td>
                        <td>
                            <p>#${wordContributionEvent.revisionNumber}</p>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="top.contributors" /> 🏆</h5>
    <div class="card-panel deep-purple lighten-5">
        <b><fmt:message key="storybooks" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributor" items="${contributorsWithStoryBookContributions}">
                <li>
                    <a href="<spring:url value='/content/contributor/${contributor.id}' />">
                        <div class="chip">
                            <c:choose>
                                <c:when test="${not empty contributor.imageUrl}">
                                    <img src="${contributor.imageUrl}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty contributor.firstName}">
                                    <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                </c:when>
                            </c:choose>
                        </div> (${storyBookContributionsCountMap[contributor.id]})
                    </a>
                </li>
            </c:forEach>
        </ol>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <b><fmt:message key="audios" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributor" items="${contributorsWithAudioContributions}">
                <li>
                    <a href="<spring:url value='/content/contributor/${contributor.id}' />">
                        <div class="chip">
                            <c:choose>
                                <c:when test="${not empty contributor.imageUrl}">
                                    <img src="${contributor.imageUrl}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty contributor.firstName}">
                                    <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                </c:when>
                            </c:choose>
                        </div> (${audioContributionsCountMap[contributor.id]})
                    </a>
                </li>
            </c:forEach>
        </ol>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <b><fmt:message key="words" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributor" items="${contributorsWithWordContributions}">
                <li>
                    <a href="<spring:url value='/content/contributor/${contributor.id}' />">
                        <div class="chip">
                            <c:choose>
                                <c:when test="${not empty contributor.imageUrl}">
                                    <img src="${contributor.imageUrl}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty contributor.firstName}">
                                    <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                </c:when>
                            </c:choose>
                        </div> (${wordContributionsCountMap[contributor.id]})
                    </a>
                </li>
            </c:forEach>
        </ol>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <b><fmt:message key="numbers" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributor" items="${contributorsWithNumberContributions}">
                <li>
                    <a href="<spring:url value='/content/contributor/${contributor.id}' />">
                        <div class="chip">
                            <c:choose>
                                <c:when test="${not empty contributor.imageUrl}">
                                    <img src="${contributor.imageUrl}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty contributor.firstName}">
                                    <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                </c:when>
                            </c:choose>
                        </div> (${numberContributionsCountMap[contributor.id]})
                    </a>
                </li>
            </c:forEach>
        </ol>
    </div>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center">Token Holders 💎</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            Active contributors get rewarded with 
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:when>
                <c:otherwise>
                    <a href="https://etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:otherwise>
            </c:choose> 
            tokens.
        </p>
        <p>
            All token holders can participate in the community's <a href="<spring:url value='/contributions/aragon-dao' />">decision making</a>.
        </p>
        <div id="tokenHoldersContainer">
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.etherscan.io/token/tokenholderchart/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                        View all token holders <i class="material-icons">launch</i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="https://etherscan.io/token/tokenholderchart/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                        View all token holders <i class="material-icons">launch</i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</content:aside>
