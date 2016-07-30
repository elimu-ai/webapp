<content:title>
    <fmt:message key="words" /> (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="word" items="${words}">
            <div class="col s12 m6 l4">
                <div class="word card-panel">
                    <h4><c:out value="${word.text}" /></h4>
                    <p>/<c:out value="${word.phonetics}" />/</p>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a>
    </div>
</content:section>
