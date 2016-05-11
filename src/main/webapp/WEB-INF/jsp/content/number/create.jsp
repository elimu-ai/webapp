<content:title>
    <fmt:message key="add.number" />
</content:title>

<content:section cssId="numberCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />

            <div class="row">
                <form:hidden path="language" value="${contributor.language}" />
                <c:if test="${contributor.language == 'ARABIC'}">
                    <div id="symbolContainer" class="input-field col s12">
                        <form:label path="symbol" cssErrorClass="error"><fmt:message key='symbol' /></form:label>
                        <form:input path="symbol" cssErrorClass="error" />
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error"><fmt:message key='value' /> (<fmt:message key='number' />)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
            </div>

            <button class="btn waves-effect waves-light" type="submit" name="action">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <%--<h5><fmt:message key="preview" /></h5>--%>
    
    <div class="previewContainer valignwrapper" style="position: relative;">
        <img src="<spring:url value='/img/device-nexus-5.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContent" style="position: absolute; top: 40%; font-size: 5em; width: 100%; text-align: center;">
            
        </div>
    </div>
    <script>
        $(function() {
            initializePreview();
            
            $('#symbol, #value').on("change", function() {
                console.debug('#symbol/#value on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var symbol = $('#symbol').val();
                var value = $('#value').val();
                if ((symbol != undefined) && (symbol != "")) {
                    $('#previewContent').html(symbol);
                } else {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
</content:aside>
