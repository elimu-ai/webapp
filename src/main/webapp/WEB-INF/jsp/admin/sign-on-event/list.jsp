<content:title>
    <fmt:message key="sign.on.events" /> (${fn:length(signOnEvents)})
</content:title>

<content:section cssId="signOnEventsPage">
    <div class="section row">
        <c:if test="${not empty signOnEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="time" /></th>
                    <th><fmt:message key="contributor" /></th>
                    <th><fmt:message key="domain" /></th>
                    <th><fmt:message key="provider" /></th>
                    <th><fmt:message key="ip.address" /></th>
                    <th><fmt:message key="user.agent" /></th>
                    <th><fmt:message key="referrer" /></th>
                </thead>
                <tbody>
                    <c:forEach var="signOnEvent" items="${signOnEvents}">
                        <tr>
                            <td>
                                <fmt:formatDate value="${signOnEvent.calendar.time}" type="both" timeStyle="short" />
                            </td>
                            <td>
                                <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                                    <div class="chip">
                                        <img src="<spring:url value='${signOnEvent.contributor.imageUrl}' />" alt="${signOnEvent.contributor.firstName}" /> 
                                        <c:out value="${signOnEvent.contributor.firstName}" />&nbsp;<c:out value="${signOnEvent.contributor.lastName}" />
                                    </div>
                                </a> (<fmt:message key="language.${signOnEvent.contributor.locale.language}" />)
                            </td>
                            <td>
                                ${signOnEvent.serverName}
                            </td>
                            <td>
                                ${signOnEvent.provider}
                            </td>
                            <td>
                                ${signOnEvent.remoteAddress}
                            </td>
                            <td>
                                <c:out value="${signOnEvent.userAgent}" />
                            </td>
                            <td>
                                referrer: "<c:out value="${signOnEvent.referrer}" />"<br />
                                utmSource: "<c:out value="${signOnEvent.utmSource}" />"<br />
                                utmMedium: "<c:out value="${signOnEvent.utmMedium}" />"<br />
                                utmCampaign: "<c:out value="${signOnEvent.utmCampaign}" />"<br />
                                utmTerm: "<c:out value="${signOnEvent.utmTerm}" />"<br />
                                referralId: "<c:out value="${signOnEvent.referralId}" />"<br />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
