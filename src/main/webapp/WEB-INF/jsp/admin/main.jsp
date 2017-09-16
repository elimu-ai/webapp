<content:title>
    <fmt:message key="administration" />
</content:title>

<content:section cssId="mainAdminPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">android</i> <fmt:message key="applications" /></span>
                    <p>Collection of applications to be regularly synchronized with every device.</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/admin/application/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">group</i> <fmt:message key="contributors" /></span>
                    <p>List of registered Contributors</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/admin/contributor/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
                
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">timeline</i> <fmt:message key="sign.on.events" /></span>
                    <p>List of last Contributor sign-ons</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/admin/sign-on-event/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">list</i> <fmt:message key="projects" /></span>
                    <p>List of custom projects</p>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/admin/project/list' />"><fmt:message key="view.list" /></a>
                </div>
            </div>
        </div>
    </div>
</content:section>
