<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<%@attribute name="modelAttribute" %>
<spring:hasBindErrors name="${modelAttribute}">
    <div id="errorPanel" class="card-panel red lighten-3">
        <c:forEach var="error" items="${errors.allErrors}">
            <li>
            <c:set var="errorMessageFoundInPropertiesFile" value="false" />
            <c:forEach var="code" items="${error.codes}">
                <c:set var="errorMessage"><fmt:message key="${code}" /></c:set>               
                <c:if test="${!fn:contains(errorMessage, '???') && !errorMessageFoundInPropertiesFile}">
                    <c:choose>
                        <c:when test="${fn:length(error.arguments) == 3}">
                            <fmt:message key="${code}">
                                <fmt:param><fmt:message key="${error.field}" /></fmt:param>
                                <fmt:param>${error.arguments[1]}</fmt:param>
                                <fmt:param>${error.arguments[2]}</fmt:param>
                                <fmt:param>${error.arguments[3]}</fmt:param>
                            </fmt:message>
                        </c:when>
                        <c:when test="${fn:length(error.arguments) == 2}">
                            <fmt:message key="${code}">
                                <fmt:param><fmt:message key="${error.field}" /></fmt:param>
                                <fmt:param>${error.arguments[1]}</fmt:param>
                                <fmt:param>${error.arguments[2]}</fmt:param>
                            </fmt:message>
                        </c:when>
                        <c:when test="${fn:length(error.arguments) == 1}">
                            <fmt:message key="${code}">
                                <fmt:param><fmt:message key="${error.field}" /></fmt:param>
                                <fmt:param>${error.arguments[1]}</fmt:param>
                            </fmt:message>
                        </c:when>
                        <c:otherwise>
                            <%-- Without parameter --%>
                            <fmt:message key="${code}" />
                        </c:otherwise>
                    </c:choose>
                    <c:set var="errorMessageFoundInPropertiesFile" value="true" />
                </c:if>
            </c:forEach>
            <c:if test="${!errorMessageFoundInPropertiesFile}">
                ${error}
            </c:if>
            </li>
        </c:forEach>
    </div>
 </spring:hasBindErrors>
