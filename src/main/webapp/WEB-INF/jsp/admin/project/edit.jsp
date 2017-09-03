<content:title>
    <fmt:message key="edit.project" />
</content:title>

<content:section cssId="projectCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="project">
            <tag:formErrors modelAttribute="project" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="name" cssErrorClass="error"><fmt:message key='name' /></form:label>
                    <form:input path="name" cssErrorClass="error" />
                </div>
            </div>
                
            <%-- TODO: add project managers --%>

            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
