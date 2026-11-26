<content:title>
    Applications
</content:title>

<content:section cssId="applicationsPage">
    <div class="section row">
        <div class="col s12 m6">
            <h5>Literacy Apps/Games 📚🕹</h5>

            <c:forEach var="literacySkillApplications" items="${literacySkillApplicationsMap}">
                <h6 style="margin-top: 1.5rem;" class="skillName">
                    <a href="https://github.com/elimu-ai/wiki/tree/main/literacy-skills" target="_blank">
                        ${literacySkillApplications.key}
                    </a> (${fn:length(literacySkillApplications.value)})
                </h6>
                <c:choose>
                    <c:when test="${empty literacySkillApplications.value}">
                        <p class="grey-text">No applications</p>
                    </c:when>
                    <c:otherwise>
                        <div class="collection">
                            <c:forEach var="application" items="${literacySkillApplications.value}">
                                <a class="collection-item green lighten-4" target="_blank" href="<spring:url value='https://github.com/elimu-ai/${application.repoName}' />"><code>${application.packageName}</code></a>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <div class="col s12 m6">
            <h5>Numeracy Apps/Games 🎲🕹</h5>

            <c:forEach var="numeracySkillApplications" items="${numeracySkillApplicationsMap}">
                <h6 style="margin-top: 1.5rem;" class="skillName">
                    <a href="https://github.com/elimu-ai/wiki/tree/main/numeracy-skills" target="_blank">
                        ${numeracySkillApplications.key}
                    </a> (${fn:length(numeracySkillApplications.value)})
                </h6>
                <c:choose>
                    <c:when test="${empty numeracySkillApplications.value}">
                        <p class="grey-text">No applications</p>
                    </c:when>
                    <c:otherwise>
                        <div class="collection">
                            <c:forEach var="application" items="${numeracySkillApplications.value}">
                                <a class="collection-item blue lighten-4" target="_blank" href="<spring:url value='https://github.com/elimu-ai/${application.repoName}' />"><code>${application.packageName}</code></a>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</content:section>
