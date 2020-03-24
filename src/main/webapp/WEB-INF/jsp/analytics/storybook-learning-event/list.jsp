<content:title>
    StoryBookLearningEvents (${fn:length(storyBookLearningEvents)})
</content:title>

<content:section cssId="storyBookLearningEventsPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/storybook-learning-event/list/storybook-learning-events.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
    
        <c:if test="${not empty storyBookLearningEvents}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="time" /></th>
                    <th>Android ID</th>
                    <th><fmt:message key="application" /></th>
                    <th><fmt:message key="storybook" /></th>
                    <th><fmt:message key="learning.event.type" /></th>
                </thead>
                <tbody>
                    <c:forEach var="storyBookLearningEvent" items="${storyBookLearningEvents}">
                        <tr class="storyBookLearningEvent">
                            <td>
                                <fmt:formatDate value="${storyBookLearningEvent.time.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                                ${storyBookLearningEvent.androidId}
                            </td>
                            <td>
                                <a href="<spring:url value='/admin/application/edit/${storyBookLearningEvent.application.id}' />">
                                    ${storyBookLearningEvent.application.packageName}
                                </a>
                            </td>
                            <td>
                                <a href="<spring:url value='/content/storybook/edit/${storyBookLearningEvent.storyBook.id}' />">
                                    ${storyBookLearningEvent.storyBook.title}
                                </a>
                            </td>
                            <td>
                                ${storyBookLearningEvent.learningEventType}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</content:section>
