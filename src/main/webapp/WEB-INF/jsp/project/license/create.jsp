<content:title>
    <fmt:message key="add.license" />
</content:title>

<content:section cssId="licenseCreatePage">
    <h4><content:gettitle /></h4>
    
    <div class="card-panel amber lighten-3">
        <b>Note:</b> An e-mail with the license details will be sent to the recipient. And a copy of the e-mail will be 
        sent to ${contributor.email}.
    </div>
    
    <div class="card-panel">
        <form:form modelAttribute="license">
            <tag:formErrors modelAttribute="license" />
            
            <form:hidden path="appCollection" value="${license.appCollection.id}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="licenseEmail" cssErrorClass="error"><fmt:message key='email' /> *</form:label>
                    <form:input path="licenseEmail" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12 disabled">
                    <form:label path="licenseNumber" cssErrorClass="error"><fmt:message key='license.number' /> *</form:label>
                    <form:input path="licenseNumber" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="firstName" cssErrorClass="error"><fmt:message key='first.name' /> *</form:label>
                    <form:input path="firstName" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="lastName" cssErrorClass="error"><fmt:message key='last.name' /> *</form:label>
                    <form:input path="lastName" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="organization" cssErrorClass="error"><fmt:message key='organization' /></form:label>
                    <form:input path="organization" cssErrorClass="error" />
                </div>
            </div>
            <br />
            
            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
