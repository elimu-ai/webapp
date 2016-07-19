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
                
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error"><fmt:message key='value' /> (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error"><fmt:message key='value' /> (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" />
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
