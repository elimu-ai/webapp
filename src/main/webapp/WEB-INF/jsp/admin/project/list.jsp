<content:title>
    <fmt:message key="projects" /> (${fn:length(projects)})
</content:title>

<content:section cssId="projectListPage">
    <div class="section row">
        <c:if test="${not empty projects}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="name" /></th>
                    <th><fmt:message key="managers" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="project" items="${projects}">
                        <tr class="project">
                            <td>
                                ${project.name}
                            </td>
                            <td>
                                
                            </td>
                            <td>
                                <a class="editLink" href="<spring:url value='/admin/project/edit/${project.id}' />"><span class="material-icons">edit</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/admin/project/create' />" class="btn-floating btn-large deep-purple lighten-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.project" />"><i class="material-icons">playlist_add</i></a>
    </div>
</content:section>
