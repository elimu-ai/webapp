<content:title>
    <fmt:message key="edit.allophone" />
</content:title>

<content:section cssId="allophoneEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="allophone">
            <tag:formErrors modelAttribute="allophone" />
            
            <form:hidden path="revisionNumber" value="${allophone.revisionNumber}" />
            <form:hidden path="usageCount" value="${allophone.usageCount}" />

            <div class="row">
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

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <audio controls="true" autoplay="true">
        <source src="<spring:url value='/static/allophone/sampa_${allophone.valueSampa}.wav' />" />
    </audio>
    
    <div class="divider" style="margin: 1em 0;"></div>
    
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
