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
                    <span class="card-title"><i class="material-icons">text_format</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/letter-learning-event/list' />"><fmt:message key="view.list" /> (${letterLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/word-learning-event/list' />"><fmt:message key="view.list" /> (${wordLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> <fmt:message key="storybooks" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/storybook-learning-event/list' />"><fmt:message key="view.list" /> (${storyBookLearningEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5><fmt:message key="assessment.events" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_format</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/letter-assessment-event/list' />"><fmt:message key="view.list" /> (${letterAssessmentEventCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/word-assessment-event/list' />"><fmt:message key="view.list" /> (${wordAssessmentEventCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>
