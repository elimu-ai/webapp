<content:title>
    <fmt:message key="add.allophone" />
</content:title>

<content:section cssId="allophoneCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="allophone">
            <tag:formErrors modelAttribute="allophone" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="revisionNumber" value="${allophone.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error"><fmt:message key='value' /> (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error"><fmt:message key='value' /> (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" />
                </div>
            
                <div class="input-field col s12">
                    <select id="soundType" name="soundType">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="soundType" items="${soundTypes}">
                            <option value="${soundType}" <c:if test="${soundType == allophone.soundType}">selected="selected"</c:if>><c:out value="${soundType}" /></option>
                        </c:forEach>
                    </select>
                    <label for="soundType"><fmt:message key="sound.type" /></label>
                </div>
            </div>

            <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <div class="previewContainer valignwrapper">
        <img src="<spring:url value='/static/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContentContainer">
            <div id="previewContent" class="previewContentGrapheme">

            </div>
        </div>
    </div>
    <script>
        $(function() {
            initializePreview();
            
            $('#valueIpa').on("change", function() {
                console.debug('#valueIpa on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var value = $('#valueIpa').val();
                if (value != '') {
                    $('#previewContent').html('/' + value + '/');
                }
            };
        });
    </script>
</content:aside>
