<content:title>
    🎓 Student ID ${student.id}
</content:title>

<content:section cssId="studentPage">
    <h4><content:gettitle /></h4>
    <h5 style="margin-bottom: 1em;"><code>${student.androidId}</code></h5>
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
    
    <div class="card-panel">
        <h5>🔤 Word learning events (${fn:length(wordLearningEvents)})</h5>
        
        <canvas id="wordChart"></canvas>
        <script>
            const wordLabels = [
                <c:forEach var="month" items="${wordMonthList}">'${month}',</c:forEach>
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
    </div>

    <div class="card-panel">
        <h5>📚 Storybook learning events</h5>
        
        <canvas id="storyBookChart"></canvas>
        <script>
            const storyBookLabels = [
                <c:forEach var="month" items="${storyBookMonthList}">'${month}',</c:forEach>
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
    </div>

    <div class="card-panel">
        <h5>🎬 Video learning events</h5>
        
        <canvas id="videoChart"></canvas>
        <script>
            const videoLabels = [
                <c:forEach var="month" items="${videoMonthList}">'${month}',</c:forEach>
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
    </div>
</content:section>
