<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainPage">
    <div class="row">
        <div class="col s12">
          <ul class="tabs">
            <li class="tab col s3"><a <c:if test="${language == 'ARABIC'}">class="active"</c:if> href="<spring:url value='/content' />?language=ARABIC"><fmt:message key="language.ar" /></a></li>
            <li class="tab col s3"><a <c:if test="${language == 'ENGLISH'}">class="active"</c:if> href="<spring:url value='/content' />?language=ENGLISH"><fmt:message key="language.en" /></a></li>
            <li class="tab col s3"><a <c:if test="${language == 'SPANISH'}">class="active"</c:if> href="<spring:url value='/content' />?language=SPANISH"><fmt:message key="language.es" /></a></li>
            <li class="tab col s3"><a <c:if test="${language == 'SWAHILI'}">class="active"</c:if> href="<spring:url value='/content' />?language=SWAHILI"><fmt:message key="language.sw" /></a></li>
          </ul>
        </div>
    </div>
          
    <h4><fmt:message key="latest.uploads" /></h4>
    
    <div class="section row">
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
                    <p>    
                        <fmt:message key="number" />
                    </p>
                    <div class="divider"></div>
                    <c:choose>
                        <c:when test="${number.language == 'ARABIC'}">
                            <h4><c:out value="${number.symbol}" /> (${number.value})</h4>
                        </c:when>
                        <c:otherwise>
                            <h4>${number.value}</h4>
                        </c:otherwise>
                    </c:choose>
                    <div class="chip">${number.language}</div>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a href="<spring:url value='/content/number/edit/${number.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                </div>
            </div>
        </c:forEach>
        
        <c:forEach var="image" items="${images}">
            <div class="col s12 m6 l4">
                <div class="card">
                    <div class="card-content">
                        <div class="row valign-wrapper">
                            <div class="col s2">
                                <img src="${image.contributor.imageUrl}" alt="" class="circle responsive-img">
                            </div>
                            <div class="col s10">
                                <span class="black-text">
                                    <c:out value="${image.contributor.firstName}" />&nbsp;<c:out value="${image.contributor.lastName}" />
                                </span>
                            </div>
                        </div>
                    </div>
                    
                    <img src="<spring:url value='/image/${image.title}.${fn:toLowerCase(image.imageType)}' />" alt="${image.title}" />
                    
                    <div class="card-content">
                        <h4>${image.title}</h4>
                        <div class="chip">${image.language}</div>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a href="<spring:url value='/content/image/edit/${image.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                    </div>
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
