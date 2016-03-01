<content:title>
    <fmt:message key="contributors" />
</content:title>

<content:section cssId="contributorsPage">
    <c:forEach var="contributor" items="${contributors}">
        <div class="col s12 m6 l4">
            <div class="card small">
              <div class="card-image">
                <img src="${contributor.imageUrl}" />
                <span class="card-title"><c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /></span>
              </div>
              <div class="card-content">
                  <p>Bio: ...</p>
              </div>
              <div class="card-action">
                  <c:forEach var="team" items="${contributor.teams}">
                      <div class="chip">
                          <fmt:message key="team.${team}" />
                      </div>
                  </c:forEach>
              </div>
            </div>
        </div>
    </c:forEach>
</content:section>
