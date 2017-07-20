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
                
                <c:forEach var="team" items="${teams}">
                    <p>
                        <input type="checkbox" name="teams" id="${team}" value="${team}" <c:if test="${fn:contains(contributor.teams, team)}">checked="checked"</c:if> />
                        <label for="${team}"><b><fmt:message key="team.${team}" /></b><c:if test="${team != 'OTHER'}"> <br />(<fmt:message key="team.${team}.description" />)</label></c:if>
                    </p>
                </c:forEach>

                <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
