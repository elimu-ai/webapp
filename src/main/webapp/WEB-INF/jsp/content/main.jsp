<content:title>
    <fmt:message key="educational.content" />
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <div class="col s12">
            <h5><fmt:message key="text" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_fields</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/letter/list' />"><fmt:message key="view.list" /> (${letterCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">music_note</i> <fmt:message key="sounds" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/allophone/list' />"><fmt:message key="view.list" /> (${allophoneCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">emoji_symbols</i> <fmt:message key="letter.sounds" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/letter-sound-correspondence/list' />"><fmt:message key="view.list" /> (${letterSoundCorrespondenceCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">queue_music</i> <fmt:message key="syllables" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/syllable/list' />"><fmt:message key="view.list" /> (${syllableCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">looks_one</i> <fmt:message key="numbers" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${numberCount/333 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/number/list' />"><fmt:message key="view.list" /> (${numberCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${wordCount/666 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/word/list' />"><fmt:message key="view.list" /> (${wordCount})</a>
                    <a href="<spring:url value='/content/word/peer-reviews' />"><fmt:message key="peer.review" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">emoji_emotions</i> <fmt:message key="emojis" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/emoji/list' />"><fmt:message key="view.list" /> (${emojiCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5><fmt:message key="multimedia" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">image</i> <fmt:message key="images" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/image/list' />"><fmt:message key="view.list" /> (${imageCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">audiotrack</i> <fmt:message key="audios" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${audioCount/wordCount * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/multimedia/audio/list' />"><fmt:message key="view.list" /> (${audioCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> <fmt:message key="storybooks" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${storyBookCount/33 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/storybook/list' />"><fmt:message key="view.list" /> (${storyBookCount})</a>
                    <a href="<spring:url value='/content/storybook/peer-reviews' />"><fmt:message key="peer.review" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">movie</i> <fmt:message key="videos" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${videoCount/12 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/multimedia/video/list' />"><fmt:message key="view.list" /> (${videoCount})</a>
                </div>
            </div>
        </div>
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
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <a href="<spring:url value='/contributions/most-recent' />">Most recent contributions</a>
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
