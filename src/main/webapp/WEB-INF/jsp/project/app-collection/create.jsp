<content:title>
    <fmt:message key="add.app.collection" />
</content:title>

<content:section cssId="appCollectionCreatePage">
    <h4><content:gettitle /></h4>
    
    <div class="card-panel">
        <form:form modelAttribute="appCollection">
            <tag:formErrors modelAttribute="appCollection" />
            
            <form:hidden path="project" value="${project.id}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="name" cssErrorClass="error"><fmt:message key='name' /></form:label>
                    <form:input path="name" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <fmt:message key="app.categories" /><br />
                    <c:forEach var="appCategory" items="${project.appCategories}">
                        <input type="checkbox" name="appCategories" id="appCategory_${appCategory.id}" value="${appCategory.id}" />
                        <label for="appCategory_${appCategory.id}">
                            <c:out value="${appCategory.name}" />
                        </label><br />
                    </c:forEach>
                </div>
            </div>
            <br />
            
            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
