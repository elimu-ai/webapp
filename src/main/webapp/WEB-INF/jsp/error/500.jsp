<content:title>
    HTTP 500 Internal Server Error
</content:title>

<content:section cssId="errorPage" cssClass="code500">
    <div class="col s12 m10 l8 offset-m1 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            An error occurred: 
            <hr />
            <code>${pageContext.exception}</code>
        </div>
    </div>
</content:section>
