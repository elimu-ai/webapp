<content:title>
    <fmt:message key="numbers" /> (${fn:length(numbers)})
</content:title>

<content:section cssId="numberListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="number" items="${numbers}">
            <div class="col s12 m6 l4">
                <a name="${number.id}"></a>
                <div class="number card-panel">
                    <c:choose>
                        <c:when test="${number.locale.language == 'ar'}">
                            <h4><c:out value="${number.symbol}" /> (${number.value})</h4>
                        </c:when>
                        <c:otherwise>
                            <h4>${number.value}</h4>
                            <p><fmt:message key="number.word" />: "<a href="<spring:url value='/content/word/edit/${number.word.id}' />">${number.word.text}</a>"</p>
                            <p>/<c:out value="${number.word.phonetics}" />/</p>
                            <p><fmt:message key="revision" />: #${number.revisionNumber}</p>
                        </c:otherwise>
                    </c:choose>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/number/edit/${number.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
                
                <%-- TODO: word --%>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a>
    </div>
</content:section>
