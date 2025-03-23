<content:title>
    VideoLearningEvents (${videoLearningEventCount})
</content:title>

<content:section cssId="videoLearningEventsPage">
    <div class="section row">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                const labels = [
                    <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
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
           href="<spring:url value='/analytics/video-learning-event/list/video-learning-events.csv' />">
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
                <th><code>video_title</code></th>
                <th><code>learning_event_type</code></th>
            </thead>
            <tbody>
                <c:forEach var="videoLearningEvent" items="${videoLearningEvents}">
                    <tr class="videoLearningEvent">
                        <td>
                            <fmt:formatDate value="${videoLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            ${videoLearningEvent.androidId}
                        </td>
                        <td>
                            ${videoLearningEvent.packageName}
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty videoLearningEvent.video}">
                                    "<a href="<spring:url value='/content/video/edit/${videoLearningEvent.video.id}' />">${videoLearningEvent.video.title}</a>"
                                </c:when>
                                <c:otherwise>
                                    "${videoLearningEvent.videoTitle}"
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            ${videoLearningEvent.learningEventType}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
