<content:title>
    <fmt:message key="add.word" />
</content:title>

<content:section cssId="wordCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />

            <div class="row">
                <form:hidden path="language" value="${applicationScope.configProperties['content.language']}" />
                <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
                
                <div class="col s12">
                    <label><fmt:message key="allophones" /></label><br />
                    /<span id="selectedAllophonesContainer">
                        <c:forEach var="allophone" items="${word.allophones}">
                            <input name="allophones" type="hidden" value="${allophone.id}" />
                            <div class="chip" data-allophoneid="${allophone.id}" data-allophonevalue="${allophone.valueIpa}">
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
                            });
                        </script>
                    </span>/
                </div>
                
                <div class="input-field col s12">
                    <div id="allophonesContainer">
                        <c:forEach var="allophone" items="${allophones}">
                            <a href="#" class="allophone chip" data-allophoneid="${allophone.id}" data-valuesampa="${allophone.valueSampa}">${allophone.valueIpa}</a>
                            <audio id="audio_sampa_${allophone.valueSampa}">
                                <source src="<spring:url value='/static/allophone/sampa_${allophone.valueSampa}.wav' />" />
                            </audio>
                        </c:forEach>
                        <script>
                            $(function() {
                                // Append IPA value to text field
                                $('#allophonesContainer .chip').click(function(event) {
                                    console.info('#allophonesContainer .chip click');
                                    event.preventDefault();
                                    
                                    var allophoneId = $(this).attr("data-allophoneid");
                                    console.log('allophoneId: ' + allophoneId);
                                    
                                    var allophoneValueIpa = $(this).html();
                                    console.info('allophoneValueIpa: ' + allophoneValueIpa);
                                    
                                    $('#selectedAllophonesContainer').append('<input name="allophones" type="hidden" value="' + allophoneId + '" />');
                                    $('#selectedAllophonesContainer').append('<div class="chip">' + allophoneValueIpa + '</div>');
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
                    </div>
                </div>
                
                <p>&nbsp;</p>
                
                <div class="input-field col s12">
                    <select id="spellingConsistency" name="spellingConsistency">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="spellingConsistency" items="${spellingConsistencies}">
                            <option class="green" value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>><fmt:message key="spelling.consistency.${spellingConsistency}" /></option>
                        </c:forEach>
                    </select>
                    <label for="spellingConsistency"><fmt:message key="spelling.consistency" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="wordType" name="wordType">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="wordType" items="${wordTypes}">
                            <option value="${wordType}" <c:if test="${wordType == word.wordType}">selected="selected"</c:if>>${wordType}</option>
                        </c:forEach>
                    </select>
                    <label for="wordType"><fmt:message key="word.type" /></label>
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
