<content:title>
    🎓 Student #${student.id}
</content:title>

<content:section cssId="studentPage">
    <div class="col s7 m5 l4">
        <div class="card contributor">
            <div class="card-image">
                <img src="<spring:url value='/static/img/student-${applicationScope.configProperties["content.language"]}.png' />" />
                <span class="card-title">
                    <content:gettitle />
                </span>
            </div>
            <div class="card-content">
                <p class="grey-text">Student ID</p>
                #${student.id}<br />

                <p class="grey-text" style="margin-top: 1em;">Android ID</p>
                <code>${student.androidId}</code>
            </div>
        </div>
    </div>
    <div class="col s5 m7 l8">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart"></canvas>
            <script>
                var ctx = document.getElementById('chart');
                var data = {
                    labels: ['Letter-sounds', 'Words', 'Numbers', 'Storybooks', 'Videos'],
                    datasets: [
                        {
                            data: [${fn:length(letterSoundLearningEvents)}, ${fn:length(wordLearningEvents)}, ${fn:length(numberLearningEvents)}, ${fn:length(storyBookLearningEvents)}, ${fn:length(videoLearningEvents)}],
                            label: 'Learning events',
                            backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                            tension: 0.5
                        },
                        {
                            data: [${fn:length(lettersoundAssessmentEvents)}, ${fn:length(wordAssessmentEvents)}, ${fn:length(numberAssessmentEvents)}, ${fn:length(storyBookAssessmentEvents)}, ${fn:length(videoAssessmentEvents)}],
                            label: 'Assessment events',
                            backgroundColor: 'rgba(77,182,172, 0.5)', // #4db6ac teal lighten-2
                            tension: 0.5
                        }
                    ]
                };
                var options = {};
                new Chart(ctx, {
                    type: 'radar',
                    data: data,
                    options: options
                });
            </script>
        </div>
    </div>
    <div style="clear: both;"></div>

    <div class="col s6">
        <h5 style="margin-top: 1em;">Literacy skills</h5>
        <div class="card-panel">
            <table class="bordered highlight">
                <thead>
                    <th>Skill</th>
                    <th></th>
                    <th>Mastery</th>
                </thead>
                <tbody>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <tr>
                            <td>
                                ${literacySkill}
                            </td>
                            <td>
                                0%
                            </td>
                            <td>
                                <div class="progress">
                                    <div class="determinate" style="width: 0%"></div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col s6">
        <h5 style="margin-top: 1em;">Numeracy skills</h5>
        <div class="card-panel">
            <table class="bordered highlight">
                <thead>
                    <th>Skill</th>
                    <th></th>
                    <th>Mastery</th>
                </thead>
                <tbody>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <tr>
                            <td>
                                ${numeracySkill}
                            </td>
                            <td>
                                0%
                            </td>
                            <td>
                                <div class="progress">
                                    <div class="determinate" style="width: 0%"></div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div style="clear: both;"></div>
    
    <h5 style="margin-top: 1em;">🎼 Letter-sounds</h5>
    <div class="card-panel">
        <h5>Letter identification speed (correct letter-sounds per minute)</h5>
        <canvas id="letterIdentificationSpeedChart"></canvas>
        <script>
            const letterIdentificationSpeedLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const letterIdentificationSpeedData = {
                labels: letterIdentificationSpeedLabels,
                datasets: [{
                    data: <c:out value="${letterIdentificationSpeedAvgList}" />,
                    label: 'clspm',
                    backgroundColor: 'rgba(100,181,246, 0.8)', // #64b5f6 blue lighten-2
                    borderColor: 'rgba(100,181,246, 0.8)', // #64b5f6 blue lighten-2
                    borderWidth: 4,
                    tension: 0.5,
                    fill: true
                },{
                    data: Array(letterIdentificationSpeedLabels.length).fill(10.0),
                    label: 'LEVEL1',
                    backgroundColor: 'rgba(149,117,205, 0.1)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.1)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(letterIdentificationSpeedLabels.length).fill(20.0),
                    label: 'LEVEL2',
                    backgroundColor: 'rgba(149,117,205, 0.2)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.2)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(letterIdentificationSpeedLabels.length).fill(40.0),
                    label: 'LEVEL3',
                    backgroundColor: 'rgba(149,117,205, 0.3)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.3)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(letterIdentificationSpeedLabels.length).fill(100.0),
                    label: 'LEVEL4',
                    backgroundColor: 'rgba(149,117,205, 0.4)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.4)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                }]
            };
            const letterIdentificationSpeedConfig = {
                type: 'line',
                data: letterIdentificationSpeedData,
                options: {}
            };
            var letterIdentificationSpeedCtx = document.getElementById('letterIdentificationSpeedChart');
            new Chart(letterIdentificationSpeedCtx, letterIdentificationSpeedConfig);
        </script>

        <div class="divider" style="margin: 2em 0;"></div>

        <a id="exportLetterSoundAssessmentEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/letter-sound-assessment-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportLetterSoundAssessmentEventsToCsvButton').click(function() {
                    console.info('#exportLetterSoundAssessmentEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
        <h5>Letter-sound assessment events (${fn:length(letterSoundAssessmentEvents)})</h5>
        ...

        <div class="divider" style="margin: 2em 0;"></div>

        <a id="exportLetterSoundLearningEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/letter-sound-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportLetterSoundLearningEventsToCsvButton').click(function() {
                    console.info('#exportLetterSoundLearningEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
        <h5>Letter-sound learning events (${fn:length(letterSoundLearningEvents)})</h5>
        <canvas id="letterSoundLearningEventChart"></canvas>
        <script>
            const letterSoundLearningEventLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const letterSoundLearningEventData = {
                labels: letterSoundLearningEventLabels,
                datasets: [{
                    data: <c:out value="${letterSoundLearningEventCountList}" />,
                    label: 'Letter-sound learning events',
                    backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const letterSoundLearningEventConfig = {
                type: 'line',
                data: letterSoundLearningEventData,
                options: {}
            };
            var letterSoundLearningEventCtx = document.getElementById('letterSoundLearningEventChart');
            new Chart(letterSoundLearningEventCtx, letterSoundLearningEventConfig);
        </script>
        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>letter_sound_id</th>
                <th>ℹ️</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="letterSoundLearningEvent" value="${letterSoundLearningEvents[fn:length(letterSoundLearningEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${letterSoundLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${letterSoundLearningEvent.packageName}</code>
                        </td>
                        <td>
                            ${letterSoundLearningEvent.letterSoundId}
                        </td>
                        <td>
                            <code>${letterSoundLearningEvent.additionalData}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div style="clear: both;"></div>
    
    <h5 style="margin-top: 1em;">🔤 Words</h5>
    <div class="card-panel">
        <h5>Reading speed (correct words per minute)</h5>
        <canvas id="readingSpeedChart"></canvas>
        <script>
            const readingSpeedLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const readingSpeedData = {
                labels: readingSpeedLabels,
                datasets: [{
                    data: <c:out value="${readingSpeedAvgList_kukariri}" />,
                    label: 'cwpm (kukariri)',
                    backgroundColor: 'rgba(100,181,246, 0.8)', // #64b5f6 blue lighten-2
                    borderColor: 'rgba(100,181,246, 0.8)', // #64b5f6 blue lighten-2
                    borderWidth: 4,
                    tension: 0.5
                },{
                    data: <c:out value="${readingSpeedAvgList_vitabu}" />,
                    label: 'cwpm (vitabu)',
                    backgroundColor: 'rgba(77,182,172, 0.8)', // #4db6ac teal lighten-2
                    borderColor: 'rgba(77,182,172, 0.8)', // #4db6ac teal lighten-2
                    borderWidth: 4,
                    tension: 0.5
                },{
                    data: Array(readingSpeedLabels.length).fill(5.0),
                    label: 'LEVEL1',
                    backgroundColor: 'rgba(149,117,205, 0.1)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.1)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(readingSpeedLabels.length).fill(10.0),
                    label: 'LEVEL2',
                    backgroundColor: 'rgba(149,117,205, 0.2)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.2)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(readingSpeedLabels.length).fill(20.0),
                    label: 'LEVEL3',
                    backgroundColor: 'rgba(149,117,205, 0.3)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.3)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                },{
                    data: Array(readingSpeedLabels.length).fill(50.0),
                    label: 'LEVEL4',
                    backgroundColor: 'rgba(149,117,205, 0.4)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.4)', // #9575cd deep-purple lighten-2
                    tension: 0.5
                }]
            };
            const readingSpeedConfig = {
                type: 'line',
                data: readingSpeedData,
                options: {}
            };
            var readingSpeedCtx = document.getElementById('readingSpeedChart');
            new Chart(readingSpeedCtx, readingSpeedConfig);
        </script>

        <div class="divider" style="margin: 2em 0;"></div>

        <a id="exportWordAssessmentEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/word-assessment-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportWordAssessmentEventsToCsvButton').click(function() {
                    console.info('#exportWordAssessmentEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
        <h5>Word assessment events (${fn:length(wordAssessmentEvents)})</h5>
        <canvas id="wordAssessmentEventChart"></canvas>
        <script>
            const wordAssessmentEventLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const wordAssessmentEventData = {
                labels: wordAssessmentEventLabels,
                datasets: [{
                    data: <c:out value="${wordAssessmentEventCorrectCountList}" />,
                    label: 'Correct',
                    backgroundColor: 'rgba(100,181,246, 0.5)', // #64b5f6 blue lighten-2
                    borderColor: 'rgba(100,181,246, 0.5)', // #64b5f6 blue lighten-2
                    tension: 0.5,
                    fill: true
                },{
                    data: <c:out value="${wordAssessmentEventIncorrectCountList}" />,
                    label: 'Incorrect',
                    backgroundColor: 'rgba(245,124,0, 0.5)', // #f57c00 orange darken-2
                    borderColor: 'rgba(245,124,0, 0.5)', // #f57c00 orange darken-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const wordAssessmentEventConfig = {
                type: 'line',
                data: wordAssessmentEventData,
                options: {
                    scales: {
                        y: {
                            stacked: true
                        }
                    }
                }
            };
            var wordAssessmentEventCtx = document.getElementById('wordAssessmentEventChart');
            new Chart(wordAssessmentEventCtx, wordAssessmentEventConfig);
        </script>
        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>mastery_score</th>
                <th>time_spent_ms</th>
                <th>word_text</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="wordAssessmentEvent" value="${wordAssessmentEvents[fn:length(wordAssessmentEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${wordAssessmentEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${wordAssessmentEvent.packageName}</code>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${wordAssessmentEvent.masteryScore < 0.5}">
                                    <span data-badge-caption="${wordAssessmentEvent.masteryScore}" class="new badge orange darken-2"></span>
                                </c:when>
                                <c:otherwise>
                                    <span data-badge-caption="${wordAssessmentEvent.masteryScore}" class="new badge blue lighten-2"></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <fmt:formatNumber value="${wordAssessmentEvent.timeSpentMs}" /> ms
                        </td>
                        <td>
                            "${wordAssessmentEvent.wordText}"
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="divider" style="margin: 2em 0;"></div>

        <a id="exportWordLearningEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/word-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportWordLearningEventsToCsvButton').click(function() {
                    console.info('#exportWordLearningEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
        <h5>Word learning events (${fn:length(wordLearningEvents)})</h5>
        <canvas id="wordChart"></canvas>
        <script>
            const wordLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const wordData = {
                labels: wordLabels,
                datasets: [{
                    data: <c:out value="${wordEventCountList}" />,
                    label: 'Word learning events',
                    backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const wordConfig = {
                type: 'line',
                data: wordData,
                options: {}
            };
            var wordCtx = document.getElementById('wordChart');
            new Chart(wordCtx, wordConfig);
        </script>
        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>word_text</th>
                <th>word_id</th>
                <th>ℹ️</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="wordLearningEvent" value="${wordLearningEvents[fn:length(wordLearningEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${wordLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${wordLearningEvent.packageName}</code>
                        </td>
                        <td>
                            "${wordLearningEvent.wordText}"
                        </td>
                        <td>
                            <%--
                            ${wordLearningEvent.wordId}
                            TODO: https://github.com/elimu-ai/webapp/issues/2113
                            --%>
                        </td>
                        <td>
                            <code>${wordLearningEvent.additionalData}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div style="clear: both;"></div>

    <h5 style="margin-top: 1em;">🔢 Numbers</h5>
    <div class="card-panel">
        <h5>Number assessment events (${fn:length(numberAssessmentEvents)})</h5>
        ...

        <div class="divider" style="margin: 2em 0;"></div>

        <a id="exportNumberLearningEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/number-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportNumberLearningEventsToCsvButton').click(function() {
                    console.info('#exportNumberLearningEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>
        <h5>Number learning events (${fn:length(numberLearningEvents)})</h5>
        <canvas id="numberChart"></canvas>
        <script>
            const numberLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const numberData = {
                labels: numberLabels,
                datasets: [{
                    data: <c:out value="${numberEventCountList}" />,
                    label: 'Number learning events',
                    backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const numberConfig = {
                type: 'line',
                data: numberData,
                options: {}
            };
            var numberCtx = document.getElementById('numberChart');
            new Chart(numberCtx, numberConfig);
        </script>
        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>number_value</th>
                <th>number_symbol</th>
                <th>number_id</th>
                <th>ℹ️</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="numberLearningEvent" value="${numberLearningEvents[fn:length(numberLearningEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${numberLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${numberLearningEvent.packageName}</code>
                        </td>
                        <td>
                            ${numberLearningEvent.numberValue}
                        </td>
                        <td>
                            "${numberLearningEvent.numberSymbol}"
                        </td>
                        <td>
                            ${numberLearningEvent.numberId}
                        </td>
                        <td>
                            <code>${numberLearningEvent.additionalData}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div style="clear: both;"></div>

    <h5 style="margin-top: 1em;">📚 Storybooks</h5>
    <div class="card-panel">
        <a id="exportStoryBookLearningEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/storybook-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportStoryBookLearningEventsToCsvButton').click(function() {
                    console.info('#exportStoryBookLearningEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>

        <h5>Storybook learning events (${fn:length(storyBookLearningEvents)})</h5>
        <canvas id="storyBookChart"></canvas>
        <script>
            const storyBookLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const storyBookData = {
                labels: storyBookLabels,
                datasets: [{
                    data: <c:out value="${storyBookEventCountList}" />,
                    label: 'Storybook learning events',
                    backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const storyBookConfig = {
                type: 'line',
                data: storyBookData,
                options: {}
            };
            var storyBookCtx = document.getElementById('storyBookChart');
            new Chart(storyBookCtx, storyBookConfig);
        </script>

        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>storybook_title</th>
                <th>storybook_id</th>
                <th>ℹ️</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="storyBookLearningEvent" value="${storyBookLearningEvents[fn:length(storyBookLearningEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${storyBookLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${storyBookLearningEvent.packageName}</code>
                        </td>
                        <td>
                            "${storyBookLearningEvent.storyBookTitle}"
                        </td>
                        <td>
                            ${storyBookLearningEvent.storyBookId}
                        </td>
                        <td>
                            <code>${storyBookLearningEvent.additionalData}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div style="clear: both;"></div>

    <h5 style="margin-top: 1em;">🎬 Videos</h5>
    <div class="card-panel">
        <a id="exportVideoLearningEventsToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/analytics/students/${student.id}/video-learning-events.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportVideoLearningEventsToCsvButton').click(function() {
                    console.info('#exportVideoLearningEventsToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>

        <h5>Video learning events (${fn:length(videoLearningEvents)})</h5>
        <canvas id="videoChart"></canvas>
        <script>
            const videoLabels = [
                <c:forEach var="week" items="${weekList}">'${week}',</c:forEach>
            ];
            const videoData = {
                labels: videoLabels,
                datasets: [{
                    data: <c:out value="${videoEventCountList}" />,
                    label: 'Video learning events',
                    backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    borderColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                    tension: 0.5,
                    fill: true
                }]
            };
            const videoConfig = {
                type: 'line',
                data: videoData,
                options: {}
            };
            var videoCtx = document.getElementById('videoChart');
            new Chart(videoCtx, videoConfig);
        </script>

        <table class="bordered highlight">
            <thead>
                <th>timestamp</th>
                <th>package_name</th>
                <th>video_title</th>
                <th>video_id</th>
                <th>ℹ️</th>
            </thead>
            <tbody>
                <c:forEach var="i" begin="0" end="4">
                    <c:set var="videoLearningEvent" value="${videoLearningEvents[fn:length(videoLearningEvents) - 1 - i]}" />
                    <tr>
                        <td>
                            <fmt:formatDate value="${videoLearningEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td>
                            <code>${videoLearningEvent.packageName}</code>
                        </td>
                        <td>
                            "${videoLearningEvent.videoTitle}"
                        </td>
                        <td>
                            ${videoLearningEvent.videoId}
                        </td>
                        <td>
                            <code>${videoLearningEvent.additionalData}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
