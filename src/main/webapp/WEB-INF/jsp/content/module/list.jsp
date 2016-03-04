<content:title>
    <fmt:message key="modules" /> (age 7-10)
</content:title>

<content:section cssId="moduleListPage">
    <h4>Difficulty level: 1</h4>
    
    <div class="divider"></div>
    
    <div class="section row">
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel pink lighten-4 center">
                <i class="material-icons large">healing</i>
                <h4>Health</h4>
            </div>
        </a>
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel blue lighten-3 center">
                <i class="material-icons large">train</i>
                <h4>Travel</h4>
            </div>
        </a>
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel amber lighten-4 center">
                <i class="material-icons large">euro_symbol</i>
                <h4>Money</h4>
            </div>
        </a>
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel blue-grey lighten-3 center">
                <i class="material-icons large">grade</i>
                <h4>Space</h4>
            </div>
        </a>
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel orange center">
                <i class="material-icons large">restaurant</i>
                <h4>Food</h4>
            </div>
        </a>
        <a class="col s12 m6 l4 waves-effect waves-light black-text" href="<spring:url value='/content/module/activities' />">
            <div class="card-panel green lighten-2 center">
                <i class="material-icons large">pets</i>
                <h4>Animals</h4>
            </div>
        </a>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/module/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.module" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
