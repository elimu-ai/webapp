<content:title>
    <fmt:message key="add.number" />
</content:title>

<content:section cssId="numberCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="revisionNumber" value="${number.revisionNumber}" />
                
                <c:if test="${contributor.locale.language == 'ar'}">
                    <div id="symbolContainer" class="input-field col s12">
                        <form:label path="symbol" cssErrorClass="error"><fmt:message key='symbol' /></form:label>
                        <form:input path="symbol" cssErrorClass="error" />
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error"><fmt:message key='value' /> (<fmt:message key='number' />)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
                
                <div class="input-field col s12">
                    <select id="word" name="word">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}" <c:if test="${word.id == number.word.id}">selected="selected"</c:if>><c:out value="${word.text}" /></option>
                        </c:forEach>
                    </select>
                    <label for="word"><fmt:message key="number.word" /></label>
                    <a href="<spring:url value='/content/number/create' />" target="_blank"><fmt:message key="add.word" /></a>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <div class="previewContainer valignwrapper">
        <img src="<spring:url value='/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContentContainer">
            <div id="previewContent" class="previewContentGrapheme">

            </div>
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
