<content:title>
    StoryBookLearningEvents (${fn:length(storyBookLearningEvents)})
</content:title>

<content:section cssId="storyBookLearningEventsPage">
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
           href="<spring:url value='/analytics/storybook-learning-event/list/storybook-learning-events.csv' />">
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
                <th>time</th>
                <th>android_id</th>
                <th>package_name</th>
                <th>storybook_id</th>
                <th>storybook_title</th>
                <th>learning_event_type</th>
            </thead>
            <tbody>
                <c:forEach var="storyBookLearningEvent" items="${storyBookLearningEvents}">
                    <tr class="storyBookLearningEvent">
                        <td>
                            <fmt:formatDate value="${storyBookLearningEvent.time.time}" pattern="yyyy-MM-dd" />
                        </td>   
                        <td>
                            ${storyBookLearningEvent.androidId}
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty storyBookLearningEvent.application}">
                                    <a href="<spring:url value='/admin/application/edit/${storyBookLearningEvent.application.id}' />">
                                        ${storyBookLearningEvent.packageName}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    ${storyBookLearningEvent.packageName}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            ${storyBookLearningEvent.storyBook.id}
                        </td>
                        <td>
                            "<a href="<spring:url value='/content/storybook/edit/${storyBookLearningEvent.storyBook.id}' />"><c:out value="${storyBookLearningEvent.storyBook.title}" /></a>"
                        </td>
                        <td>
                            ${storyBookLearningEvent.learningEventType}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
