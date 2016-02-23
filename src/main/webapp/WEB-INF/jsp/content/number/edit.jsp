<content:title>
    <fmt:message key="edit.number" />
</content:title>

<content:section cssId="numberEditPage">
    <div class="section row">
        <div class="col s12 m10 offset-m1 l8 offset-l2">
            <h4><content:gettitle /></h4>
            <div class="card-panel">
                <form:form modelAttribute="number">
                    <tag:formErrors modelAttribute="number" />
                    
                    <div class="row">
                        <div class="input-field col s12">
                            <form:select path="language" cssErrorClass="error">
                                <c:set var="select"><fmt:message key='select' /></c:set>
                                <form:option value="" label="-- ${select} --" />
                                <form:options items="${languages}" />
                            </form:select>
                            <form:label path="language" cssErrorClass="error"><fmt:message key='language' /></form:label>
                        </div>
                        <div class="input-field col s12">
                            <form:label path="value" cssErrorClass="error"><fmt:message key='value' /></form:label>
                            <form:input path="value" cssErrorClass="error" type="number" />
                        </div>
                    </div>
                    
                    <button class="btn waves-effect waves-light" type="submit" name="action">
                        <fmt:message key="add" /> <i class="material-icons right">send</i>
                    </button>
                </form:form>
            </div>
        </div>
    </div>
</content:section>
