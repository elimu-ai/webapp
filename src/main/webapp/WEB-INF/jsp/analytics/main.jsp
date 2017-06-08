<content:title>
    <fmt:message key="analytics" />
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">school</i> <fmt:message key="students" /></span>
                    <p>List of students that have been assigned an ID on one or more devices.</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/student/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">App usage</i> <fmt:message key="devices" /></span>
                    <p>List of devices that have registered with the server.</p>
                    <div id="chartPreferences" class="ct-chart ct-perfect-fourth"></div>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/device/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">timeline</i> <fmt:message key="application.opened.events" /></span>
                    <p>Diagrams displaying applications opened on the devices.</p>

                            <div class="content">
                                <div id="chartActivity" class="ct-chart"></div>

                                <div class="footer">
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> Tanzania
                                        <i class="fa fa-circle text-danger"></i> Kenya
                                    </div>
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-check"></i> Data information certified
                                    </div>
                                </div>
                            </div>


                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/application-opened-event/list' />"><fmt:message key="view.events" /></a>
                </div>
            </div>
        </div>
    </div>
</content:section>
