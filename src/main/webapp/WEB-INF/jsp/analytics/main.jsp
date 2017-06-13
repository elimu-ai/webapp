<content:title>
    <fmt:message key="analytics" />
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">school</i> <fmt:message key="students" /></span>
                    <p>Number of students and device used.</p>
                        <div id="chart-line-student" class="echart"></div>
                </div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/student/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>

        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">timeline</i> <fmt:message key="application.opened.events" /></span>
                    <p>Diagrams displaying applications opened on different location every months.</p>

                            <div class="content">
                                <div id="chart-bar-student" class="echart"></div>
                            </div>


                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/application-opened-event/list' />"><fmt:message key="view.events" /></a>
                </div>
            </div>
        </div>  
        
        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">App usage</i> <fmt:message key="devices" /></span>
                    <p>List of apps that have used by student every week.</p>
                </div>
                <div id="chartapps" class="echart"></div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/device/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
        

        <div class="col s12 m6">
            <div class="card darken-1">
                <div class="card-content black-text">
                    <span class="card-title"><i class="material-icons">school</i> <fmt:message key="students" /></span>
                    <p>Performance of student across applications.</p>
                        <div id="chart-line-student-perf" class="echart"></div>
                </div>
                
                <div class="card-action">
                    <a href="<spring:url value='/analytics/student/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>


    </div>
</content:section>
