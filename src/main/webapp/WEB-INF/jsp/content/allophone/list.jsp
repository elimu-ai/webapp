<content:title>
    <fmt:message key="allophones" /> (${fn:length(allophones)})
</content:title>

<content:section cssId="allophoneListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
    
        <c:if test="${not empty allophones}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="ipa.value" /></th>
                    <th><fmt:message key="sampa.value" /></th>
                    <th><fmt:message key="sound.type" /></th>
                    <th><fmt:message key="audio" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="allophone" items="${allophones}">
                        <tr class="allophone">
                            <td>
                                ${allophone.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${allophone.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${allophone.id}"></a>
                                /${allophone.valueIpa}/
                            </td>
                            <td style="font-size: 2em;">
                                ${allophone.valueSampa}
                            </td>
                            <td>
                                ${allophone.soundType}
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/static/audio/${locale.language}/sampa_${allophone.valueSampa}.wav' />" />
                                </audio>
                            </td>
                            <td>
                                <p>#${allophone.revisionNumber}</p>
                            </td>
                            <td><a class="editLink" href="<spring:url value='/content/allophone/edit/${allophone.id}' />"><span class="material-icons">edit</span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/allophone/create' />" class="btn-floating btn-large deep-purple lighten-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.allophone" />"><i class="material-icons">record_voice_over</i></a>
    </div>
</content:section>
