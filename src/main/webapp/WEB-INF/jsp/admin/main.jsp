<content:title>
    Administration
</content:title>

<content:section cssId="mainAdminPage">
    <div class="row">
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">android</i> Applications</span>
                    <p>Collection of applications to be regularly synchronized with every device.</p>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${applicationCount/(4+24+12) * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/admin/application/list' />">View list (${applicationCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>
