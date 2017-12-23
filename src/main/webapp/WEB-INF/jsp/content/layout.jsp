<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <%-- The title should ideally be less than 64 characters in length (http://www.w3.org/Provider/Style/TITLE.html). --%>
        <title><content:gettitle /> | elimu.ai</title>

        <meta charset="UTF-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />
        
        <%-- CSS --%>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" />
        <link rel="stylesheet" href="<spring:url value='/static/css/styles.css?version=' /><content:getversion />" />
        <link href="https://fonts.googleapis.com/css?family=Andika" rel="stylesheet" />
        <link rel="stylesheet" href="<spring:url value='/static/css/content/styles.css?version=' /><content:getversion />" />
        
        <%-- JavaScripts --%>
        <script src="<spring:url value='/static/js/jquery-2.1.4.min.js' />"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
        <script src="<spring:url value='/static/js/init.js' />"></script>
        <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
        <%@ include file="/WEB-INF/jsp/google-analytics.jsp" %>
    </head>

    <body>
        <nav class="deep-purple lighten-1">
            <div class="row nav-wrapper">
                <div class="col s1">
                    <ul id="nav-mobile" class="side-nav">
                        <li>
                            <a href="<spring:url value='/content' />">
                                <img style="max-width: 100%; padding-top: 1em;" src="<spring:url value='/static/img/logo-text-256x78.png' />" alt="elimu.ai" />
                            </a>
                        </li>
                        
                        <li class="divider"></li>
                        <li class="grey-text"><b><fmt:message key="community" /></b></li>
                        <li><a href="<spring:url value='/content/community/contributors' />"><i class="material-icons left">group</i><fmt:message key="contributors" /></a></li>
                        <li><a href="http://slack.elimu.ai" target="_blank"><i class="material-icons left">chat_bubble_outline</i><fmt:message key="chat" /></a></li>
                        <li><a href="<spring:url value='/content/community/issue-management' />"><i class="material-icons left">assignment</i><fmt:message key="issue.management" /></a></li>
                        
                        <li class="divider"></li>
                        <%--<li class="grey-text"><b><fmt:message key="curriculum" /></b></li>
                        <li><a href="<spring:url value='/content/module/list' />"><i class="material-icons left">view_module</i><fmt:message key="modules" /> (age 5-6)</a></li>
                        <li><a href="<spring:url value='/content/module/list' />"><i class="material-icons left">view_module</i><fmt:message key="modules" /> (age 7-10)</a></li>
                        
                        <li class="divider"></li>--%>
                        <li class="grey-text"><b><fmt:message key="application.content" /></b></li>
                        <li><a href="<spring:url value='/content/number/list' />"><i class="material-icons left">looks_one</i><fmt:message key="numbers" /></a></li>
                        <li><a href="<spring:url value='/content/letter/list' />"><i class="material-icons left">text_format</i><fmt:message key="letters" /></a></li>
                        <li><a href="<spring:url value='/content/syllable/list' />"><i class="material-icons left">queue_music</i><fmt:message key="syllables" /></a></li>
                        <li><a href="<spring:url value='/content/word/list' />"><i class="material-icons left">sms</i><fmt:message key="words" /></a></li>
                        <li><a href="<spring:url value='/content/storybook/list' />"><i class="material-icons left">book</i><fmt:message key="storybooks" /></a></li>
                        <li><a href="<spring:url value='/content/multimedia/audio/list' />"><i class="material-icons left">audiotrack</i><fmt:message key="audios" /></a></li>
                        <li><a href="<spring:url value='/content/multimedia/image/list' />"><i class="material-icons left">image</i><fmt:message key="images" /></a></li>
                        <li><a href="<spring:url value='/content/multimedia/video/list' />"><i class="material-icons left">movie</i><fmt:message key="videos" /></a></li>
                        
                        <li class="divider"></li>
                        <li class="grey-text"><b><fmt:message key="language.${contributor.locale.language}" /></b></li>
                        <li><a href="<spring:url value='/content/allophone/list' />"><i class="material-icons left">record_voice_over</i><fmt:message key="allophones" /></a></li>
                    </ul>
                    <a id="navButton" href="<spring:url value='/content' />" data-activates="nav-mobile" class="waves-effect waves-light"><i class="material-icons">dehaze</i></a>
                </div>
                <div class="col s5">
                    <a href="<spring:url value='/content' />" class="breadcrumb"><fmt:message key="content" /></a>
                    <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/main.jsp')}">
                        <c:choose>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/number/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/number/list' />"><fmt:message key="numbers" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/letter/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/letter/list' />"><fmt:message key="letters" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/word/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/word/list' />"><fmt:message key="words" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/storybook/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/storybook/list' />"><fmt:message key="storybooks" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/audio/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/multimedia/audio/list' />"><fmt:message key="audios" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/image/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/multimedia/image/list' />"><fmt:message key="images" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/video/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/multimedia/video/list' />"><fmt:message key="videos" /></a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/allophone/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/allophone/list' />"><fmt:message key="allophones" /></a>
                            </c:when>
                        </c:choose>
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
                            <sec:authorize access="hasRole('ROLE_PROJECT_MANAGER')">
                                <li class="divider"></li>
                                <li><a href="<spring:url value='/project' />"><i class="material-icons left">list</i><fmt:message key="projects" /></a></li>
                            </sec:authorize>
                            <li class="divider"></li>
                            <li><a href="<spring:url value='/j_spring_security_logout' />"><i class="material-icons left">power_settings_new</i><fmt:message key="sign.out" /></a></li>
                        </ul>
                    </ul>
                    <c:if test="${not empty contributor.locale}">
                        <div class="right">
                            <div class="white-text"><fmt:message key="language.${contributor.locale.language}" /></div>
                        </div>
                    </c:if>
                </div>
            </div>
        </nav>
        <script>
            $(function() {
                <c:choose>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/number/')}">
                        $('nav').addClass('indigo');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/letter/')}">
                        $('nav').addClass('teal');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/syllable/')}">
                        $('nav').addClass('green');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/word/')}">
                        $('nav').addClass('green');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/storybook/')}">
                        $('nav').addClass('lime');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/audio/')}">
                        $('nav').addClass('amber');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/image/')}">
                        $('nav').addClass('orange');
                    </c:when>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/video/')}">
                        $('nav').addClass('deep-orange');
                    </c:when>
                </c:choose>
            });
        </script>
                        
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
