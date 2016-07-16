<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <%-- The title should ideally be less than 64 characters in length (http://www.w3.org/Provider/Style/TITLE.html). --%>
        <title><content:gettitle /> | LiteracyApp.org</title>

        <meta charset="UTF-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link rel="shortcut icon" href="<spring:url value='/img/favicon.ico' />" />
        
        <%-- CSS --%>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
        <link rel="stylesheet" href="<spring:url value='/css/styles.css?version=' /><content:getversion />" />
        <link rel="stylesheet" href="<spring:url value='/css/content/styles.css?version=' /><content:getversion />" />
        
        <%-- JavaScripts --%>
        <script src="<spring:url value='/js/jquery-2.1.4.min.js' />"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
        <script src="<spring:url value='/js/init.js' />"></script>
        <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
        <%@ include file="/WEB-INF/jsp/google-analytics.jsp" %>
    </head>

    <body>
        <nav class="black lighten-1">
            <div class="row nav-wrapper">
                <div class="col s1">
                    <ul id="nav-mobile" class="side-nav">
                        <li>
                            <a href="<spring:url value='/content' />">
                                <img style="max-width: 100%; padding-top: 1em;" src="<spring:url value='/img/logo-text-256x52.png' />" alt="LiteracyApp" />
                            </a>
                        </li>
                        
                        <li class="divider"></li>
                        <li class="grey-text"><b><fmt:message key="community" /></b></li>
                        <li><a href="<spring:url value='/content/community/contributors' />"><i class="material-icons left">group</i><fmt:message key="contributors" /></a></li>
                        <li><a href="https://literacyapp.slack.com" target="_blank"><i class="material-icons left">chat_bubble_outline</i><fmt:message key="chat" /></a></li>
                        <li><a href="<spring:url value='/content/community/issue-management' />"><i class="material-icons left">assignment</i><fmt:message key="issue.management" /></a></li>
                        
                        <li class="divider"></li>
                        <%--<li class="grey-text"><b><fmt:message key="curriculum" /></b></li>
                        <li><a href="<spring:url value='/content/module/list' />"><i class="material-icons left">view_module</i><fmt:message key="modules" /> (age 5-6)</a></li>
                        <li><a href="<spring:url value='/content/module/list' />"><i class="material-icons left">view_module</i><fmt:message key="modules" /> (age 7-10)</a></li>
                        
                        <li class="divider"></li>--%>
                        <li class="grey-text"><b><fmt:message key="application.content" /></b></li>
                        <li><a href="<spring:url value='/content/number/list' />"><i class="material-icons left">looks_one</i><fmt:message key="numbers" /></a></li>
                        <li><a href="<spring:url value='/content/letter/list' />"><i class="material-icons left">text_format</i><fmt:message key="letters" /></a></li>
                        <li><a href="<spring:url value='/content/word/list' />"><i class="material-icons left">sms</i><fmt:message key="words" /></a></li>
                        <li><a href="<spring:url value='/content/audio/list' />"><i class="material-icons left">audiotrack</i><fmt:message key="audios" /></a></li>
                        <li><a href="<spring:url value='/content/image/list' />"><i class="material-icons left">image</i><fmt:message key="images" /></a></li>
                        <li><a href="<spring:url value='/content/video/list' />"><i class="material-icons left">movie</i><fmt:message key="videos" /></a></li>
                        
                        <li class="divider"></li>
                        <li class="grey-text"><b><fmt:message key="language.${contributor.locale.language}" /></b></li>
                        <li><a href="<spring:url value='/content/allophone/list' />"><i class="material-icons left">record_voice_over</i><fmt:message key="allophones" /></a></li>
                    </ul>
                    <a id="navButton" href="<spring:url value='/content' />" data-activates="nav-mobile" class="waves-effect waves-light"><i class="material-icons">dehaze</i></a>
                </div>
                <div class="col s5">
                    <a href="<spring:url value='/content' />" class="breadcrumb"><fmt:message key="content" /></a>
                    <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/main.jsp')}">
                        <a class="breadcrumb"><content:gettitle /></a>
                    </c:if>
                </div>
                <div class="col s6">
                    <ul class="right">
                        <a class="dropdown-button" data-activates="contributorDropdown" data-beloworigin="true" >
                            <div class="chip">
                                <img src="<spring:url value='${contributor.imageUrl}' />" alt="${contributor.firstName}" /> 
                                <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" /> &lt;${contributor.email}&gt; <%--<a href="<spring:url value='/j_spring_security_logout' />"><fmt:message key="sign.out" /></a>--%>
                            </div>
                        </a>
                        <ul id='contributorDropdown' class='dropdown-content'>
                            <li><a href="<spring:url value='/content/contributor/edit-locale' />"><i class="material-icons left">public</i><fmt:message key="select.language" /></a></li>
                            <li class="divider"></li>
                            <li><a href="<spring:url value='/content/contributor/edit-teams' />"><i class="material-icons left">group</i><fmt:message key="select.teams" /></a></li>
                            <li class="divider"></li>
                            <li><a href="<spring:url value='/content/contributor/edit-name' />"><i class="material-icons left">mode_edit</i><fmt:message key="edit.name" /></a></li>
                            <%--<li class="divider"></li>
                            <li><a href="<spring:url value='/content/contributor/edit-email' />"><i class="material-icons left">mail</i><fmt:message key="edit.email" /></a></li>--%>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li class="divider"></li>
                                <li><a href="<spring:url value='/admin' />"><i class="material-icons left">build</i><fmt:message key="administration" /></a></li>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_ANALYST')">
                                <li class="divider"></li>
                                <li><a href="<spring:url value='/analytics' />"><i class="material-icons left">timeline</i><fmt:message key="analytics" /></a></li>
                            </sec:authorize>
                            <li class="divider"></li>
                            <li><a href="<spring:url value='/j_spring_security_logout' />"><i class="material-icons left">power_settings_new</i><fmt:message key="sign.out" /></a></li>
                        </ul>
                    </ul>
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
    </body>
</html>
