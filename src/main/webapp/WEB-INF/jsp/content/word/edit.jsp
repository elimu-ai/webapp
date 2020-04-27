<content:title>
    <fmt:message key="edit.word" />
</content:title>

<content:section cssId="wordEditPage">
    <h4><content:gettitle /></h4>
    
    <c:choose>
        <c:when test="${empty audio}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This word has no corresponding audio.
                <a href="<spring:url value='/content/multimedia/audio/create?transcription=${word.text}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
            </div>
        </c:when>
        <c:otherwise>
            <audio controls="true" autoplay="true">
                <source src="<spring:url value='/audio/${audio.id}.${fn:toLowerCase(audio.audioFormat)}' />" />
            </audio>
        </c:otherwise>
    </c:choose>
    
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />

            <div class="row">
                <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
                <form:hidden path="usageCount" value="${word.usageCount}" />
                
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
                
                <c:if test="${not empty word.text}">
                    <c:if test="${(applicationScope.configProperties['content.language'] == 'BEN')
                          || (applicationScope.configProperties['content.language'] == 'HIN')
                          || (applicationScope.configProperties['content.language'] == 'URD')}">
                          <%-- Extract and display each letter of the word. E.g. "न ह ी ं" for "नहीं" --%>
                        <div class="col s12 grey-text" style="font-size: 3em; height: 2em;">
                            <c:forEach begin="0" end="${fn:length(word.text) - 1}" varStatus="status">
                                <c:set var="letter" value="${fn:substring(word.text, status.index, status.index + 1)}" />
                                <c:out value="${letter}" /><c:out value=" " />
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>
                
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
                            <option value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>><fmt:message key="spelling.consistency.${spellingConsistency}" /></option>
                        </c:forEach>
                    </select>
                    <label for="spellingConsistency"><fmt:message key="spelling.consistency" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="rootWord" name="rootWord">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="rootWord" items="${rootWords}">
                            <option value="${rootWord.id}" <c:if test="${rootWord.id == word.rootWord.id}">selected="selected"</c:if>>${rootWord.text}<c:if test="${not empty rootWord.wordType}"> (${rootWord.wordType})</c:if></option>
                        </c:forEach>
                    </select>
                    <label for="rootWord"><fmt:message key="root.word" /></label>
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
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/word/delete/${word.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
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
    
    <c:if test="${not empty wordInflections}">
        <div class="divider" style="margin-top: 1em;"></div>

        <h5 class="center"><fmt:message key="inflections" /></h5>

        <div id="wordInflectionsContainer">
            <c:forEach var="word" items="${wordInflections}">
                <div class="chip">
                    <a href="<spring:url value='/content/word/edit/${word.id}' />">
                        ${word.text} 
                    </a>
                </div>
            </c:forEach>
        </div>
    </c:if>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center"><fmt:message key="labeled.content" /></h5>
    
    <b><fmt:message key="audios" /></b><br />
    <br />
    
    <b><fmt:message key="emojis" /></b><br />
    <div id="labeledEmojis">
        <c:forEach var="emoji" items="${labeledEmojis}">
            <a href="<spring:url value='/content/emoji/edit/${emoji.id}' />">
                <span style="font-size: 4em;">${emoji.glyph}</span>
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b><fmt:message key="images" /></b><br />
    <div id="labeledImages">
        <c:forEach var="image" items="${labeledImages}">
            <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                <img src="<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageFormat)}' />" alt="${image.title}" />
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b><fmt:message key="videos" /></b><br />
</content:aside>
