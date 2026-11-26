<content:title>
    Skill categorization
</content:title>

<content:section cssId="skillCategorizationPage">
    <div class="section row">
        <%-- Literacy skills (EGRA) on the left side --%>
        <div class="col s12 m6">
            <h5>Literacy Skills (EGRA)</h5>

            <c:forEach var="literacySkillApplications" items="${literacySkillApplicationsMap}">
                <h6 class="skillName">${fn:replace(fn:toLowerCase(literacySkillApplications.key), '_', ' ')} (${fn:length(literacySkillApplications.value)})</h6>
                <c:choose>
                    <c:when test="${empty literacySkillApplications.value}">
                        <p class="grey-text">No applications</p>
                    </c:when>
                    <c:otherwise>
                        <div class="collection">
                            <c:forEach var="application" items="${literacySkillApplications.value}">
                                <a class="collection-item" href="<spring:url value='/application/edit/${application.id}' />"><code>${application.packageName}</code></a>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <%-- Numeracy skills (EGMA) on the right side --%>
        <div class="col s12 m6">
            <h5>Numeracy Skills (EGMA)</h5>

            <c:forEach var="numeracySkillApplications" items="${numeracySkillApplicationsMap}">
                <h6 class="skillName">${fn:replace(fn:toLowerCase(numeracySkillApplications.key), '_', ' ')} (${fn:length(numeracySkillApplications.value)})</h6>
                <c:choose>
                    <c:when test="${empty numeracySkillApplications.value}">
                        <p class="grey-text">No applications</p>
                    </c:when>
                    <c:otherwise>
                        <div class="collection">
                            <c:forEach var="application" items="${numeracySkillApplications.value}">
                                <a class="collection-item" href="<spring:url value='/application/edit/${application.id}' />"><code>${application.packageName}</code></a>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</content:section>
