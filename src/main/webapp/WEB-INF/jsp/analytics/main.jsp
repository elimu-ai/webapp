<content:title>
    <fmt:message key="analytics" />
</content:title>

<content:section cssId="mainAnalyticsPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">devices</i> <fmt:message key="devices" /></span>
                    <p>List of devices that have registered with the server.</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/analytics/device/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
    </div>
</content:section>
