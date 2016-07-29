<content:title>
    <fmt:message key="contributors" /> (${fn:length(contributors)})
</content:title>

<content:section cssId="contributorsPage">
    <div class="section row">
        <c:if test="${not empty contributors}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="name" /></th>
                    <th><fmt:message key="language" /></th>
                    <th><fmt:message key="teams" /></th>
                    <th><fmt:message key="registration.date" /></th>
                    <th><fmt:message key="personal.motivation" /></th>
                    <th><fmt:message key="contact.info" /></th>
                </thead>
                <tbody>
                    <c:forEach var="contributor" items="${contributors}">
                        <tr>
                            <td>
                                <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                                    <div class="chip">
                                        <img src="<spring:url value='${contributor.imageUrl}' />" alt="${contributor.firstName}" /> 
                                        <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                    </div>
                                </a>
                            </td>
                            <td>
                                <fmt:message key="language.${contributor.locale.language}" />
                            </td>
                            <td>
                                <c:forEach var="team" items="${contributor.teams}">
                                    <div class="chip">
                                        <fmt:message key="team.${team}" />
                                    </div>
                                </c:forEach>
                            </td>
                            <td>
                                <fmt:formatDate value="${contributor.registrationTime.time}" timeStyle="short" />
                            </td>
                            <td>
                                "<c:out value="${contributor.motivation}" />"
                            </td>
                            <td>
                                <c:out value="${contributor.email}" />
                                <div class="card-action">
                                    <c:if test="${not empty contributor.providerIdFacebook}">
                                        <a href="https://www.facebook.com/${contributor.providerIdFacebook}" target="_blank">Facebook</a>
                                    </c:if>
                                    <c:if test="${not empty contributor.providerIdGoogle}">
                                        <a href="https://plus.google.com/u/0/${contributor.providerIdGoogle}" target="_blank">Google+</a>
                                    </c:if>
                                    <c:if test="${not empty contributor.usernameGitHub}">
                                        <a href="https://github.com/${contributor.usernameGitHub}" target="_blank">GitHub</a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
