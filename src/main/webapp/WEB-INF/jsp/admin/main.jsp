<content:title>
    <fmt:message key="administration" />
</content:title>

<content:section cssId="mainAdminPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
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
