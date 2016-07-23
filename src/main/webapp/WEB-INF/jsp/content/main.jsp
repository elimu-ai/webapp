<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <h4><fmt:message key="latest.uploads" /></h4>
        
        <%-- TODO: show progress bar for each content type --%>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <%-- Latest content creation events --%>
        <c:if test="${not empty contentCreationEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="content.type" /></th>
                    <th><fmt:message key="content" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="time.created_updated" /></th>
                </thead>
                <tbody>
                    <c:forEach var="contentCreationEvent" items="${contentCreationEvents}">
                        <tr class="contentCreationEvent">
                            <td>
                                <c:set var="contentClassName" value="${fn:toLowerCase(contentCreationEvent.content.class.simpleName)}" />
                                <fmt:message key="${contentClassName}" />
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Allophone'}">
                                        
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Letter'}">
                                        <%-- TODO --%>
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Number'}">
                                        <%-- TODO: display value/symbol --%>
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Word'}">
                                        
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Audio'}">
                                        
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Image'}">
                                        <%-- TODO --%>
                                    </c:when>
                                    <c:when test="${contentCreationEvent.content.class.simpleName == 'Video'}">
                                        
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <div class="chip">
                                    <img src="<spring:url value='${contentCreationEvent.contributor.imageUrl}' />" alt="${contentCreationEvent.contributor.firstName}" /> 
                                    <c:out value="${contentCreationEvent.contributor.firstName}" />&nbsp;<c:out value="${contentCreationEvent.contributor.lastName}" />
                                </div>
                            </td>
                            <td>
                                <fmt:formatDate value="${contentCreationEvent.calendar.time}" type="both" timeStyle="short" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
            <a class="btn-floating btn-large red" title="Add content">
                <i class="large material-icons">add</i>
            </a>
            <ul>
                <li><a href="<spring:url value='/content/number/create' />" class="btn-floating red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a></li>
                <li><a href="<spring:url value='/content/letter/create' />" class="btn-floating yellow darken-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a></li>
                <li><a href="<spring:url value='/content/word/create' />" class="btn-floating green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating blue tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating teal tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a></li>
            </ul>
        </div>
    </div>
</content:section>
