<content:title>
    WordLearningEvents (${fn:length(wordLearningEvents)})
</content:title>

<content:section cssId="wordLearningEventsPage">
    <div class="section row">
        <a class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/word-learning-event/list/word-learning-events.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
    
        <table class="bordered highlight">
            <thead>
                <th><fmt:message key="time" /></th>
                <th>Android ID</th>
                <th><fmt:message key="application" /></th>
                <th><fmt:message key="word" /></th>
                <th><fmt:message key="word.text" /></th>
                <th><fmt:message key="learning.event.type" /></th>
            </thead>
            <tbody>
                <c:forEach var="wordLearningEvent" items="${wordLearningEvents}">
                    <tr class="wordLearningEvent">
                        <td>
                            <fmt:formatDate value="${wordLearningEvent.time.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            ${wordLearningEvent.androidId}
                        </td>
                        <td>
                            <a href="<spring:url value='/admin/application/edit/${wordLearningEvent.application.id}' />">
                                ${wordLearningEvent.application.packageName}
                            </a>
                        </td>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${wordLearningEvent.word.id}' />">
                                ${wordLearningEvent.word.text}
                            </a>
                        </td>
                        <td>
                            "<c:out value='${wordLearningEvent.wordText}' />"
                        </td>
                        <td>
                            ${wordLearningEvent.learningEventType}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
