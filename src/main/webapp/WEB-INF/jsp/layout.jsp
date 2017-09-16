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
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/project/')}">
        <%@ include file="/WEB-INF/jsp/project/layout.jsp" %>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html>
        <html lang="${locale.language}">
            <head>
                <%-- The title should ideally be less than 64 characters in length (http://www.w3.org/Provider/Style/TITLE.html). --%>
                <title><content:gettitle /> | elimu.ai</title>

                <meta charset="UTF-8" />

                <meta name="viewport" content="width=device-width, initial-scale=1.0" />

                <%-- Google will only display the first 150 characters of the meta description in SERPs. --%>
                <meta name="description" content="<fmt:message key='free.quality.education.for.every.child' />" />
                <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />

                <meta property="og:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />
                <meta property="twitter:image" content="http://${pageContext.request.serverName}/static/img/logo-256x256.png" />

                <%-- CSS --%>
                <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons" />
                <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Amaranth" />
                <%--<link rel="stylesheet" href="<spring:url value='/static/css/materialize.min-0.97.6.css' />" />--%>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
                <link rel="stylesheet" href="<spring:url value='/static/css/styles.css?version=' /><content:getversion />" />
                
                <%-- JavaScripts --%>
                <script src="<spring:url value='/static/js/jquery-2.1.4.min.js' />"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
                <script src="<spring:url value='/static/js/init.js' />"></script>
                <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
                <%@ include file="/WEB-INF/jsp/google-analytics.jsp" %>
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
                            <p class="grey-text text-lighten-2"><fmt:message key="we.build.tablet.based.software.which.is.open.source" />
                            <a class="white-text" href="<spring:url value='/publish' />"><fmt:message key="read.more" />...</a></p>
                          </div>
                          <div class="col l3 offset-l1 s12 ">
                            <h5 class="white-text"><fmt:message key="contact.us" /></h5>
                            <p class="grey-text text-lighten-2">
                                Send us an <a class="white-text" href="mailto:info@elimu.ai"><fmt:message key="email" /></a> 
                                or talk with us directly in our chat room:
                            </p>
                            <a class="btn waves-effect waves-light deep-purple lighten-2" target="_blank" href="http://slack.elimu.ai">
                                <fmt:message key="open.chat" /><i class="material-icons right">forum</i>
                            </a>
                            <%--<p class="grey-text text-lighten-4">
                                <fmt:message key="mailing.list" />: <a class="white-text" href="http://eepurl.com/bGihkr" target="_blank"><fmt:message key="subscribe" /></a>
                            </p>--%>
                          </div>
                          <div class="col l2 s12">
                            <h5 class="white-text"><fmt:message key="social.media" /></h5>
                            <ul>
                                <li><a class="white-text" href="https://twitter.com/elimu_ai" target="_blank">Twitter</a></li>
                                <li><a class="white-text" href="https://www.facebook.com/elimuai" target="_blank">Facebook</a></li>
                                <li><a class="white-text" href="https://plus.google.com/u/0/100080736050421577958" target="_blank">Google+</a></li>
                                <li><a class="white-text" href="https://www.linkedin.com/company-beta/18167293" target="_blank">LinkedIn</a></li>
                                <li><a class="white-text" href="https://medium.com/elimu-ai" target="_blank"><fmt:message key="blog" /></a></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    <div class="footer-copyright">
                      <div class="container">
                          <div class="row">
                              <div class="col s12">
                                  <a class="white-text" href="http://www.apache.org/licenses/LICENSE-2.0">
                                      Apache License, 2.0
                                  </a> &nbsp; | &nbsp; 
                                  <fmt:message key="see.our" />&nbsp;<a class="white-text" href="https://github.com/elimu-ai"><fmt:message key="github.repository" /></a>
                                   &nbsp; | &nbsp; 
                                   <fmt:message key="languages.supported.by.the.platform" />: 
                                    <c:choose>
                                        <c:when test="${applicationScope.configProperties['env'] == 'DEV'}">
                                            <a class="white-text" href="<spring:url value='/?lang=en' />" title="English">en</a>
                                            <a class="white-text" href="<spring:url value='/?lang=es' />" title="Español">es</a>
                                            <a class="white-text" href="<spring:url value='/?lang=ar' />" title="العربية">ar</a>
                                            <a class="white-text" href="<spring:url value='/?lang=sw' />" title="Kiswahili">sw</a>
                                        </c:when>
                                        <c:when test="${applicationScope.configProperties['env'] == 'TEST'}">
                                            <a class="white-text" href="http://en.test.elimu.ai" title="English">en</a>
                                            <a class="white-text" href="http://es.test.elimu.ai" title="Español">es</a>
                                            <a class="white-text" href="http://ar.test.elimu.ai" title="العربية">ar</a>
                                            <a class="white-text" href="http://sw.test.elimu.ai" title="Kiswahili">sw</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="white-text" href="http://test.elimu.ai" title="English">en</a>
                                            <a class="white-text" href="http://test.elimu.ai" title="Español">es</a>
                                            <a class="white-text" href="http://test.elimu.ai" title="العربية">ar</a>
                                            <a class="white-text" href="http://test.elimu.ai" title="Kiswahili">sw</a>
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