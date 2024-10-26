<!DOCTYPE html>
<html lang="en" data-content-language="${fn:toLowerCase(applicationScope.configProperties['content.language'])}">
    <head>
        <title><content:gettitle /> | ${fn:toLowerCase(applicationScope.configProperties['content.language'])}.elimu.ai</title>

        <meta charset="UTF-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />
        
        <%-- CSS --%>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css" />
        <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
        <link rel="stylesheet" href="<spring:url value='/static/css/analytics/styles.css' />" />
        
        <%-- JavaScripts --%>
        <script src="<spring:url value='/static/js/jquery-3.6.0.min.js' />"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="<spring:url value='/static/js/init.js' />"></script>
        <script src="<spring:url value='/static/js/difflib-0.2.4.min.js' />"></script>
        <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
    </head>

    <body>
        <div id="formLoadingOverlay" style="display: none;">
            <div class="preloader-wrapper big active">
                <div class="spinner-layer spinner-blue-only">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div><div class="gap-patch">
                        <div class="circle"></div>
                    </div><div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <nav class="deep-purple lighten-1">
            <div class="row nav-wrapper">
                <div class="col s1">
                    <ul id="nav-mobile" class="side-nav">
                        <li>
                            <a href="<spring:url value='/analytics' />">
                                <img style="max-width: 100%; vertical-align: middle; max-height: 60%;" src="<spring:url value='/static/img/logo-text-256x78.png' />" alt="elimu.ai" />
                            </a>
                        </li>
                        
                        <li class="divider"></li>
                        <li class="grey-text"><b><fmt:message key="learning.events" /></b></li>
                        <li><a href="<spring:url value='/analytics/letter-learning-event/list' />"><i class="material-icons left">text_format</i><fmt:message key="letters" /></a></li>
                        <li><a href="<spring:url value='/analytics/word-learning-event/list' />"><i class="material-icons left">sms</i><fmt:message key="words" /></a></li>
                        <li><a href="<spring:url value='/analytics/storybook-learning-event/list' />"><i class="material-icons left">book</i><fmt:message key="storybooks" /></a></li>
                        <li><a href="<spring:url value='/analytics/video-learning-event/list' />"><i class="material-icons left">movie</i>Videos</a></li>
                    </ul>
                    <a id="navButton" href="<spring:url value='/analytics' />" data-activates="nav-mobile" class="waves-effect waves-light"><i class="material-icons">dehaze</i></a>
                </div>
                <div class="col s11">
                    <a href="<spring:url value='/analytics' />" class="breadcrumb"><fmt:message key="analytics" /></a>
                    <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/analytics/main.jsp')}">
                        <a class="breadcrumb"><content:gettitle /></a>
                    </c:if>
                </div>
            </div>
        </nav>
        
        <c:if test="${hasBanner}">
            <div class="section no-pad-bot" id="index-banner">
                <div class="container">
                    <content:getbanner />
                </div>
            </div>
        </c:if>

        <main id="${cssId}" class="container <c:if test="${cssClass != null}">${cssClass}</c:if>">
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
        </main>
    </body>
</html>
