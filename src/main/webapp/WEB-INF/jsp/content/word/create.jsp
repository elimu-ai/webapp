<content:title>
    <fmt:message key="add.word" />
</content:title>

<content:section cssId="wordCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="phonetics" cssErrorClass="error"><fmt:message key='phonetics' /> (IPA)</form:label>
                    <form:input path="phonetics" cssErrorClass="error" />
                    <div id="allophonesContainer">
                        <c:forEach var="allophone" items="${allophones}">
                            <a href="#" class="chip">${allophone.valueIpa}</a>
                        </c:forEach>
                        <script>
                            $(function() {
                                // Append IPA value to text field
                                $('#allophonesContainer .chip').click(function(event) {
                                    console.info('#allophonesContainer .chip click');
                                    event.preventDefault();
                                    var valueIpa = $(this).html();
                                    console.info('valueIpa: ' + valueIpa);
                                    $('#phonetics').val($('#phonetics').val() + valueIpa);
                                    $('#phonetics').focus();
                                });
                            });
                        </script>
                    </div>
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
            
            $('#text').on("change", function() {
                console.debug('#text on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var value = $('#text').val();
                if ((value != undefined) && (value != "")) {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
</content:aside>
