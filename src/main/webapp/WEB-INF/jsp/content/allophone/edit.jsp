<content:title>
    <fmt:message key="edit.allophone" />
</content:title>

<content:section cssId="allophoneEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="allophone">
            <tag:formErrors modelAttribute="allophone" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="contributor" value="${contributor.id}" />
                
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error"><fmt:message key='value' /> (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" required="required" pattern=".{,3}" title="Maximum 3 characters" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error"><fmt:message key='value' /> (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" required="required" pattern=".{,3}" title="Maximum 3 characters" />
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
