<content:title>
    <fmt:message key="letters" /> (${fn:length(letters)})
</content:title>

<content:section cssId="letterListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
    
        <c:if test="${not empty letters}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="letter" /></th>
                    <th><fmt:message key="phonetics" /></th>
                    <th>Braille</th>
                    <th><fmt:message key="audio" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="letter" items="${letters}">
                        <tr class="letter">
                            <td>
                                ${letter.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${letter.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${letter.id}"></a>
                                ${letter.text}
                            </td>
                            <td>
                                /<c:forEach var="allophone" items="${letter.allophones}">
                                    ${allophone.valueIpa} 
                                </c:forEach>/
                            </td>
                            <td>
                                <span style="border: 1px solid rgba(0,0,0, 0.20); padding: 0.5em;">
                                    ${letter.braille}
                                </span>
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/static/audio/${locale.language}/sampa_${letter.text}.wav' />" />
                                </audio>
                            </td>
                            <td>
                                <p>#${letter.revisionNumber}</p>
                            </td>
                            <td><a class="editLink" href="<spring:url value='/content/letter/edit/${letter.id}' />"><span class="material-icons">edit</span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large teal tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a>
    </div>
</content:section>
