<content:title>
    Analytics
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="section row">
        <div class="col s12">
            <h5>Learning events</h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_format</i> Letter-sound correspondences</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/letter-sound-learning-event/list' />">View list (${letterSoundLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> Words</span>
                </div>
                <div class="card-action">
                    <a id="wordLearningEventsLink" href="<spring:url value='/analytics/word-learning-event/list' />">View list (${wordLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> Storybooks</span>
                </div>
                <div class="card-action">
                    <a id="storyBookLearningEventsLink" href="<spring:url value='/analytics/storybook-learning-event/list' />">View list (${storyBookLearningEventCount})</a>
                </div>
            </div>
        </div>

        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">movie</i> Videos</span>
                </div>
                <div class="card-action">
                    <a id="videoLearningEventsLink" href="<spring:url value='/analytics/video-learning-event/list' />">View list (${videoLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5>Assessment events</h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_format</i> Letters</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/letter-assessment-event/list' />">View list (${letterAssessmentEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> Words</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/word-assessment-event/list' />">View list (${wordAssessmentEventCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>
