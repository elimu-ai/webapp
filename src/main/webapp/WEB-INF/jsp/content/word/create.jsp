<content:title>
    <fmt:message key="add.word" />
</content:title>

<content:section cssId="wordCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />
            
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>
            
            <%--
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
            --%>
                
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="letter.to.allophone.mappings" /></label><br />
                    
                    <span id="letterToAllophoneMappingsContainer">
                        <c:forEach var="letterToAllophoneMapping" items="${word.letterToAllophoneMappings}">
                            <input name="letterToAllophoneMappings" type="hidden" value="${letterToAllophoneMapping.id}" />
                            <div class="chip" data-letter-to-allophone-mapping-id="${letterToAllophoneMapping.id}">
                                <a href="<spring:url value='/content/letter-to-allophone-mapping/edit/${letterToAllophoneMapping.id}' />">
                                    "<c:forEach var="letter" items="${letterToAllophoneMapping.letters}">
                                        ${letter.text}
                                    </c:forEach>" 
                                    → 
                                    /<c:forEach var="allophone" items="${letterToAllophoneMapping.allophones}">
                                        ${allophone.valueIpa}
                                    </c:forEach>/
                                </a>
                                
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
                            <option class="green" value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>><fmt:message key="spelling.consistency.${spellingConsistency}" /></option>
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
                            <option value="${rootWord.id}" <c:if test="${rootWord.id == word.rootWord.id}">selected="selected"</c:if>>${rootWord.text}<c:if test="${not empty rootWord.wordType}"> (${rootWord.wordType})</c:if><c:out value=" ${emojisByWordId[rootWord.id]}" /></option>
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
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <c:if test="${not empty word.text}">
        <h5 class="center"><fmt:message key="audio" /></h5>
        <c:choose>
            <c:when test="${empty audio}">
                <div class="card-panel amber lighten-3">
                    <b>Warning:</b> This word has no corresponding audio.
                    <a href="<spring:url value='/content/multimedia/audio/create?autoFillTranscription=${word.text}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
                </div>
            </c:when>
            <c:otherwise>
                <audio controls="true" autoplay="true">
                    <source src="<spring:url value='/audio/${audio.id}_r${audio.revisionNumber}.${fn:toLowerCase(audio.audioFormat)}' />" />
                </audio>
            </c:otherwise>
        </c:choose>
    </c:if>
    
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <c:if test="${not empty word.text}">
            <div class="divider" style="margin-top: 1em;"></div>
        </c:if>

        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            For assistance with pronunciation and IPA transcription of "<c:out value='${word.text}' />", see:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#hi" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/#view=home&op=translate&sl=hi&tl=en&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'FIL'}">
        <c:if test="${not empty word.text}">
            <div class="divider" style="margin: 1.5em 0;"></div>
        </c:if>

        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            For assistance with pronunciation and IPA transcription of "<c:out value='${word.text}' />", see:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#tl" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/#view=home&op=translate&sl=tl&tl=en&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
                <li>
                    <a href="https://www.tagaloglessons.com/words/<c:out value='${word.text}' />.php" target="_blank">TagalogLessons</a>
                </li>
            </ol>
        </div>
    </c:if>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="<spring:url value='/content/word/pending' />"><fmt:message key="words.pending" /></a>
            </li>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/master/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
</content:aside>
