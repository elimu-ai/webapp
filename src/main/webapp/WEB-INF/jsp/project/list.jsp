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
                </thead>
                <tbody>
                    <c:forEach var="project" items="${projects}">
                        <tr class="project">
                            <td>
                                <a href="<spring:url value='/project/${project.id}' />">
                                    ${project.name}
                                </a>
                            </td>
                            <td>
                                <c:forEach var="manager" items="${project.managers}">
                                    <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                                        <div class="chip">
                                            <img src="<spring:url value='${manager.imageUrl}' />" alt="${manager.firstName}" /> 
                                            <c:out value="${manager.firstName}" />&nbsp;<c:out value="${manager.lastName}" />
                                        </div>
                                    </a>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
