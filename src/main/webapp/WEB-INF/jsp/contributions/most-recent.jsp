<content:title>
    <fmt:message key="contributions" />
</content:title>

<content:section cssId="contributionsPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <h5>Most Recent Storybook Contributions</h5>
        storyBookContributionEvents: ${storyBookContributionEvents}
        
        <div class="divider" style="margin: 1.5em 0;"></div>
        
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
                            <p>#${word.revisionNumber}</p>
                        </td>
                        <td><a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />"><span class="material-icons">edit</span></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="top.contributors" /></h5>
    <div class="card-panel deep-purple lighten-5">
        TODO...
    </div>
</content:aside>
