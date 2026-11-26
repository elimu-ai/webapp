<content:title>
    Applications (${fn:length(applications)})
</content:title>

<content:section cssId="applicationListPage">
    <div class="section row">
        <p>
            To add new content, click the button below. <span style="position: absolute; transform: rotate(-33deg);">👇🏽</span>
        </p>
        
        <c:if test="${not empty applications}">
            <table class="bordered highlight">
                <thead>
                    <th>Package name</th>
                    <th>Literacy skills</th>
                    <th>Numeracy skills</th>
                    <th>Status</th>
                    <th>Creator</th>
                </thead>
                <tbody>
                    <c:forEach var="application" items="${applications}">
                        <tr>
                            <td>
                                <a name="${application.id}"></a>
                                <a class="editLink" href="<spring:url value='/application/edit/${application.id}' />"><code>${application.packageName}</code></a>
                            </td>
                            <td>
                                ${application.literacySkills}
                            </td>
                            <td>
                                ${application.numeracySkills}
                            </td>
                            <td>
                                ${application.applicationStatus}
                            </td>
                            <td>
                                <c:set var="chipContributor" value="${application.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/application/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add application"><i class="material-icons">add</i></a>
    </div>
</content:section>
