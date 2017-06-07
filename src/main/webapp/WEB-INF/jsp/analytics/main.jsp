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
                    <span class="card-title"><i class="material-icons">devices</i> <fmt:message key="devices" /></span>
                    <p>List of devices that have registered with the server.</p>
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
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/application-opened-event/list' />"><fmt:message key="view.events" /></a>
                </div>
            </div>
        </div>
    </div>
</content:section>
