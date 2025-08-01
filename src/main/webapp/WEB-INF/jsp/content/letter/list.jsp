<content:title>
    Letters (${fn:length(letters)})
</content:title>

<content:section cssId="letterListPage">
    <div class="section row">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                var ctx = document.getElementById('chart');
                var data = {
                    labels: [
                        <c:forEach var="key" items="${letterFrequencyMap.keySet()}">
                            '<c:out value="${key}" escapeXml="true" />',
                        </c:forEach>
                    ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="key" items="${letterFrequencyMap}">
                                    ${key.value},
                                </c:forEach>
                            ],
                            label: 'Letter frequency distribution',
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
           href="<spring:url value='/content/letter/list/letters.csv' />">
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
            To add new content, click the button below. <span style="position: absolute; transform: rotate(-33deg);">👇🏽</span>
        </p>
    
        <c:if test="${not empty letters}">
            <table class="bordered highlight">
                <thead>
                    <th>Usage count</th>
                    <th>Letter</th>
                    <th>Diacritic</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="letter" items="${letters}">
                        <tr class="letter">
                            <td>
                                ${letter.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${letter.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${letter.id}"></a>
                                <a class="editLink" href="<spring:url value='/content/letter/edit/${letter.id}' />">"<c:out value='${letter.text}' />"</a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${letter.diacritic}">
                                        Yes
                                    </c:when>
                                    <c:otherwise>
                                        No
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <p>#${letter.revisionNumber}</p>
                                <p>
                                    <a href="<spring:url value='/content/letter/edit/${letter.id}#contribution-events' />" style="display: flex;">
                                        <span class="peerReviewStatusContainer" data-status="${letter.peerReviewStatus}">
                                            Peer-review: <code>${letter.peerReviewStatus}</code>
                                        </span>
                                    </a>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add letter"><i class="material-icons">add</i></a>
    </div>
</content:section>
