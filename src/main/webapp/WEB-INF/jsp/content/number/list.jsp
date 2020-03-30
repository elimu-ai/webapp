<content:title>
    <fmt:message key="numbers" /> (${fn:length(numbers)})
</content:title>

<content:section cssId="numberListPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/number/list/numbers.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="number" items="${numbers}">
            <div class="col s12 m6 l4">
                <a name="${number.id}"></a>
                <div class="number card-panel">
                    <h4>${number.value}<c:if test="${not empty number.symbol}"> (${number.symbol})</c:if></h4>
                    <p><fmt:message key="number.words" />: 
                        <c:forEach var="word" items="${number.words}">
                            <a href="<spring:url value='/content/word/edit/${word.id}' />">${word.text}</a>
                        </c:forEach>
                    </p>
                    <p><fmt:message key="revision" />: #${number.revisionNumber}</p>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/number/edit/${number.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
                
                <%-- TODO: word --%>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
