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
                    <span class="card-title"><i class="material-icons">record_voice_over</i> <fmt:message key="allophones" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/allophone/list' />"><fmt:message key="view.list" /> (${allophoneCount})</a>
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
                        <div class="determinate" style="width: ${numberCount/1001 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/number/list' />"><fmt:message key="view.list" /> (${numberCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_format</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/letter/list' />"><fmt:message key="view.list" /> (${letterCount})</a>
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
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${wordCount/2000 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/word/list' />"><fmt:message key="view.list" /> (${wordCount})</a>
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
                        <div class="determinate" style="width: ${storyBookCount/104 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/storybook/list' />"><fmt:message key="view.list" /> (${storyBookCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">movie</i> <fmt:message key="videos" /></span>
                </div>
                <div class="card-action">
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
        <ol>
            <c:forEach var="contributorWithStoryBookContributions" items="${contributorsWithStoryBookContributions}">
                <li>
                    <div class="chip">
                        <img src="<spring:url value='${contributorWithStoryBookContributions.imageUrl}' />" alt="${contributorWithStoryBookContributions.firstName}" /> 
                        <c:out value="${contributorWithStoryBookContributions.firstName}" />&nbsp;<c:out value="${contributorWithStoryBookContributions.lastName}" />
                    </div> (${storyBookContributionsCountMap[contributorWithStoryBookContributions.id]})
                </li>
            </c:forEach>
        </ol>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <b><fmt:message key="words" /></b><br />
        <ol>
            <c:forEach var="contributorWithWordContributions" items="${contributorsWithWordContributions}">
                <li>
                    <div class="chip">
                        <img src="<spring:url value='${contributorWithWordContributions.imageUrl}' />" alt="${contributorWithWordContributions.firstName}" /> 
                        <c:out value="${contributorWithWordContributions.firstName}" />&nbsp;<c:out value="${contributorWithWordContributions.lastName}" />
                    </div> (${wordContributionsCountMap[contributorWithWordContributions.id]})
                </li>
            </c:forEach>
        </ol>
    </div>
</content:aside>
