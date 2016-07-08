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
    </div>
</content:section>
