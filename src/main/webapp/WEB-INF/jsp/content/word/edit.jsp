<content:title>
    <fmt:message key="edit.word" />
</content:title>

<content:section cssId="wordEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />
            
            <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
            <form:hidden path="usageCount" value="${word.usageCount}" />

            <div class="row">
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
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="letters" /></label><br />
                    
                    TODO...<br />
                    
                    <a href="<spring:url value='/content/letter/create' />" target="_blank"><fmt:message key="add.letter" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
        
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="allophones" /></label><br />
                    /<span id="selectedAllophonesContainer">
                        <c:forEach var="allophone" items="${word.allophones}">
                            <input name="allophones" type="hidden" value="${allophone.id}" />
                            <div class="chip <c:if test="${allophone.soundType == 'VOWEL'}"> purple lighten-5</c:if><c:if test="${allophone.soundType == 'CONSONANT'}"> teal lighten-5</c:if>" data-allophoneid="${allophone.id}" data-allophonevalue="${allophone.valueIpa}">
                                <a href="<spring:url value='/content/allophone/edit/${allophone.id}' />">
                                    ${allophone.valueIpa}
                                </a> 
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
                            <a href="#" class="allophone chip <c:if test="${allophone.soundType == 'VOWEL'}"> purple lighten-5</c:if><c:if test="${allophone.soundType == 'CONSONANT'}"> teal lighten-5</c:if>" data-allophoneid="${allophone.id}" data-valuesampa="${allophone.valueSampa}">${allophone.valueIpa}</a>
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
                    <a href="<spring:url value='/content/allophone/create' />" target="_blank"><fmt:message key="add.allophone" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
                
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="letter.to.allophone.mappings" /></label><br />
                    
                    <span id="letterToAllophoneMappingsContainer">
                        <c:forEach var="letterToAllophoneMapping" items="${word.letterToAllophoneMappings}">
                            <input name="letterToAllophoneMappings" type="hidden" value="${letterToAllophoneMapping.id}" />
                            <div class="chip" data-letter-to-allophone-mapping-id="${letterToAllophoneMapping.id}">
                                "<c:forEach var="letter" items="${letterToAllophoneMapping.letters}">
                                    <a href="<spring:url value='/content/letter/edit/${letter.id}' />">${letter.text}</a>
                                </c:forEach>" 
                                → 
                                /<c:forEach var="allophone" items="${letterToAllophoneMapping.allophones}">
                                    <a href="<spring:url value='/content/allophone/edit/${allophone.id}' />">${allophone.valueIpa}</a>
                                </c:forEach>/
                                
                                <a href="#" class="letterToAllophoneMappingDeleteLink" data-letter-to-allophone-mapping-id="${letterToAllophoneMapping.id}">
                                    <i class="material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.letterToAllophoneMappingDeleteLink').on("click", function() {
                                    console.log('.letterToAllophoneMappingDeleteLink on click');
                                    
                                    var letterToAllophoneMappingId = $(this).attr("data-letter-to-allophone-mapping-id");
                                    console.log('letterToAllophoneMappingId: ' + letterToAllophoneMappingId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="letterToAllophoneMappings"][value="' + letterToAllophoneMappingId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </span>

                    <select id="letterToAllophoneMappings" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="letterToAllophoneMapping" items="${letterToAllophoneMappings}">
                            <option value="${letterToAllophoneMapping.id}">"<c:forEach var="letter" items="${letterToAllophoneMapping.letters}">${letter.text}</c:forEach>" → /<c:forEach var="allophone" items="${letterToAllophoneMapping.allophones}">${allophone.valueIpa}</c:forEach>/</option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#letterToAllophoneMappings').on("change", function() {
                                console.log('#letterToAllophoneMappings on change');
                                
                                var letterToAllophoneMappingId = $(this).val();
                                console.log('letterToAllophoneMappingId: ' + letterToAllophoneMappingId);
                                var letterToAllophoneMappingValueIpa = $(this).find('option[value="' + letterToAllophoneMappingId + '"]').text();
                                console.log('letterToAllophoneMappingValueIpa: ' + letterToAllophoneMappingValueIpa);
                                if (letterToAllophoneMappingId != "") {
                                    $('#letterToAllophoneMappingsContainer').append('<input name="letterToAllophoneMappings" type="hidden" value="' + letterToAllophoneMappingId + '" />');
                                    $('#letterToAllophoneMappingsContainer').append('<div class="chip">' + letterToAllophoneMappingValueIpa + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/letter-to-allophone-mapping/create' />" target="_blank"><fmt:message key="add.letter.to.allophone.mapping" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <select id="spellingConsistency" name="spellingConsistency">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="spellingConsistency" items="${spellingConsistencies}">
                            <option value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>><fmt:message key="spelling.consistency.${spellingConsistency}" /></option>
                        </c:forEach>
                    </select>
                    <label for="spellingConsistency"><fmt:message key="spelling.consistency" /></label>
                </div>
            </div>
                
            <div class="row">
                <div class="input-field col s12">
                    <select id="rootWord" name="rootWord">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="rootWord" items="${rootWords}">
                            <option value="${rootWord.id}" <c:if test="${rootWord.id == word.rootWord.id}">selected="selected"</c:if>>${rootWord.text}<c:if test="${not empty rootWord.wordType}"> (${rootWord.wordType})</c:if></option>
                        </c:forEach>
                    </select>
                    <label for="rootWord"><fmt:message key="root.word" /></label>
                </div>
            </div>
                
            <div class="row">
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
    <h5 class="center"><fmt:message key="audio" /></h5>
    <c:choose>
        <c:when test="${empty audio}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This word has no corresponding audio.
                <a href="<spring:url value='/content/multimedia/audio/create?transcription=${word.text}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
            </div>
        </c:when>
        <c:otherwise>
            <audio controls="true" autoplay="true">
                <source src="<spring:url value='/audio/${audio.id}_r${audio.revisionNumber}.${fn:toLowerCase(audio.audioFormat)}' />" />
            </audio>
        </c:otherwise>
    </c:choose>
    
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <c:if test="${not empty word.text}">
            <div class="divider" style="margin-top: 1em;"></div>
        </c:if>

        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            For assistance with pronunciation and IPA transcription of "<c:out value='${word.text}' />", see:
            <ul>
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#hi" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/#view=home&op=translate&sl=hi&tl=en&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
            </ul>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'FIL'}">
        <c:if test="${not empty word.text}">
            <div class="divider" style="margin-top: 1em;"></div>
        </c:if>

        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            For assistance with pronunciation and IPA transcription of "<c:out value='${word.text}' />", see:
            <ul>
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#tl" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/#view=home&op=translate&sl=tl&tl=en&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
                <li>
                    <a href="https://www.tagaloglessons.com/words/<c:out value='${word.text}' />.php" target="_blank">TagalogLessons</a>
                </li>
            </ul>
            
            <div class="divider" style="margin: 1em 0;"></div>
        
            General resources:
            <ul>
                <li>
                    <a href="https://github.com/elimu-ai/wiki/blob/master/LOCALIZATION.md#add-educational-content-to-the-website-step-by-step-guide" target="_blank">elimu.ai Wiki</a>
                </li>
                <li>
                    <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
                </li>
            </ul>
        </div>
    </c:if>
    
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
                <span style="font-size: 6em;">${emoji.glyph}</span>
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b><fmt:message key="images" /></b><br />
    <div id="labeledImages">
        <c:forEach var="image" items="${labeledImages}">
            <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                <img src="<spring:url value='/image/${image.id}_r${image.revisionNumber}.${fn:toLowerCase(image.imageFormat)}' />" alt="${image.title}" />
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b><fmt:message key="videos" /></b><br />
</content:aside>
