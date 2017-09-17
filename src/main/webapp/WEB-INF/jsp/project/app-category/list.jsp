<content:title>
    <fmt:message key="app.categories" /> (${fn:length(project.appCategories)})
</content:title>

<content:section cssId="appCategoryListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="appCategory" items="${project.appCategories}">
            <div class="col s12 card-panel" style="padding: 1em;">
                <c:out value="${appCategory.name}" />
                <a class="editLink right" href="<spring:url value='/project/${project.id}/app-category/edit/${appCategory.id}' />"><span class="material-icons" style="vertical-align: bottom;">edit</span> <fmt:message key="edit" /></a>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/project/${project.id}/app-category/create' />" class="btn-floating btn-large deep-purple lighten-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.app.category" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
