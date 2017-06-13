<content:title>
    <fmt:message key="analytics" />
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="row">

        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">timeline</i> <fmt:message key="application.opened.events" /></span>
                    <p>Applications opened events.</p>
                        <div id="application-open-event" class="echart"></div>
                </div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/student/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>

        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">devices</i> <fmt:message key="devices" /></span>
                    <p>Average number of active devices every day.</p>
                </div>
                <div id="chart-bar-device" class="echart"></div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/device/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>

        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">school</i> <fmt:message key="students" /></span>
                    <p>Average number of students active every day.</p>
                        <div id="chart-bar-student" class="echart"></div>
                </div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/student/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>

        
        
        
        







    </div>
</content:section>
