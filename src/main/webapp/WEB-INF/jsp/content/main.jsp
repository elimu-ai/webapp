<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainPage">
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
                    <fmt:message key="number" />
                    <div class="divider"></div>
                    <h4>${number.value}</h4>
                    <div class="chip">${number.language}</div>
                </div>
            </div>
        </c:forEach>
        
        <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
            <a class="btn-floating btn-large red" title="Add content">
                <i class="large material-icons">add</i>
            </a>
            <ul>
                <li><a href="<spring:url value='/content/number/create' />" class="btn-floating red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a></li>
                <li><a href="<spring:url value='/content/letter/create' />" class="btn-floating yellow darken-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a></li>
                <li><a href="<spring:url value='/content/word/create' />" class="btn-floating green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a></li>
                <li><a href="<spring:url value='/content/audio/create' />" class="btn-floating blue tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a></li>
                <li><a href="<spring:url value='/content/image/create' />" class="btn-floating orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a></li>
            </ul>
        </div>
    </div>
</content:section>
