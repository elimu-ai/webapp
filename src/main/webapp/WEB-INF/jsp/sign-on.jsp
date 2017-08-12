<content:title>
    <fmt:message key="sign.on" />
</content:title>

<content:section cssId="signOnPage">
    <h2 class="center"><i class="material-icons large deep-purple-text">group</i></h2>
    <h2 class="center"><fmt:message key="join.the.community" /></h2>
    
    <div class="section">
        <div class="row section">
            <p class="center"><fmt:message key="sign.on.via.an.existing.account" />:</p>
            
            <div class="col s12 m4 offset-m2">
                <a href="<spring:url value='/sign-on/facebook' />" class="col s12 btn-large waves-effect waves-light blue darken-3">
                    <svg style="width:24px;height:24px;top: 6px; position: relative; right: 5px;" viewBox="0 0 24 24">
                        <path fill="#ffffff" d="M19,4V7H17A1,1 0 0,0 16,8V10H19V13H16V20H13V13H11V10H13V7.5C13,5.56 14.57,4 16.5,4M20,2H4A2,2 0 0,0 2,4V20A2,2 0 0,0 4,22H20A2,2 0 0,0 22,20V4C22,2.89 21.1,2 20,2Z" />
                    </svg>
                    &nbsp;Facebook
                </a>
            </div>
            
            <div class="col s12 m4">
                <a href="<spring:url value='/sign-on/google' />" class="col s12 btn-large waves-effect waves-light red darken-2">
                    <svg style="width:24px;height:24px;top: 6px; position: relative; right: 5px;" viewBox="0 0 24 24">
                        <path fill="#ffffff" d="M19,12H17V14H16V12H14V11H16V9H17V11H19M9.07,19.2C6.27,19.2 5,17.64 5,16.18C5,15.73 5.14,14.59 6.5,13.8C7.25,13.33 8.33,13 9.62,12.89C9.43,12.64 9.28,12.34 9.28,11.9C9.28,11.75 9.3,11.59 9.34,11.44H8.95C7,11.44 5.8,9.89 5.8,8.39C5.8,6.66 7.09,4.8 9.91,4.8H14.13L13.79,5.14L13.08,5.85L13,5.91H12.3C12.71,6.33 13.2,7 13.2,8.07C13.2,9.47 12.46,10.16 11.64,10.8C11.5,10.92 11.22,11.18 11.22,11.5C11.22,11.82 11.46,12 11.61,12.14C11.74,12.25 11.9,12.36 12.08,12.5C12.89,13.05 14,13.83 14,15.36C14,17.13 12.71,19.2 9.07,19.2M20,2H4A2,2 0 0,0 2,4V20A2,2 0 0,0 4,22H20A2,2 0 0,0 22,20V4C22,2.89 21.1,2 20,2M10.57,13.81C10.46,13.8 10.38,13.8 10.25,13.8H10.23C9.97,13.8 9.08,13.85 8.41,14.07C7.77,14.31 7,14.79 7,15.77C7,16.85 8.04,18 9.96,18C11.5,18 12.4,17 12.4,16C12.4,15.25 11.94,14.79 10.57,13.81M11.2,8.87C11.2,7.85 10.57,5.85 9.12,5.85C8.5,5.85 7.8,6.29 7.8,7.5C7.8,8.7 8.42,10.45 9.77,10.45C9.83,10.45 11.2,10.44 11.2,8.87Z" />
                    </svg>
                    &nbsp;Google+
                </a>
            </div>
        </div>
        
        <div class="divider"></div>
                
        <div class="row section">
            <p class="center"><fmt:message key="are.you.a.developer" /></p>
            
            <div class="col s12 m4 offset-m4">
                <a href="<spring:url value='/sign-on/github' />" class="col s12 btn-large waves-effect waves-light grey darken-1">
                    <svg style="width:32px;height:32px;top: 6px; position: relative; right: 5px;" viewBox="0 0 50 50">
                        <path fill="#ffffff" d="M32,16c-8.8,0-16,7.2-16,16c0,7.1,4.6,13.1,10.9,15.2 c0.8,0.1,1.1-0.3,1.1-0.8c0-0.4,0-1.4,0-2.7c-4.5,1-5.4-2.1-5.4-2.1c-0.7-1.8-1.8-2.3-1.8-2.3c-1.5-1,0.1-1,0.1-1 c1.6,0.1,2.5,1.6,2.5,1.6c1.4,2.4,3.7,1.7,4.7,1.3c0.1-1,0.6-1.7,1-2.1c-3.6-0.4-7.3-1.8-7.3-7.9c0-1.7,0.6-3.2,1.6-4.3 c-0.2-0.4-0.7-2,0.2-4.2c0,0,1.3-0.4,4.4,1.6c1.3-0.4,2.6-0.5,4-0.5c1.4,0,2.7,0.2,4,0.5c3.1-2.1,4.4-1.6,4.4-1.6 c0.9,2.2,0.3,3.8,0.2,4.2c1,1.1,1.6,2.5,1.6,4.3c0,6.1-3.7,7.5-7.3,7.9c0.6,0.5,1.1,1.5,1.1,3c0,2.1,0,3.9,0,4.4 c0,0.4,0.3,0.9,1.1,0.8C43.4,45.1,48,39.1,48,32C48,23.2,40.8,16,32,16z" />
                    </svg>
                    &nbsp;GitHub
                </a>
            </div>
        </div>
    </div>    
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="teams" /></h5>
    
    <p>
        Once you sign on, we will ask you to pick the team(s) where you wish to 
        contribute:
    </p>
    
    <div class="divider"></div>
    
    <ul class="browser-default">
        <c:forEach var="team" items="${teams}">
            <c:if test="${team != 'OTHER'}">
                <li>
                    <b><fmt:message key="team.${team}" /></b> (<fmt:message key="team.${team}.description" />)<br />
                    <br />
                </li>
            </c:if>
        </c:forEach>
    </ul>
</content:aside>
