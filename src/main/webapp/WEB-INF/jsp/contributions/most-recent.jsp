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
                    <a name="${storyBook.id}"></a>
                    <div class="storyBook card">
                        <c:if test="${not empty storyBook.coverImage}">
                            <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                                <div class="card-image" style="height: 10em; background-image: url(<spring:url value='/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}' />);">
                                    <span class="card-title"><c:out value="${storyBook.title}" /></span>
                                </div>
                            </a>
                        </c:if>
                        <div class="card-content">
                            <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                            <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                            <p><fmt:message key="revision" />: #${storyBookContributionEvent.revisionNumber}</p>

                            <div class="divider" style="margin: 1em 0;"></div>

                            <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
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
                <th><fmt:message key="allophones" /></th>
                <th><fmt:message key="word.type" /></th>
                <th><fmt:message key="revision" /></th>
                <th><fmt:message key="edit" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
                    <c:set var="word" value="${wordContributionEvent.word}" />
                    <tr>
                        <td>
                            <a name="${word.id}"></a>
                            "${word.text}"
                        </td>
                        <td>
                            /<c:forEach var="allophone" items="${word.allophones}"><a href="<spring:url value='/content/allophone/edit/${allophone.id}' />">${allophone.valueIpa}</a></c:forEach>/
                        </td>
                        <td>
                            ${word.wordType}<br />
                            <c:out value=" ${emojisByWordId[word.id]}" />
                        </td>
                        <td>
                            <p>#${wordContributionEvent.revisionNumber}</p>
                        </td>
                        <td><a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />"><span class="material-icons">edit</span></a></td>
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
