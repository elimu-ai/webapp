<content:title>
    <fmt:message key="select.teams" />
</content:title>

<content:section cssId="editTeamsPage">
    <div class="col s12 m10 l8 offset-m1 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <blockquote>
                <fmt:message key="how.would.you.like.to.contribute" /><br />
                <fmt:message key="select.other.if.you.are.not.sure" />
            </blockquote>

            <div class="divider"></div>

            <form method="POST">
                <c:if test="${not empty errorCode}">
                    <div id="errorPanel" class="card-panel red lighten-3">
                        <fmt:message key="${errorCode}" />
                    </div>
                </c:if>
                
                <p>
                    <input type="checkbox" name="teams" id="ANALYTICS" value="ANALYTICS" <c:if test="${fn:contains(contributor.teams, 'ANALYTICS')}">checked="checked"</c:if> />
                    <label for="ANALYTICS"><b><fmt:message key="team.ANALYTICS" /></b> <br />(<fmt:message key="team.ANALYTICS.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="CONTENT_CREATION" value="CONTENT_CREATION" <c:if test="${fn:contains(contributor.teams, 'CONTENT_CREATION')}">checked="checked"</c:if> />
                    <label for="CONTENT_CREATION"><b><fmt:message key="team.CONTENT_CREATION" /></b> <br />(<fmt:message key="team.CONTENT_CREATION.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="DEVELOPMENT" value="DEVELOPMENT" <c:if test="${fn:contains(contributor.teams, 'DEVELOPMENT')}">checked="checked"</c:if> />
                    <label for="DEVELOPMENT"><b><fmt:message key="team.DEVELOPMENT" /></b> <br />(<fmt:message key="team.DEVELOPMENT.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="MARKETING" value="MARKETING" <c:if test="${fn:contains(contributor.teams, 'MARKETING')}">checked="checked"</c:if> />
                    <label for="MARKETING"><b><fmt:message key="team.MARKETING" /></b> <br />(<fmt:message key="team.MARKETING.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="TESTING" value="TESTING" <c:if test="${fn:contains(contributor.teams, 'TESTING')}">checked="checked"</c:if> />
                    <label for="TESTING"><b><fmt:message key="team.TESTING" /></b> <br />(<fmt:message key="team.TESTING.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="TRANSLATION" value="TRANSLATION" <c:if test="${fn:contains(contributor.teams, 'TRANSLATION')}">checked="checked"</c:if> />
                    <label for="TRANSLATION"><b><fmt:message key="team.TRANSLATION" /></b> <br />(<fmt:message key="team.TRANSLATION.description" />)</label>
                </p>
                <p>
                    <input type="checkbox" name="teams" id="OTHER" value="OTHER" <c:if test="${fn:contains(contributor.teams, 'OTHER')}">checked="checked"</c:if> />
                    <label for="OTHER"><b><fmt:message key="team.OTHER" /></b></label>
                </p>

                <button id="submitButton" class="btn waves-effect waves-light" type="submit" name="action">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
