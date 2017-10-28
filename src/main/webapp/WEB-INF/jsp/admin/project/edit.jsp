<content:title>
    <fmt:message key="edit.project" />
</content:title>

<content:section cssId="adminProjectCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="project">
            <tag:formErrors modelAttribute="project" />
            
            <c:forEach var="appCategory" items="${project.appCategories}">
                <form:hidden path="appCategories" value="${appCategory.id}" />
            </c:forEach>

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="name" cssErrorClass="error"><fmt:message key='name' /></form:label>
                    <form:input path="name" cssErrorClass="error" />
                </div>
                
                <div class="col s12">
                    <label><fmt:message key="managers" /></label>
                    <div id="managersContainer">
                        <c:forEach var="manager" items="${project.managers}">
                            <input name="managers" type="hidden" value="${manager.id}" />
                            <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                                <div class="chip">
                                    <img src="<spring:url value='${manager.imageUrl}' />" alt="${manager.firstName}" /> 
                                    <c:out value="${manager.firstName}" />&nbsp;<c:out value="${manager.lastName}" />
                                </div>
                            </a>
                        </c:forEach>
                    </div>

                    <select id="managers" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="manager" items="${managers}">
                            <option value="${manager.id}"><c:out value="${manager.firstName}" />&nbsp;<c:out value="${manager.lastName}" /> &lt;<c:out value="${manager.email}" />&gt;</option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#managers').on("change", function() {
                                console.log('#managers on change');

                                var managerId = $(this).val();
                                console.log('managerId: ' + managerId);
                                var managerEmail = $(this).find('option[value="' + managerId + '"]').text();
                                console.log('managerEmail ' + managerEmail);
                                if (managerId != "") {
                                    $('#managersContainer').append('<input name="managers" type="hidden" value="' + managerId + '" />');
                                    $('#managersContainer').append('<div class="chip">' + managerEmail + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                </div>
            </div>

            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
