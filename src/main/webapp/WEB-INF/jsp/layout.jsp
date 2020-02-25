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
        <html lang="en">
            <head>
                <title><content:gettitle /> | elimu.ai</title>

                <meta charset="UTF-8" />

                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                
                <meta name="description" content="<fmt:message key="we.are.an.open.community.with" />" />
                <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />

                <meta property="og:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />
                <meta property="twitter:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />

                <%-- CSS --%>
                <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons" />
                <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Poppins" />
                <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Amaranth" />
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
                <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
                
                <%-- JavaScripts --%>
                <script src="<spring:url value='/static/js/jquery-2.1.4.min.js' />"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
                <script src="<spring:url value='/static/js/init.js' />"></script>
                <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
            </head>

            <body>
                <a href="https://github.com/elimu-ai"><img style="position: absolute; top: 0; right: 0; border: 0;" src="https://camo.githubusercontent.com/38ef81f8aca64bb9a64448d0d70f1308ef5341ab/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6461726b626c75655f3132313632312e706e67" alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_darkblue_121621.png"></a>
                
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
                                        <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /> &lt;${contributor.email}&gt; <%--<a href="<spring:url value='/j_spring_security_logout' />"><fmt:message key="sign.out" /></a>--%>
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
                            <h5 class="white-text"><fmt:message key="about" /> elimu.ai</h5>
                            <p class="grey-text text-lighten-2"><fmt:message key="we.are.an.open.community.with" /></p>
                            <p class="grey-text text-lighten-2"><fmt:message key="we.build.educational.software.which.is.open.source" />
                            <a class="white-text" href="<spring:url value='/publish' />"><fmt:message key="read.more" />...</a></p>
                          </div>
                          <div class="col l3 offset-l1 s12 ">
                            <h5 class="white-text"><fmt:message key="contact.us" /> 👋🏽</h5>
                            <p class="grey-text text-lighten-2">
                                Send us an <a class="white-text" href="mailto:info@elimu.ai" style="text-transform: lowercase;"><fmt:message key="email" /></a> 
                                or talk with us directly in our chat room:
                            </p>
                            <a class="btn waves-effect waves-light deep-purple lighten-2" target="_blank" href="http://slack.elimu.ai">
                                <fmt:message key="open.chat" /><i class="material-icons right">forum</i>
                            </a>
                          </div>
                          <div class="col l2 s12">
                            <h5 class="white-text"><fmt:message key="social.media" /></h5>
                            <ul>
                                <li><a class="white-text" href="https://twitter.com/elimu_ai" target="_blank">Twitter</a></li>
                                <li><a class="white-text" href="https://www.facebook.com/elimuai" target="_blank">Facebook</a></li>
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
                                <a class="white-text" href="http://www.apache.org/licenses/LICENSE-2.0">
                                    Apache License, 2.0
                                </a> &nbsp; | &nbsp; 
                                <fmt:message key="see.our" />&nbsp;<a class="white-text" href="https://github.com/elimu-ai"><fmt:message key="github.repository" /></a>
                            </div>
                            <div class="col s12 m6">
                                <fmt:message key="languages.supported.by.the.platform" />: 
                                <c:choose>
                                    <c:when test="${applicationScope.configProperties['env'] == 'DEV'}">
                                        <a class="white-text" href="<spring:url value='/?lang=eng' />" title="English">eng</a>
                                        <a class="white-text" href="<spring:url value='/?lang=fil' />" title="Filipino">fil</a>
                                        <a class="white-text" href="<spring:url value='/?lang=hin' />" title="हिंदी">hin</a>
                                        <a class="white-text" href="<spring:url value='/?lang=swa' />" title="Kiswahili">swa</a>
                                    </c:when>
                                    <c:when test="${applicationScope.configProperties['env'] == 'TEST'}">
                                        <a class="white-text" href="http://eng.test.elimu.ai" title="English">eng</a>
                                        <a class="white-text" href="http://fil.test.elimu.ai" title="Filipino">fil</a>
                                        <a class="white-text" href="http://hin.test.elimu.ai" title="हिंदी">hin</a>
                                        <a class="white-text" href="http://swa.test.elimu.ai" title="Kiswahili">swa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="white-text" href="http://eng.elimu.ai" title="English">eng</a>
                                        <a class="white-text" href="http://fil.elimu.ai" title="Filipino">fil</a>
                                        <a class="white-text" href="http://hin.elimu.ai" title="हिंदी">hin</a>
                                        <a class="white-text" href="http://swa.elimu.ai" title="Kiswahili">swa</a>
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
