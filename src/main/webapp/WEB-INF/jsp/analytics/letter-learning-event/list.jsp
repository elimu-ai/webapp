<content:title>
    LetterLearningEvents (${fn:length(letterLearningEvents)})
</content:title>

<content:section cssId="letterLearningEventsPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/letter-learning-event/list/letter-learning-events.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
    
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="time" /></th>
                <th>Android ID</th>
                <th><fmt:message key="application" /></th>
                <th><fmt:message key="letter" /></th>
                <th><fmt:message key="letter.text" /></th>
                <th><fmt:message key="learning.event.type" /></th>
            </thead>
            <tbody>
                <c:forEach var="letterLearningEvent" items="${letterLearningEvents}">
                    <tr class="letterLearningEvent">
                        <td>
                            <fmt:formatDate value="${letterLearningEvent.time.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            ${letterLearningEvent.androidId}
                        </td>
                        <td>
                            <a href="<spring:url value='/admin/application/edit/${letterLearningEvent.application.id}' />">
                                ${letterLearningEvent.application.packageName}
                            </a>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/letter/edit/${letterLearningEvent.letter.id}' />">
                                ${letterLearningEvent.letter.text}
                            </a>
                        </td>
                        <td>
                            "<c:out value='${letterLearningEvent.letterText}' />"
                        </td>
                        <td>
                            ${letterLearningEvent.learningEventType}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
