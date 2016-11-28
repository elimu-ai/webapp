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
                    <th><fmt:message key="ipa.value" /></th>
                    <th><fmt:message key="sampa.value" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="allophone" items="${allophones}">
                        <tr class="allophone">
                            <td style="font-size: 2em;">
                                /${allophone.valueIpa}/
                            </td>
                            <td style="font-size: 2em;">
                                ${allophone.valueSampa}
                            </td>
                            <td>
                                <p>${allophone.revisionNumber}</p>
                            </td>
                            <td><a class="editLink" href="<spring:url value='/content/allophone/edit/${allophone.id}' />"><span class="material-icons">edit</span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/allophone/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.allophone" />"><i class="material-icons">record_voice_over</i></a>
    </div>
</content:section>
