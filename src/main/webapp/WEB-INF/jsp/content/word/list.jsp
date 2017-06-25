<content:title>
    <fmt:message key="words" /> (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:if test="${not empty words}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="text" /></th>
                    <th><fmt:message key="phonetics" /></th>
                    <th><fmt:message key="spelling.consistency" /></th>
                    <th><fmt:message key="word.type" /></th>
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
                            <td>
                                "${word.text}"
                            </td>
                            <td>
                                <a name="${word.id}"></a>
                                /${word.phonetics}/
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
                                ${word.wordType}
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
        <a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a>
    </div>
</content:section>
