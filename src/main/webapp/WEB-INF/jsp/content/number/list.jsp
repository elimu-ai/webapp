<content:title>
    <fmt:message key="numbers" />
</content:title>

<content:section cssId="numberListPage">
    <div class="section row">
        <c:if test="${empty numbers}">
            <p>
                <fmt:message key="to.add.new.content.click.the.button.below" />
            </p>
        </c:if>
        
        <c:forEach var="number" items="${numbers}">
            <div class="col s12 m6 l4">
                <div class="card-panel">
                    <c:if test="${not empty number.contributor}">
                        <div class="row valign-wrapper">
                            <div class="col s2">
                                <img src="${number.contributor.imageUrl}" alt="" class="circle responsive-img">
                            </div>
                            <div class="col s10">
                                <span class="black-text">
                                    <c:out value="${number.contributor.firstName}" />&nbsp;<c:out value="${number.contributor.lastName}" />
                                </span>
                            </div>
                        </div>
                    
                        <div class="divider"></div>
                    </c:if>
                    <c:choose>
                        <c:when test="${number.language == 'ARABIC'}">
                            <h4><c:out value="${number.symbol}" /> (${number.value})</h4>
                        </c:when>
                        <c:otherwise>
                            <h4>${number.value}</h4>
                        </c:otherwise>
                    </c:choose>
                    <div class="chip"><fmt:message key="language.${number.language.designator}" /></div>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a href="<spring:url value='/content/number/edit/${number.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a>
    </div>
</content:section>
