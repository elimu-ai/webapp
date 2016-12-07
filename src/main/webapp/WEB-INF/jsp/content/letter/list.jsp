<content:title>
    <fmt:message key="letters" /> (${fn:length(letters)})
</content:title>

<content:section cssId="letterListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="letter" items="${letters}">
            <div class="col s12 m6 l4">
                <a name="${letter.id}"></a>
                <div class="letter card-panel">
                    <h4><c:out value="${letter.text}" /></h4>
                    <p><fmt:message key="revision" />: ${letter.revisionNumber}</p>
                    <p><fmt:message key="frequency" />: ${letter.usageCount}</p>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/letter/edit/${letter.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large purple tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a>
    </div>
</content:section>
