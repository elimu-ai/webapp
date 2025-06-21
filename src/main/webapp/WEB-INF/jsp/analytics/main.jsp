<content:title>
    Analytics
</content:title>

<content:section cssId="mainAnalyticsPage">
    <h4><content:gettitle /></h4>

    <div class="row">
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">school</i> Students</span>
                </div>
                <div class="card-action">
                    <a id="studentsLink" href="<spring:url value='/analytics/students' />">View list (${studentCount})</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">biotech</i> Research Experiments</span>
                </div>
                <div class="card-action">
                    <a id="experimentsLink" href="<spring:url value='/analytics/research/experiments' />">View list (${researchExperimentCount})</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <table class="bordered highlight">
            <thead>
                <th>Data Type</th>
                <th>Total Count</th>
            </thead>
            <tbody>
                <tr>
                    <td>🎼 Letter-sound assessment events</td>
                    <td>${letterSoundAssessmentEventCount}</td>
                </tr>
                <tr>
                    <td>🎼 Letter-sound learning events</td>
                    <td>${letterSoundLearningEventCount}</td>
                </tr>

                <tr>
                    <td>🔤 Word assessment events</td>
                    <td>${wordAssessmentEventCount}</td>
                </tr>
                <tr>
                    <td>🔤 Word learning events</td>
                    <td>${wordLearningEventCount}</td>
                </tr>

                <tr>
                    <td>🔢 Number assessment events</td>
                    <td>${numberAssessmentEventCount}</td>
                </tr>
                <tr>
                    <td>🔢 Number learning events</td>
                    <td>${numberLearningEventCount}</td>
                </tr>

                <tr>
                    <td>📚 Storybook learning events</td>
                    <td>${storyBookLearningEventCount}</td>
                </tr>

                <tr>
                    <td>🎬 Video learning events</td>
                    <td>${videoLearningEventCount}</td>
                </tr>
            </tbody>
        </table>
    </div>
</content:section>
