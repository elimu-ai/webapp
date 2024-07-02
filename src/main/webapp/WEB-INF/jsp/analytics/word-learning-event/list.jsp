<content:title>
    WordLearningEvents (${fn:length(wordLearningEvents)})
</content:title>

<content:section cssId="wordLearningEventsPage">
    <div class="section row">
        <div class="card-panel">
            <script src="<spring:url value='/static/js/chart.bundle.min-2.8.0.js' />"></script>
            <link rel="stylesheet" href="<spring:url value='/static/css/chart.min-2.8.0.css' />" />
            <canvas id="myChart" width="400" height="100"></canvas>
            <script>
                const labels = [
                    'January',
                    'February',
                    'March',
                    'April',
                    'May',
                    'June',
                ];
                const data = {
                    labels: labels,
                    datasets: [{
                        label: 'My First dataset',
                        backgroundColor: 'rgba(149,117,205, 0.5)',
                        borderColor: 'rgba(149,117,205, 0.5)',
                        data: [0, 10, 5, 2, 20, 30, 45],
                    }]
                };
                const config = {
                    type: 'line',
                    data: data,
                    options: {}
                };
                var ctx = document.getElementById('myChart');
                var myRadarChart = new Chart(ctx, config);
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
