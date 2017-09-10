<content:title>
    <fmt:message key="contributors" /> (${fn:length(contributors)})
</content:title>

<content:section cssId="contributorsPage">
    <c:forEach var="contributor" items="${contributors}">
        <div class="col s12 m6 l4">
            <div class="card" style="overflow: hidden;">
                <div class="card-image waves-effect waves-block waves-light">
                    <img class="activator" src="${contributor.imageUrl}" />
                </div>
                <div class="card-content">
                    <span class="card-title activator grey-text text-darken-4"><c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /><i class="material-icons right">more_vert</i></span>
                    <c:if test="${not empty contributor.slackId}">
                        <p><a href="http://slack.elimu.ai" target="_blank"><i class="material-icons left">chat_bubble_outline</i>Chat with <c:out value="${contributor.firstName}" /> in Slack</a></p>
                    </c:if>
                </div>

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
                    
                <div class="card-reveal" style="display: none; transform: translateY(0px);">
                    <span class="card-title grey-text text-darken-4"><c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /><i class="material-icons right">close</i></span>
                    <p><b><fmt:message key="language" /></b></p>
                    <p><fmt:message key="language.${contributor.locale.language}" /></p>
                    
                    <c:if test="${not empty contributor.occupation}">
                        <p><b><fmt:message key="occupation" /></b></p>
                        <p><c:out value="${contributor.occupation}" /></p>
                    </c:if>
                    
                    <p><b><fmt:message key="teams" /></b></p>
                    <c:forEach var="team" items="${contributor.teams}">
                      <div class="chip">
                          <fmt:message key="team.${team}" />
                      </div>
                    </c:forEach>
                    
                    <p><b><fmt:message key="personal.motivation" /></b></p>
                    <p>"<c:out value="${contributor.motivation}" />"</p>
                    
                    <p><b><fmt:message key="time.available.per.week" /></b></p>
                    <p><c:out value="${contributor.timePerWeek}" />&nbsp;<fmt:message key="minutes" /></p>
                    
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="divider"></div>
                        
                        <div class="amber lighten-5" style="padding: 1em">
                            <blockquote>
                                <fmt:message key="only.visible.for.ROLE_ADMIN" />
                            </blockquote>

                            <p><b><fmt:message key="email" /></b></p>
                            <p><c:out value="${contributor.email}" /></p>

                            <p><b><fmt:message key="registration.time" /></b></p>
                            <p><fmt:formatDate value="${contributor.registrationTime.time}" type="both" timeStyle="short" /></p>
                            
                            <p><b><fmt:message key="referrer" /></b></p>
                            <p>
                                referrer: "<c:out value="${contributor.referrer}" />"<br />
                                utm_source: "<c:out value="${contributor.utmSource}" />"<br />
                                utm_medium: "<c:out value="${contributor.utmMedium}" />"<br />
                                utm_campaign: "<c:out value="${contributor.utmCampaign}" />"
                            </p>
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </c:forEach>
</content:section>
