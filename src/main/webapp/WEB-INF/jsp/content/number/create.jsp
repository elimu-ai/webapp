<content:title>
    <fmt:message key="add.number" />
</content:title>

<content:section cssId="numberCreatePage">
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
                    <script>
                        $(function() {
                            $('#language').on("change", function() {
                                console.debug('#language on change');
                                var language = $(this).val();
                                console.debug('language: ' + language);
                                if (language == "ARABIC") {
                                    $('#symbolContainer').fadeIn();
                                } else {
                                    $('#symbol').val("");
                                    $('#symbolContainer').fadeOut();
                                }
                            });
                        });
                    </script>
                </div>
                <div id="symbolContainer" class="input-field col s12" <c:if test="${number.language != 'ARABIC'}"> style="display: none;" </c:if> >
                    <form:label path="symbol" cssErrorClass="error"><fmt:message key='symbol' /></form:label>
                    <form:input path="symbol" cssErrorClass="error" />
                </div>
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
                if (symbol != "") {
                    $('#previewContent').html(symbol);
                } else {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
</content:aside>
