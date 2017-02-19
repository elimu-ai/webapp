<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <%-- Show number of apps in each EGRA/EGMA category --%>
        <div class="row">            
            <div class="col s12 m6">
                <h5>APKs Covering EGRA Skills</h5>
                
                <c:forEach var="literacySkillCount" items="${literacySkillCountMap}">
                    <fmt:message key="literacy.skill.${literacySkillCount.key}" /> (${literacySkillCount.value})<br />
                    <div class="progress">
                        <div class="determinate" style="width: ${literacySkillCount.value * 100 / maxLiteracySkillCount}%"></div>
                    </div>
                </c:forEach>
            </div>

            <div class="col s12 m6">
                <h5>APKs Covering EGMA Skills</h5>
                
                <c:forEach var="numeracySkillCount" items="${numeracySkillCountMap}">
                    <fmt:message key="numeracy.skill.${numeracySkillCount.key}" /> (${numeracySkillCount.value})<br />
                    <div class="progress">
                        <div class="determinate" style="width: ${numeracySkillCount.value * 100 / maxLiteracySkillCount}%"></div>
                    </div>
                </c:forEach>
            </div>
        </div>
        
        <hr />
        
        <%-- TODO: show progress bar for each content type --%>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <%-- Latest content creation events --%>
        <c:if test="${not empty contentCreationEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="content" /></th>
                    <th><fmt:message key="content.type" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="time.created_updated" /></th>
                    <th><fmt:message key="revision" /></th>
                </thead>
                <tbody>
                    <c:forEach var="contentCreationEvent" items="${contentCreationEvents}">
                        <tr class="contentCreationEvent">
                            <td>
                                <c:set var="multimediaUrl">
                                    <c:if test="${fn:contains(contentCreationEvent.content.class.package, 'multimedia')}">
                                        /multimedia
                                    </c:if>
                                </c:set>
                                <a href="<spring:url value='/content${multimediaUrl}/${fn:toLowerCase(contentCreationEvent.content.class.simpleName)}/edit/${contentCreationEvent.content.id}' />">
                                    <h4>
                                        <c:choose>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Allophone'}">
                                                /${contentCreationEvent.content.valueIpa}/ (${contentCreationEvent.content.valueSampa})
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Letter'}">
                                                ${contentCreationEvent.content.text}
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Number'}">
                                                <c:choose>
                                                    <c:when test="${not empty contentCreationEvent.content.symbol}">
                                                        ${contentCreationEvent.content.symbol} (${contentCreationEvent.content.value})
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${contentCreationEvent.content.value}
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Word'}">
                                                <c:out value="${contentCreationEvent.content.text}" />
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Audio'}">
                                                <%--<audio controls="true">
                                                    <source src="<spring:url value='/audio/${contentCreationEvent.content.id}.${fn:toLowerCase(contentCreationEvent.content.audioFormat)}' />" />
                                                </audio><br />--%>
                                                "<c:out value="${contentCreationEvent.content.transcription}" />"
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Image'}">
                                                <%--<img src="<spring:url value='/image/${contentCreationEvent.content.id}.${fn:toLowerCase(contentCreationEvent.content.imageFormat)}' />" 
                                                    style="max-height: 2em;" 
                                                    alt="<c:out value="${contentCreationEvent.content.title}" />" /><br />--%>
                                                <c:out value="${contentCreationEvent.content.title}" />
                                            </c:when>
                                            <c:when test="${contentCreationEvent.content.class.simpleName == 'Video'}">
                                                <%--<video controls="true">
                                                    <source src="<spring:url value='/video/${contentCreationEvent.content.id}.${fn:toLowerCase(contentCreationEvent.content.videoFormat)}' />" />
                                                </video><br />--%>
                                                "<c:out value="${contentCreationEvent.content.title}" />"
                                            </c:when>
                                        </c:choose>
                                    </h4>
                                </a>
                            </td>
                            <td>
                                <c:set var="contentClassName" value="${fn:toLowerCase(contentCreationEvent.content.class.simpleName)}" />
                                <fmt:message key="${contentClassName}" />
                            </td>
                            <td>
                                <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                                    <div class="chip">
                                        <img src="<spring:url value='${contentCreationEvent.contributor.imageUrl}' />" alt="${contentCreationEvent.contributor.firstName}" /> 
                                        <c:out value="${contentCreationEvent.contributor.firstName}" />&nbsp;<c:out value="${contentCreationEvent.contributor.lastName}" />
                                    </div>
                                </a>
                            </td>
                            <td>
                                <fmt:formatDate value="${contentCreationEvent.calendar.time}" type="both" timeStyle="short" />
                            </td>
                            <td>
                                ${contentCreationEvent.content.revisionNumber}
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
                <li><a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="110" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a></li>
                <li><a href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large purple darken-1 tooltipped" data-position="left" data-delay="100" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a></li>
                <li><a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large green tooltipped" data-position="left" data-delay="90" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a></li>
                <li><a href="<spring:url value='/content/storybook/create' />" class="btn-floating btn-large grey tooltipped" data-position="left" data-delay="80" data-tooltip="<fmt:message key="add.storybook" />"><i class="material-icons">book</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating btn-large blue tooltipped" data-position="left" data-delay="70" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating btn-large orange tooltipped" data-position="left" data-delay="60" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating btn-large teal tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a></li>
            </ul>
        </div>
    </div>
</content:section>
