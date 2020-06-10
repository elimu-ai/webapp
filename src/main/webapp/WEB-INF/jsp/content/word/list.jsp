<content:title>
    <fmt:message key="words" /> (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/word/list/words.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:if test="${not empty words}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="text" /></th>
                    <th><fmt:message key="allophones" /></th>
                    <th><fmt:message key="spelling.consistency" /></th>
                    <th><fmt:message key="word.type" /></th>
                    <th><fmt:message key="root.word" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="word" items="${words}">
                        <tr class="allophone">
                            <td>
                                ${word.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${word.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${word.id}"></a>
                                "${word.text}"
                            </td>
                            <td style="font-size: 2em;">
                                /<c:forEach var="allophone" items="${word.allophones}"><a href="<spring:url value='/content/allophone/edit/${allophone.id}' />">${allophone.valueIpa}</a></c:forEach>/
                            </td>
                            <c:choose>
                                <c:when test="${word.spellingConsistency == 'PERFECT'}">
                                    <c:set var="spellingConsistencyColor" value="green lighten-1" />
                                </c:when>
                                <c:when test="${word.spellingConsistency == 'HIGHLY_PHONEMIC'}">
                                    <c:set var="spellingConsistencyColor" value="green lighten-3" />
                                </c:when>
                                <c:when test="${word.spellingConsistency == 'PHONEMIC'}">
                                    <c:set var="spellingConsistencyColor" value="yellow lighten-3" />
                                </c:when>
                                <c:when test="${word.spellingConsistency == 'NON_PHONEMIC'}">
                                    <c:set var="spellingConsistencyColor" value="orange lighten-3" />
                                </c:when>
                                <c:when test="${word.spellingConsistency == 'HIGHLY_NON_PHONEMIC'}">
                                    <c:set var="spellingConsistencyColor" value="red lighten-3" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="spellingConsistencyColor" value="" />
                                </c:otherwise>
                            </c:choose>
                            <td class="${spellingConsistencyColor}">
                                <fmt:message key="spelling.consistency.${word.spellingConsistency}" />
                            </td>
                            <td>
                                ${word.wordType}<br />
                                <c:out value=" ${emojisByWordId[word.id]}" />
                            </td>
                            <td>
                                <c:if test="${not empty word.rootWord}">
                                    <a href="<spring:url value='/content/word/edit/${word.rootWord.id}' />">
                                        ${word.rootWord.text} 
                                    </a> (${word.rootWord.wordType})
                                </c:if>
                            </td>
                            <td>
                                <p>#${word.revisionNumber}</p>
                            </td>
                            <td><a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />"><span class="material-icons">edit</span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
