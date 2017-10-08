<content:title>
    <fmt:message key="applications" /> (${fn:length(appGroup.applications)})
</content:title>

<content:section cssId="applicationListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="package.name" /></th>
                <th><fmt:message key="status" /></th>
            </thead>
            <tbody>
                <c:forEach var="application" items="${appGroup.applications}">
                    <tr class="application">
                        <td>
                            <a href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/application/${application.id}/edit' />">
                                ${application.packageName}
                            </a>
                        </td>
                        <td>
                            ${application.applicationStatus}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
                
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/project/${project.id}/app-category/${appCategory.id}/app-group/${appGroup.id}/app/create' />" class="btn-floating btn-large deep-purple lighten-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.application" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
