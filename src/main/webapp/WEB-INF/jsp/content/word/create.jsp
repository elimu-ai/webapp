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
                
                <c:if test="${not empty word.text}">
                    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
                          <%-- Extract and display each letter of the word. E.g. "न ह ी ं" for "नहीं" --%>
                        <div class="col s12 grey-text" style="font-size: 4em;">
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
                    <label><fmt:message key="letter.sound.correspondences" /></label><br />
                    
                    <div id="letterSoundsContainer">
                        <c:forEach var="letterSound" items="${word.letterSounds}">
                            <input name="letterSounds" type="hidden" value="${letterSound.id}" />
                            <div class="chip">
                                <a href="#" class="letterSoundDeleteLink" data-letter-sound-correspondence-id="${letterSound.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                                <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />">
                                    " <c:forEach var="letter" items="${letterSound.letters}">
                                        ${letter.text}<c:out value=" " />
                                    </c:forEach> "<br />
                                    ↓<br />
                                    / <c:forEach var="sound" items="${letterSound.sounds}">
                                        ${sound.valueIpa}<c:out value=" " />
                                    </c:forEach> /
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.letterSoundDeleteLink').on("click", function() {
                                    console.log('.letterSoundDeleteLink on click');
                                    
                                    var letterSoundId = $(this).attr("data-letter-sound-correspondence-id");
                                    console.log('letterSoundId: ' + letterSoundId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="letterSounds"][value="' + letterSoundId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </div>

                    <select id="letterSounds" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="letterSound" items="${letterSounds}">
                            <option value="${letterSound.id}" data-letters="<c:forEach var="letter" items="${letterSound.letters}">${letter.text}</c:forEach>" data-sounds="<c:forEach var="sound" items="${letterSound.sounds}">${sound.valueIpa}</c:forEach>">" <c:forEach var="letter" items="${letterSound.letters}">${letter.text}<c:out value=" " /></c:forEach> " → / <c:forEach var="sound" items="${letterSound.sounds}">${sound.valueIpa}<c:out value=" " /></c:forEach> /</option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#letterSounds').on("change", function() {
                                console.log('#letterSounds on change');
                                
                                var letterSoundId = $(this).val();
                                console.log('letterSoundId: ' + letterSoundId);
                                var selectedOption = $(this).find('option[value="' + letterSoundId + '"]');
                                var letterSoundLetters = selectedOption.attr('data-letters');
                                console.log('letterSoundLetters: "' + letterSoundLetters + '"');
                                var letterSoundSounds = selectedOption.attr('data-sounds');
                                console.log('letterSoundSounds: "' + letterSoundSounds + '"');
                                if (letterSoundId != "") {
                                    $('#letterSoundsContainer').append('<input name="letterSounds" type="hidden" value="' + letterSoundId + '" />');
                                    $('#letterSoundsContainer').append('<div class="chip">"' + letterSoundLetters + '"<br />↓<br />/' + letterSoundSounds + '/</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/letter-sound/create' />" target="_blank"><fmt:message key="add.letter.sound.correspondence" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <%--
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
            --%>
                
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
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <c:if test="${applicationScope.configProperties['content.language'] == 'TGL'}">
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
                    <a href="https://translate.google.com/?sl=tl&tl=en&op=translate&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
                <li>
                    <a href="https://www.tagaloglessons.com/words/<c:out value='${word.text}' />.php" target="_blank">TagalogLessons</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <c:if test="${not empty word.text}">
            <div class="divider" style="margin: 1.5em 0;"></div>
        </c:if>

        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            For assistance with pronunciation and IPA transcription of "<c:out value='${word.text}' />", see:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#hi" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/?sl=hi&tl=en&op=translate&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
            </ol>
        </div>
    </c:if>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="<spring:url value='/content/word/pending' />"><fmt:message key="words.pending" /></a>
            </li>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
</content:aside>
