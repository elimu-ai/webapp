<content:title>
    WordLearningEvents (${fn:length(wordLearningEvents)})
</content:title>

<content:section cssId="wordLearningEventsPage">
    <div class="section row">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                const labels = [
                    <c:forEach var="month" items="${monthList}">'${month}',</c:forEach>
                ];
                const data = {
                    labels: labels,
                    datasets: [{
                        data: <c:out value="${eventCountList}" />,
                        label: 'Learning events',
                        backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                        borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                        tension: 0.5,
                        fill: true
                    }]
                };
                const config = {
                    type: 'line',
                    data: data,
                    options: {}
                };
                var ctx = document.getElementById('chart');
                new Chart(ctx, config);
            </script>
        </div>
    </div>
    
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/word-learning-event/list/word-learning-events.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportToCsvButton').click(function() {
                    console.info('#exportToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
    
        <table class="bordered highlight">
            <thead>
                <th><code>timestamp</code></th>
                <th><code>android_id</code></th>
                <th><code>package_name</code></th>
                <th><code>word_id</code></th>
                <th><code>word_text</code></th>
                <th><code>learning_event_type</code></th>
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
