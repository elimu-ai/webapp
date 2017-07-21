<content:title>
    <fmt:message key="application.opened.events" />
</content:title>

<content:section cssId="applicationOpenedEventListPage">
    <div class="section row">
        <c:if test="${empty applicationOpenedEvents}">
            <p>No ApplicationOpenedEvents have been registered.</p>
            <p>Remember to install the <a href="https://github.com/elimu-ai/analytics" target="_blank">elimu.ai Analytics</a> application on the tablet(s).</p>
        </c:if>
        <c:if test="${not empty applicationOpenedEvents}">
            <table class="bordered highlight">
                <thead>
                    <th>id</th>
                    <th><fmt:message key="device.id" /></th>
                    <th><fmt:message key="time.registered" /></th>
                    <th><fmt:message key="package.name" /></th>
                    <th><fmt:message key="student" /></th>
                </thead>
                <tbody>
                    <c:forEach var="applicationOpenedEvent" items="${applicationOpenedEvents}">
                        <tr class="applicationOpenedEvent">
                            <td>
                                ${applicationOpenedEvent.id}
                            </td>
                            <td>
                                ${applicationOpenedEvent.device.deviceId}
                            </td>
                            <td>
                                <fmt:formatDate value="${applicationOpenedEvent.calendar.time}" type="both" timeStyle="short" />
                            </td>
                            <td>
                                ${applicationOpenedEvent.packageName}
                            </td>
                            <td>
                                ${applicationOpenedEvent.student}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
