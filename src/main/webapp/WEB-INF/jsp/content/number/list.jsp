<content:title>
    <fmt:message key="numbers" />
</content:title>

<content:section cssId="numberListPage">
    <div class="row">
        <div class="col s12">
          <ul class="tabs">
            <li class="tab col s3 disabled"><a href="#ARABIC"><fmt:message key="language.ar" /></a></li>
            <li class="tab col s3"><a class="active" href="#ENGLISH"><fmt:message key="language.en" /></a></li>
            <li class="tab col s3 disabled"><a href="#SPANISH"><fmt:message key="language.es" /></a></li>
            <li class="tab col s3 disabled"><a href="#SWAHILI"><fmt:message key="language.sw" /></a></li>
          </ul>
        </div>
    </div>
    
    <div class="section row">
        <c:forEach var="number" items="${numbers}">
            <div class="col s12 m6 l4">
                <div class="card-panel">
                    <h4>${number.value}</h4>
                    <div class="chip">${number.language}</div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a>
    </div>
</content:section>
