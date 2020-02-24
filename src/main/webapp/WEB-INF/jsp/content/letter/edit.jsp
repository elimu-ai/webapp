<content:title>
    <fmt:message key="edit.letter" />
</content:title>

<content:section cssId="letterEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <form:hidden path="language" value="${letter.language}" />
            <form:hidden path="revisionNumber" value="${letter.revisionNumber}" />
            <form:hidden path="usageCount" value="${letter.usageCount}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="allophones" /></label><br />
                    /<span id="allophonesContainer">
                        <c:forEach var="allophone" items="${letter.allophones}">
                            <input name="allophones" type="hidden" value="${allophone.id}" />
                            <audio id="audio_sampa_${allophone.valueSampa}">
                                <source src="<spring:url value='/static/allophone/sampa_${allophone.valueSampa}.wav' />" />
                            </audio>
                            <div class="allophone chip" data-allophoneid="${allophone.id}" data-allophonevalue="${allophone.valueIpa}" data-valuesampa="${allophone.valueSampa}">
                                ${allophone.valueIpa} 
                                <a href="#" class="allophoneDeleteLink" data-allophoneid="${allophone.id}">
                                    <i class="material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.allophoneDeleteLink').on("click", function() {
                                    console.log('.allophoneDeleteLink on click');
                                    
                                    var allophoneId = $(this).attr("data-allophoneid");
                                    console.log('allophoneId: ' + allophoneId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="allophones"][value="' + allophoneId + '"]');
                                    $hiddenInput.remove();
                                });
                                
                                // Play sound when hovering IPA value
                                $('.allophone').mouseenter(function() {
                                    console.info('.allophone mouseenter');
                                    
                                    var valueSampa = $(this).attr('data-valuesampa');
                                    console.info('valueSampa: ' + valueSampa);
                                    
                                    var audio = $('#audio_sampa_' + valueSampa);
                                    audio[0].play();
                                });
                            });
                        </script>
                    </span>/

                    <select id="allophones" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="allophone" items="${allophones}">
                            <option value="${allophone.id}"><c:out value="${allophone.valueIpa}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#allophones').on("change", function() {
                                console.log('#allophones on change');
                                
                                var allophoneId = $(this).val();
                                console.log('allophoneId: ' + allophoneId);
                                var allophoneValueIpa = $(this).find('option[value="' + allophoneId + '"]').text();
                                console.log('allophoneValueIpa: ' + allophoneValueIpa);
                                if (allophoneId != "") {
                                    $('#allophonesContainer').append('<input name="allophones" type="hidden" value="' + allophoneId + '" />');
                                    $('#allophonesContainer').append('<div class="chip">' + allophoneValueIpa + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col">
                    <select id="diacritic" name="diacritic">
                        <option value="false" <c:if test="${not letter.diacritic}">selected="selected"</c:if>><fmt:message key="no" /></option>
                        <option value="true" <c:if test="${letter.diacritic}">selected="selected"</c:if>><fmt:message key="yes" /></option>
                    </select>
                    <label for="diacritic"><fmt:message key="diacritic" /></label>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/letter/delete/${letter.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <c:forEach var="allophone" items="${letter.allophones}">
        <audio controls="true" autoplay="true">
            <source src="<spring:url value='/static/allophone/sampa_${allophone.valueSampa}.wav' />" />
        </audio><br />
    </c:forEach>
    
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
