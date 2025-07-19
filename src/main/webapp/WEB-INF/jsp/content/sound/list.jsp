<content:title>
    Sounds (${fn:length(sounds)})
</content:title>

<content:section cssId="soundListPage">
    <div class="section row">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                var ctx = document.getElementById('chart');
                var data = {
                    labels: [
                        <c:forEach var="sound" items="${sounds}">
                            '/${sound.valueIpa}/',
                        </c:forEach>
                    ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="sound" items="${sounds}">
                                    '${sound.usageCount}',
                                </c:forEach>
                            ],
                            label: 'Sound distribution',
                            backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                        }
                    ]
                };
                var options = {};
                new Chart(ctx, {
                    type: 'bar',
                    data: data,
                    options: options
                });
            </script>
        </div>

        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/sound/list/sounds.csv' />">
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
        
        <p>
            To add new content, click the button below.
        </p>
    
        <c:if test="${not empty sounds}">
            <table class="bordered highlight">
                <thead>
                    <th>Usage count</th>
                    <th>IPA value</th>
                    <th>X-SAMPA value</th>
                    <th>Sound type</th>
                    <th>Audio</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="sound" items="${sounds}">
                        <tr>
                            <td>
                                ${sound.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${sound.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${sound.id}"></a>
                                <a class="editLink" href="<spring:url value='/content/sound/edit/${sound.id}' />">/${sound.valueIpa}/</a>
                            </td>
                            <td>
                                ${sound.valueSampa}
                            </td>
                            <td>
                                ${sound.soundType}
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/static/sound/sampa_${sound.valueSampa}.wav' />" />
                                </audio>
                            </td>
                            <td>
                                <p>#${sound.revisionNumber}</p>
                                <p>
                                    <c:choose>
                                        <c:when test="${sound.peerReviewStatus == 'APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                        </c:when>
                                        <c:when test="${sound.peerReviewStatus == 'NOT_APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="peerReviewStatusColor" value="" />
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="chip ${peerReviewStatusColor}">
                                        <a href="<spring:url value='/content/sound/edit/${sound.id}#contribution-events' />">
                                            ${sound.peerReviewStatus}
                                        </a>
                                    </span>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/sound/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add sound"><i class="material-icons">add</i></a>
    </div>
</content:section>
