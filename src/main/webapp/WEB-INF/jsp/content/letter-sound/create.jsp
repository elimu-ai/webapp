<content:title>
    <fmt:message key="add.letter.sound.correspondence" />
</content:title>

<content:section cssId="letterSoundCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letterSound">
            <tag:formErrors modelAttribute="letterSoundCorrespondence" />
            
            <input type="hidden" name="timeStart" value="${timeStart}" />
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="letters" /></label><br />
                    "<span id="lettersContainer">
                        <c:forEach var="letter" items="${letterSound.letters}">
                            <input name="letters" type="hidden" value="${letter.id}" />
                            <div class="chip" data-letterid="${letter.id}" data-lettervalue="${letter.text}"
                                 style="font-size: 2rem; padding: 1rem; height: auto;">
                                <a href="<spring:url value='/content/letter/edit/${letter.id}' />">
                                    ${letter.text} 
                                </a>
                                <a href="#" class="letterDeleteLink" data-letterid="${letter.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.letterDeleteLink').on("click", function() {
                                    console.log('.letterDeleteLink on click');
                                    
                                    var letterId = $(this).attr("data-letterid");
                                    console.log('letterId: ' + letterId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="letters"][value="' + letterId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </span>"

                    <select id="letters" class="browser-default" style="font-size: 2rem; margin: 0.5rem 0; height: auto;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="letter" items="${letters}">
                            <option value="${letter.id}"><c:out value="${letter.text}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#letters').on("change", function() {
                                console.log('#letters on change');
                                
                                var letterId = $(this).val();
                                console.log('letterId: ' + letterId);
                                var letterText = $(this).find('option[value="' + letterId + '"]').text();
                                console.log('letterText: ' + letterText);
                                if (letterId != "") {
                                    $('#lettersContainer').append('<input name="letters" type="hidden" value="' + letterId + '" />');
                                    $('#lettersContainer').append('<div class="chip" style="font-size: 2rem; padding: 1rem; height: auto;">' + letterText + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/letter/create' />" target="_blank"><fmt:message key="add.letter" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="sounds" /></label><br />
                    /<span id="soundsContainer">
                        <c:forEach var="sound" items="${letterSound.sounds}">
                            <input name="sounds" type="hidden" value="${sound.id}" />
                            <div class="chip" data-soundid="${sound.id}" data-soundvalue="${sound.valueIpa}"
                                 style="font-size: 2rem; padding: 1rem; height: auto;">
                                <a href="<spring:url value='/content/sound/edit/${sound.id}' />">
                                    ${sound.valueIpa} 
                                </a>
                                <a href="#" class="soundDeleteLink" data-soundid="${sound.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.soundDeleteLink').on("click", function() {
                                    console.log('.soundDeleteLink on click');
                                    
                                    var soundId = $(this).attr("data-soundid");
                                    console.log('soundId: ' + soundId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="sounds"][value="' + soundId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </span>/

                    <select id="sounds" class="browser-default" style="font-size: 2rem; margin: 0.5rem 0; height: auto;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="sound" items="${sounds}">
                            <option value="${sound.id}"><c:out value="${sound.valueIpa}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#sounds').on("change", function() {
                                console.log('#sounds on change');
                                
                                var soundId = $(this).val();
                                console.log('soundId: ' + soundId);
                                var soundValueIpa = $(this).find('option[value="' + soundId + '"]').text();
                                console.log('soundValueIpa: ' + soundValueIpa);
                                if (soundId != "") {
                                    $('#soundsContainer').append('<input name="sounds" type="hidden" value="' + soundId + '" />');
                                    $('#soundsContainer').append('<div class="chip" style="font-size: 2rem; padding: 1rem; height: auto;">' + soundValueIpa + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/sound/create' />" target="_blank"><fmt:message key="add.sound" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="resources" /></h5>
    <div class="card-panel deep-purple lighten-5">
        <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">IPA for Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Hindustani_phonology" target="_blank">Hindustani phonology</a>
                </li>
                <li>
                    <a href="https://omniglot.com/writing/hindi.htm#alphabet" target="_blank">Devanāgarī alphabet for Hindi</a>
                </li>
            </ol>
        </c:if>
        
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
            <li>
                <a href="https://en.wikipedia.org/wiki/International_Phonetic_Alphabet">International Phonetic Alphabet (IPA)</a>
            </li>
        </ol>
    </div>
</content:aside>
