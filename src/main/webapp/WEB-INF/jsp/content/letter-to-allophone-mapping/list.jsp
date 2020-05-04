<content:title>
    <fmt:message key="letter.to.allophone.mappings" /> (${fn:length(letterToAllophoneMappings)})
</content:title>

<content:section cssId="letterToAllophoneMappingListPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/letter-to-allophone-mapping/list/letter-to-allophone-mappings.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
    
        <c:if test="${not empty letterToAllophoneMappings}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="usage.count" /></th>
                    <th><fmt:message key="letters" /></th>
                    <th><fmt:message key="allophones" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="letterToAllophoneMapping" items="${letterToAllophoneMappings}">
                        <tr class="letterToAllophoneMapping">
                            <td>
                                <a name="${letterToAllophoneMapping.id}"></a>
                                
                                ${letterToAllophoneMapping.usageCount}
                            </td>
                            <td style="font-size: 2em;">
                                "<c:forEach var="letter" items="${letterToAllophoneMapping.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />"> ${letter.text} </a></c:forEach>"
                            </td>
                            <td style="font-size: 2em;">
                                /<c:forEach var="allophone" items="${letterToAllophoneMapping.allophones}"><a href="<spring:url value='/content/allophone/edit/${allophone.id}' />">${allophone.valueIpa}</a></c:forEach>/
                            </td>
                            <td>
                                <a class="editLink" href="<spring:url value='/content/letter-to-allophone-mapping/edit/${letterToAllophoneMapping.id}' />"><span class="material-icons">edit</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/letter-to-allophone-mapping/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter.to.allophone.mapping" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
