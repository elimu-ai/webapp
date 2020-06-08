<content:title>
    <fmt:message key="words.pending" /> (${fn:length(wordFrequencyMap)})
</content:title>

<content:section cssId="wordsPendingPage">
    <div class="section row">
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="usage.count" /></th>
                <th><fmt:message key="text" /></th>
                <th><fmt:message key="add.word" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordFrequencyMapItem" items="${wordFrequencyMap}">
                    <c:set var="wordTextLowerCase" value="${wordFrequencyMapItem.key}" />
                    <c:set var="wordUsageCount" value="${wordFrequencyMapItem.value}" />
                    <tr>
                        <td>
                            ${wordUsageCount}<br />
                            <div class="progress">
                                <div class="determinate" style="width: ${wordUsageCount * 100 / maxUsageCount}%"></div>
                            </div>
                        </td>
                        <td style="font-size: 2em;">
                            "<c:out value="${wordTextLowerCase}" />"
                        </td>
                        <td>
                            <a href="<spring:url value='/content/word/create?autoFillText=${wordTextLowerCase}' />" target="_blank"><fmt:message key="add.word" /> <i class="material-icons">launch</i></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>

<content:aside>
    <div class="card-panel amber lighten-5">
        <p>
            These are words extracted from Storybooks, but that have not yet been manually added to the Word database.
        </p>
        <p>
            The list serves as a tool for content creators to know which words to add first.
        </p>
    </div>
</content:aside>
