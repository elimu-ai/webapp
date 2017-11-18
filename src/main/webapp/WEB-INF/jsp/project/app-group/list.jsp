<content:title>
    <fmt:message key="app.groups" /> (${fn:length(appCategory.appGroups)})
</content:title>

<content:section cssId="appGroupListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="appGroup" items="${appCategory.appGroups}">
            <div class="col s12 card-panel appGroup" data-id="${appGroup.id}" style="padding: 1em;">
                <a href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/app/list' />">
                    <fmt:message key="group" /> #${appGroup.id}
                </a>
                <a class="editLink right" href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/edit' />"><span class="material-icons" style="vertical-align: bottom;">edit</span> <fmt:message key="edit" /></a>
                <br />
                <br />
                
                <%-- List Applications --%>
                <c:forEach var="application" items="${appGroup.applications}">
                    <a href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/application/${application.id}/edit' />" title="${application.packageName}">
                        ${application.packageName}
                    </a>&nbsp;
                </c:forEach>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/create' />" class="btn-floating btn-large deep-purple lighten-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.app.group" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
