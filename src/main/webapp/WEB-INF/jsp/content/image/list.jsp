<content:title>
    <fmt:message key="images" />
</content:title>

<content:section cssId="numberListPage">
    <div class="section row">
        <c:if test="${empty images}">
            <p>
                <fmt:message key="to.add.new.content.click.the.button.below" />
            </p>
        </c:if>
        
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
                    
                    <img src="<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageType)}' />" alt="${image.title}" />
                    
                    <div class="card-content">
                        <h4>${image.title}</h4>
                        <div class="chip"><fmt:message key="language.${image.language.designator}" /></div>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a href="<spring:url value='/content/image/edit/${image.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/image/create' />" class="btn-floating btn-large orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a>
    </div>
</content:section>
