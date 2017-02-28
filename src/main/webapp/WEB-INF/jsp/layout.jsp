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
        <html lang="${locale.language}">
            <head>
                <%-- The title should ideally be less than 64 characters in length (http://www.w3.org/Provider/Style/TITLE.html). --%>
                <title><content:gettitle /> | LiteracyApp.org</title>

                <meta charset="UTF-8" />

                <meta name="viewport" content="width=device-width, initial-scale=1.0" />

                <%-- Google will only display the first 150 characters of the meta description in SERPs. --%>
                <meta name="description" content="<fmt:message key='one.ai.tutor.for.every.child'/>" />
                <link rel="shortcut icon" href="<spring:url value='/img/favicon.ico' />" />

                <meta property="og:image" content="http://${pageContext.request.serverName}/img/logo-256x256.png" />
                <meta property="twitter:image" content="http://${pageContext.request.serverName}/img/logo-256x256.png" />

                <%-- CSS --%>
                <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
                <%--<link rel="stylesheet" href="<spring:url value='/css/materialize.min-0.97.6.css' />" />--%>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
                <link rel="stylesheet" href="<spring:url value='/css/styles.css?version=' /><content:getversion />" />
            </head>

            <body>
                <a href="https://github.com/literacyapp-org">
                    <img style="position: absolute; top: 0; right: 0; border: 0;" 
                         src="https://camo.githubusercontent.com/e7bbb0521b397edbd5fe43e7f760759336b5e05f/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677265656e5f3030373230302e706e67" 
                         alt="Fork me on GitHub" 
                         data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_green_007200.png">
                </a>
                
                <nav class="black lighten-1">
                    <div class="nav-wrapper container">
                        <a id="logo-container" href="<spring:url value='/' />" class="brand-logo">
                            <img src="<spring:url value='/img/logo-208x208.png' />" alt="LiteracyApp" />
                            Literacy<span>App</span>
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
                        <div class="col s12">
                            <content:getsection />
                        </div>
                    </div>
                </div>

                <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
                    <footer class="page-footer green lighten-1">
                      <div class="container">
                        <div class="row">
                          <div class="col l6 s12">
                            <h5 class="white-text"><fmt:message key="about" /> LiteracyApp</h5>
                            <p class="grey-text text-lighten-4"><fmt:message key="we.build.open.source.tablet.software" /></p>
                            <p class="grey-text text-lighten-4"><fmt:message key="see.our" />&nbsp;<a class="white-text" href="https://github.com/literacyapp-org"><fmt:message key="github.repository" /></a></p>
                          </div>
                          <div class="col l3 s12">
                            <h5 class="white-text"><fmt:message key="join.the.community" /></h5>
                            <p class="grey-text text-lighten-4">
                                <fmt:message key="sign.up.to.our.mailing.list.to..." />
                            </p>
                            <a class="btn waves-effect waves-light green lighten-2" target="_blank" href="http://eepurl.com/bGihkr">
                                <fmt:message key="subscribe" /><i class="material-icons right">mail</i>
                            </a>
                          </div>
                          <div class="col l2 s12 right">
                            <h5 class="white-text"><fmt:message key="connect" /></h5>
                            <ul>
                                <li><a class="white-text" href="http://blog.literacyapp.org" target="_blank"><fmt:message key="blog" /></a></li>
                                <li><a class="white-text" href="https://twitter.com/literacyapp" target="_blank">Twitter</a></li>
                                <li><a class="white-text" href="https://www.facebook.com/literacyapp" target="_blank">Facebook</a></li>
                                <li><a class="white-text" href="https://plus.google.com/+LiteracyAppOrgCommunity" target="_blank">Google+</a></li>
                                <li><a class="white-text" href="https://www.linkedin.com/company/literacyapp-org" target="_blank">LinkedIn</a></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    <div class="footer-copyright">
                      <div class="container">
                          <div class="row">
                              <div class="col s6">
                                  <a class="white-text" href="http://www.apache.org/licenses/LICENSE-2.0">
                                      Apache License, 2.0
                                  </a>
                              </div>
                              <div class="col s6">
                                  <fmt:message key="switch.language" />: 
                                  <c:choose>
                                      <c:when test="${applicationScope.configProperties['env'] == 'DEV'}">
                                          <a class="white-text" href="<spring:url value='/?lang=en' />">
                                              English
                                          </a> | 
                                          <a class="white-text" href="<spring:url value='/?lang=es' />">
                                              Español
                                          </a> | 
                                          <a class="white-text" href="<spring:url value='/?lang=ar' />">
                                              العربية
                                          </a> | 
                                          <a class="white-text" href="<spring:url value='/?lang=sw' />">
                                              Kiswahili
                                          </a>
                                      </c:when>
                                      <c:when test="${applicationScope.configProperties['env'] == 'TEST'}">
                                          <a class="white-text" href="http://en.test.literacyapp.org">
                                              English
                                          </a> | 
                                          <a class="white-text" href="http://es.test.literacyapp.org">
                                              Español
                                          </a> | 
                                          <a class="white-text" href="http://ar.test.literacyapp.org">
                                              العربية
                                          </a> | 
                                          <a class="white-text" href="http://sw.test.literacyapp.org">
                                              Kiswahili
                                          </a>
                                      </c:when>
                                      <c:otherwise>
                                          <a class="white-text" href="http://en.literacyapp.org">
                                              English
                                          </a> | 
                                          <a class="white-text" href="http://es.literacyapp.org">
                                              Español
                                          </a> | 
                                          <a class="white-text" href="http://ar.literacyapp.org">
                                              العربية
                                          </a> | 
                                          <a class="white-text" href="http://sw.literacyapp.org">
                                              Kiswahili
                                          </a>
                                      </c:otherwise>
                                  </c:choose>
                              </div>
                          </div>
                      </div>
                    </div>
                  </footer>
                </c:if>

                <%-- JavaScripts --%>
                <script src="<spring:url value='/js/jquery-2.1.4.min.js' />"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
                <script src="<spring:url value='/js/init.js' />"></script>
                <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
                <%@ include file="/WEB-INF/jsp/google-analytics.jsp" %>

                <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/admin/') && !fn:contains(pageContext.request.requestURI, '/jsp/analytics/') && !fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
                    <%-- HelpScout Beacon --%>
                    <script>!function(e,o,n){window.HSCW=o,window.HS=n,n.beacon=n.beacon||{};var t=n.beacon;t.userConfig={},t.readyQueue=[],t.config=function(e){this.userConfig=e},t.ready=function(e){this.readyQueue.push(e)},o.config={docs:{enabled:!1,baseUrl:""},contact:{enabled:!0,formId:"317454f3-8c8e-11e5-9e75-0a7d6919297d"}};var r=e.getElementsByTagName("script")[0],c=e.createElement("script");c.type="text/javascript",c.async=!0,c.src="https://djtflbt20bdde.cloudfront.net/",r.parentNode.insertBefore(c,r)}(document,window.HSCW||{},window.HS||{});</script>
                </c:if>
            </body>
        </html>
    </c:otherwise>
</c:choose>