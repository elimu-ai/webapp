<content:title>
    Words (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                var ctx = document.getElementById('chart');
                var data = {
                    labels: [
                        <c:forEach var="key" items="${wordFrequencyMap.keySet()}" varStatus="status">
                            <c:if test="${status.index < 100}">
                                "<c:out value="${key}" escapeXml="true" />",
                            </c:if>
                        </c:forEach>
                    ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="key" items="${wordFrequencyMap}" varStatus="status">
                                    <c:if test="${status.index < 100}">
                                        ${key.value},
                                    </c:if>
                                </c:forEach>
                            ],
                            label: 'Word frequency distribution (top 100)',
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
           href="<spring:url value='/content/word/list/words.csv' />">
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
        
        <c:if test="${not empty words}">
            <table class="bordered highlight">
                <thead>
                    <th>Frequency</th>
                    <th>Word</th>
                    <th>Word type</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="word" items="${words}">
                        <tr>
                            <td>
                                ${word.usageCount}
                                <div class="progress">
                                    <div class="determinate" style="width: ${word.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td>
                                <div style="float: right; text-align: right;">
                                    <label>word.getText()</label>
                                    <div style="font-size: 2em;">
                                        "${word.text}"
                                    </div>
                                </div>
                                
                                <label>word.toString()</label>
                                <div style="font-size: 2em;">
                                    <a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />">"${word}"</a>
                                </div>

                                <div id="letterSoundsContainer">
                                    <label>Sound-to-letter correspondences</label><br />
                                    <c:forEach var="letterSound" items="${word.letterSounds}">
                                        <div class="chip">
                                            <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />">
                                                / <c:forEach var="sound" items="${letterSound.sounds}">
                                                    ${sound.valueIpa}<c:out value=" " />
                                                </c:forEach> /<br />
                                                ↓<br />
                                                " <c:forEach var="letter" items="${letterSound.letters}">
                                                    ${letter.text}<c:out value=" " />
                                                </c:forEach> "
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </td>
                            <td>
                                ${word.wordType}
                                <div style="font-size: 2em;">
                                    <c:out value=" ${emojisByWordId[word.id]}" />
                                </div>
                            </td>
                            <td>
                                <p>#${word.revisionNumber}</p>
                                <p>
                                    <a href="<spring:url value='/content/word/edit/${word.id}#contribution-events' />" style="display: flex;">
                                        <span class="peerReviewStatusContainer" data-status="${word.peerReviewStatus}">
                                            Peer-review: <code>${word.peerReviewStatus}</code>
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
        <a id="createButton" href="<spring:url value='/content/word/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add word"><i class="material-icons">add</i></a>
    </div>
</content:section>
