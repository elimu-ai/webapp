<content:title>
    <fmt:message key="analytics" />
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="section row">
        <div class="col s12">
            <h5><fmt:message key="learning.events" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> StoryBookLearningEvents</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/storybook-learning-event/list' />"><fmt:message key="view.list" /> (${storyBookLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> WordLearningEvents</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/word-learning-event/list' />"><fmt:message key="view.list" /> (${wordLearningEventCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>
