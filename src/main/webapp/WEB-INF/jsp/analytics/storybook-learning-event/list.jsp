<content:title>
    StoryBookLearningEvents (${fn:length(storyBookLearningEvents)})
</content:title>

<content:section cssId="storyBookLearningEventsPage">
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
           href="<spring:url value='/analytics/storybook-learning-event/list/storybook-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
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
                <th><code>storybook_title</code></th>
                <th><code>learning_event_type</code></th>
            </thead>
            <tbody>
                <c:forEach var="storyBookLearningEvent" items="${storyBookLearningEvents}">
                    <tr class="storyBookLearningEvent">
                        <td>
                            <fmt:formatDate value="${storyBookLearningEvent.timestamp.time}" pattern="yyyy-MM-dd" />
                        </td>   
                        <td>
                            <code>${storyBookLearningEvent.androidId}</code>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty storyBookLearningEvent.application}">
                                    <a href="<spring:url value='/admin/application/edit/${storyBookLearningEvent.application.id}' />">
                                        <code>${storyBookLearningEvent.packageName}</code>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <code>${storyBookLearningEvent.packageName}</code>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty storyBookLearningEvent.storyBook}">
                                    "<a href="<spring:url value='/content/storybook/edit/${storyBookLearningEvent.storyBook.id}' />">${storyBookLearningEvent.storyBookTitle}</a>"
                                </c:when>
                                <c:otherwise>
                                    "${storyBookLearningEvent.storyBookTitle}"
                                </c:otherwise>
                            </c:choose>
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
