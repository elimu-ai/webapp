<content:title>
    <fmt:message key="add.application" />
</content:title>

<content:section cssId="applicationCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="application">
            <tag:formErrors modelAttribute="application" />

            <div class="row">
                <form:hidden path="locale" value="${application.locale}" />
                <div class="input-field col s6">
                    <form:label path="packageName" cssErrorClass="error"><fmt:message key='package.name' /></form:label>
                    <form:input path="packageName" cssErrorClass="error" placeholder="org.literacyapp" />
                </div>
            </div>
                
            <%-- TODO: literacySkills --%>
            
            <%-- TODO: numeracySkills --%>

            <button class="btn waves-effect waves-light" type="submit" name="action">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
