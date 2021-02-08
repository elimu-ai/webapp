<c:choose>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/admin/')}">
        <%@ include file="/WEB-INF/jsp/admin/layout.jsp" %>
    </c:when>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/analytics/')}">
        <%@ include file="/WEB-INF/jsp/analytics/layout.jsp" %>
    </c:when>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
        <%@ include file="/WEB-INF/jsp/content/layout.jsp" %>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html>
        <html lang="en" data-content-language="${fn:toLowerCase(applicationScope.configProperties['content.language'])}">
            <head>
                <title><content:gettitle /> | ${fn:toLowerCase(applicationScope.configProperties['content.language'])}.elimu.ai</title>

                <meta charset="UTF-8" />

                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                
                <meta name="description" content="<fmt:message key="we.are.an.open.community.with" />" />
                <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />

                <meta property="og:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />
                <meta property="twitter:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />

                <%-- CSS --%>
                <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons" />
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
                <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Poppins" />
                <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Amaranth" />
                <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
                
                <%-- JavaScripts --%>
                <script src="<spring:url value='/static/js/jquery-2.1.4.min.js' />"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
                <script src="<spring:url value='/static/js/init.js' />"></script>
                <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
            </head>

            <body>
                <a href="https://github.com/elimu-ai" class="github-corner" aria-label="View source on GitHub"><svg width="80" height="80" viewBox="0 0 250 250" style="fill:#151513; color:#fff; position: absolute; top: 0; border: 0; right: 0;" aria-hidden="true"><path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path><path d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2" fill="currentColor" style="transform-origin: 130px 106px;" class="octo-arm"></path><path d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z" fill="currentColor" class="octo-body"></path></svg></a><style>.github-corner:hover .octo-arm{animation:octocat-wave 560ms ease-in-out}@keyframes octocat-wave{0%,100%{transform:rotate(0)}20%,60%{transform:rotate(-25deg)}40%,80%{transform:rotate(10deg)}}@media (max-width:500px){.github-corner:hover .octo-arm{animation:none}.github-corner .octo-arm{animation:octocat-wave 560ms ease-in-out}}</style>
                
                <nav class="deep-purple lighten-1">
                    <div class="nav-wrapper container">
                        <a id="logo-container" href="<spring:url value='/' />" class="brand-logo">
                            <img src="<spring:url value='/static/img/logo-text-256x77.png' />" alt="elimu.ai" />
                        </a>
                        <sec:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_CONTRIBUTOR')">
                            <ul class="right hide-on-med-and-down">
                                <li><a href="<spring:url value='/sign-on' />"><fmt:message key="sign.on" /></a></li>
                            </ul>
                            <ul id="nav-mobile" class="side-nav">
                                <li><a href="<spring:url value='/sign-on' />"><fmt:message key="sign.on" /></a></li>
                            </ul>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CONTRIBUTOR')">
                            <ul class="right">
                                <a href="<spring:url value='/content' />">
                                    <div class="chip">
                                        <img src="<spring:url value='${contributor.imageUrl}' />" alt="${contributor.firstName}" /> 
                                        <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /> &lt;${contributor.email}&gt; <%--<a href="<spring:url value='/logout' />"><fmt:message key="sign.out" /></a>--%>
                                    </div>
                                </a>
                            </ul>
                        </sec:authorize>
                        <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
                    </div>
                </nav>

                <c:if test="${hasBanner}">
                    <div class="section no-pad-bot" id="index-banner">
                        <div class="container">
                            <content:getbanner />
                        </div>
                    </div>
                </c:if>

                <div id="${cssId}" class="container <c:if test="${cssClass != null}">${cssClass}</c:if>">
                    <div class="section row">
                        <c:choose>
                            <c:when test="${!hasAside}">
                                <div class="col s12">
                                    <content:getsection />
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col s12 m8">
                                    <content:getsection />
                                </div>
                                <div class="col s12 m4">
                                    <content:getaside />
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
                    <footer class="page-footer deep-purple lighten-1">
                      <div class="container">
                        <div class="row">
                          <div class="col l6 s12">
                            <h5 class="white-text">About the elimu.ai Community</h5>
                            <p class="grey-text text-lighten-2">The purpose of the elimu.ai Community is to provide disadvantaged children with access to quality basic education.</p>
                            <p class="grey-text text-lighten-2">Read more about the project at <a class="white-text" href="https://github.com/elimu-ai/wiki" target="_blank">github.com/elimu-ai/wiki</a></p>
                          </div>
                          <div class="col l3 offset-l1 s12 ">
                            <h5 class="white-text"><fmt:message key="contact.us" /> 👋🏽</h5>
                            <p class="grey-text text-lighten-2">
                                Send us an <a class="white-text" href="mailto:info@elimu.ai" style="text-transform: lowercase;"><fmt:message key="email" /></a> 
                                or talk with us directly in our chat room:
                            </p>
                            <a class="btn waves-effect waves-light deep-purple lighten-2" target="_blank" href="https://join.slack.com/t/elimu-ai/shared_invite/zt-eoc921ow-0cfjATlIF2X~zHhSgSyaAw">
                                <fmt:message key="open.chat" /><i class="material-icons right">forum</i>
                            </a>
                          </div>
                          <div class="col l2 s12">
                            <h5 class="white-text"><fmt:message key="social.media" /></h5>
                            <ul>
                                <li><a class="white-text" href="https://twitter.com/elimu_ai" target="_blank">Twitter</a></li>
                                <li><a class="white-text" href="https://www.facebook.com/elimuai" target="_blank">Facebook</a></li>
                                <li><a class="white-text" href="https://www.instagram.com/elimu.ai" target="_blank">Instagram</a></li>
                                <li><a class="white-text" href="https://www.youtube.com/channel/UCRjBGmM0huH2DhVH3jJ8B6Q" target="_blank">YouTube</a></li>
                                <li><a class="white-text" href="https://www.linkedin.com/company/elimuai" target="_blank">LinkedIn</a></li>
                                <li><a class="white-text" href="https://medium.com/elimu-ai" target="_blank"><fmt:message key="blog" /></a></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    <div class="footer-copyright">
                      <div class="container">
                          <div class="row">
                            <div class="col s12 m6">
                                <a class="white-text" href="https://opensource.org/licenses/MIT">MIT License</a> &nbsp; | &nbsp; <fmt:message key="see.our" />&nbsp;<a class="white-text" href="https://github.com/elimu-ai">code repository</a><br />
                                <a class="white-text" href="https://creativecommons.org/licenses/by/4.0/">CC BY 4.0</a> &nbsp; | &nbsp; <fmt:message key="see.our" />&nbsp;<a class="white-text" href="<spring:url value='/content' />">content repository</a><br />
                            </div>
                            <div class="col s12 m6">
                                <fmt:message key="languages.supported.by.the.platform" />: 
                                <c:choose>
                                    <c:when test="${applicationScope.configProperties['env'] == 'DEV'}">
                                        <c:forEach var="supportedLanguage" items="${applicationScope.configProperties['supported.languages']}" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                • 
                                            </c:if>
                                            <a class="white-text" href="<spring:url value='/?lang=${supportedLanguage.isoCode}' />" title="${supportedLanguage.nativeName} (${supportedLanguage.englishName})">${supportedLanguage.isoCode}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${applicationScope.configProperties['env'] == 'TEST'}">
                                        <c:forEach var="supportedLanguage" items="${applicationScope.configProperties['supported.languages']}" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                • 
                                            </c:if>
                                            <a class="white-text" href="http://${supportedLanguage.isoCode}.test.elimu.ai" title="${supportedLanguage.nativeName} (${supportedLanguage.englishName})">${supportedLanguage.isoCode}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="supportedLanguage" items="${applicationScope.configProperties['supported.languages']}" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                • 
                                            </c:if>
                                            <a class="white-text" href="http://${supportedLanguage.isoCode}.elimu.ai" title="${supportedLanguage.nativeName} (${supportedLanguage.englishName})">${supportedLanguage.isoCode}</a>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                          </div>
                      </div>
                    </div>
                  </footer>
                </c:if>
            </body>
        </html>
    </c:otherwise>
</c:choose>
