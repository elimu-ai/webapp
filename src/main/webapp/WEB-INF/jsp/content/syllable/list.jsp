<content:title>
    <fmt:message key="syllables" /> (${fn:length(syllables)})
</content:title>

<content:section cssId="syllableListPage">
    <div class="section row">
        <%--<p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>--%>
        
        <c:forEach var="syllable" items="${syllables}">
            <div class="col s12 m6 l4">
                <a name="${syllable.id}"></a>
                <div class="syllable card-panel">
                    <h4><c:out value="${syllable.text}" /></h4>
                    
                    <p><fmt:message key="frequency" />: ${syllable.usageCount}</p>
                    <p><fmt:message key="revision" />: #${syllable.revisionNumber}</p>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/syllable/edit/${syllable.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <%--<div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/syllable/create' />" class="btn-floating btn-large green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.syllable" />"><i class="material-icons">queue_music</i></a>
    </div>--%>
</content:section>
