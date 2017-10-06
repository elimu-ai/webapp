<content:title>
    <fmt:message key="add.app.group" />
</content:title>

<content:section cssId="appGroupCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="appGroup">
            <tag:formErrors modelAttribute="appGroup" />

            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
