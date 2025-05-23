<content:title>
    Edit word
</content:title>

<content:section cssId="wordEditPage">
    <c:choose>
        <c:when test="${word.peerReviewStatus == 'APPROVED'}">
            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
        </c:when>
        <c:when test="${word.peerReviewStatus == 'NOT_APPROVED'}">
            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
        </c:when>
        <c:otherwise>
            <c:set var="peerReviewStatusColor" value="" />
        </c:otherwise>
    </c:choose>
    <div class="chip right ${peerReviewStatusColor}" style="margin-top: 1.14rem;">
        <a href="#contribution-events">
            Peer-review: ${word.peerReviewStatus}
        </a>
    </div>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />
            
            <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
            <form:hidden path="usageCount" value="${word.usageCount}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error">Text</form:label>
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
                    <label>Letter-sound correspondences</label><br />
                    
                    <div id="letterSoundsContainer">
                        <c:forEach var="letterSound" items="${word.letterSounds}">
                            <input name="letterSounds" type="hidden" value="${letterSound.id}" />
                            <div class="chip">
                                <a href="#" class="letterSoundDeleteLink" data-letter-sound-correspondence-id="${letterSound.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                                <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />">
                                    / <c:forEach var="sound" items="${letterSound.sounds}">
                                        ${sound.valueIpa}<c:out value=" " />
                                    </c:forEach> /<br />
                                    ↓<br />
                                    " <c:forEach var="letter" items="${letterSound.letters}">
                                        ${letter.text}<c:out value=" " />
                                    </c:forEach> "
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

                    <select id="letterSounds" class="browser-default" style="font-size: 2rem; margin: 0.5em 0;">
                        <option value="">-- Select --</option>
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
                    
                    <a href="<spring:url value='/content/letter-sound/create' />" target="_blank">Add letter-sound correspondence <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <%--
            <div class="row">
                <div class="input-field col s12">
                    <select id="spellingConsistency" name="spellingConsistency">
                        <option value="">-- Select --</option>
                        <c:forEach var="spellingConsistency" items="${spellingConsistencies}">
                            <option value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>>
                                <c:choose>
                                    <c:when test="${spellingConsistency == 'PERFECT'}">Perfect (100% correspondence)</c:when>
                                    <c:when test="${spellingConsistency == 'HIGHLY_PHONEMIC'}">Highly phonemic (80%-99% correspondence)</c:when>
                                    <c:when test="${spellingConsistency == 'PHONEMIC'}">Phonemic (60%-79% correspondence)</c:when>
                                    <c:when test="${spellingConsistency == 'NON_PHONEMIC'}">Non-phonemic (40%-59% correspondence)</c:when>
                                    <c:when test="${spellingConsistency == 'HIGHLY_NON_PHONEMIC'}">Highly non-phonemic (0%-39% correspondence)</c:when>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                    <label for="spellingConsistency">Grapheme-phoneme correspondence</label>
                </div>
            </div>
            --%>
                
            <div class="row">
                <div class="input-field col s12">
                    <select id="rootWord" name="rootWord">
                        <option value="">-- Select --</option>
                        <c:forEach var="rootWord" items="${rootWords}">
                            <option value="${rootWord.id}" <c:if test="${rootWord.id == word.rootWord.id}">selected="selected"</c:if>>${rootWord.text}<c:if test="${not empty rootWord.wordType}"> (${rootWord.wordType})</c:if><c:out value=" ${emojisByWordId[rootWord.id]}" /></option>
                        </c:forEach>
                    </select>
                    <label for="rootWord">Root word</label>
                </div>
            </div>
                
            <div class="row">
                <div class="input-field col s12">
                    <select id="wordType" name="wordType">
                        <option value="">-- Select --</option>
                        <c:forEach var="wordType" items="${wordTypes}">
                            <option value="${wordType}" <c:if test="${wordType == word.wordType}">selected="selected"</c:if>>${wordType}</option>
                        </c:forEach>
                    </select>
                    <label for="wordType">Word type</label>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
            <c:if test="${fn:contains(contributor.roles, 'EDITOR')}">
                <a href="<spring:url value='/content/word/delete/${word.id}' />" class="waves-effect waves-red red-text btn-flat right">Delete</a>
            </c:if>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty wordContributionEvents) 
                  && (wordContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5>Peer-review 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/word-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                Do you approve the quality of this word?
            </p>
            
            <input type="hidden" name="wordContributionEventId" value="${wordContributionEvents[0].id}" />
            
            <input type="radio" id="approved_true" name="approved" value="true" />
            <label for="approved_true">Yes (approve)</label><br />

            <input type="radio" id="approved_false" name="approved" value="false" />
            <label for="approved_false">No (request changes)</label><br />
            
            <script>
                $(function() {
                    $('[name="approved"]').on('change', function() {
                        console.info('[name="approved"] on change');
                        
                        var isApproved = $('#approved_true').is(':checked');
                        console.info('isApproved: ' + isApproved);
                        if (isApproved) {
                            console.info('isApproved');
                            $('#comment').removeAttr('required');
                        } else {
                            $('#comment').attr('required', 'required');
                            console.info('!isApproved');
                        }
                        
                        $('#peerReviewSubmitContainer').fadeIn();
                    });
                });
            </script>
            
            <div id="peerReviewSubmitContainer" style="display: none;">
                <label for="comment">Comment</label>
                <textarea id="comment" name="comment" class="materialize-textarea"></textarea>

                <button class="btn waves-effect waves-light" type="submit">
                    Submit <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
        
        <div class="divider" style="margin: 2em 0;"></div>
    </c:if>
    
    <a name="contribution-events"></a>
    <h5>Contributions 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
            <a name="contribution-event_${wordContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${wordContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${wordContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${wordContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                <c:if test="${not empty wordContributionEvent.comment}">
                    <blockquote><c:out value="${wordContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEvents}">
                    <c:if test="${wordPeerReviewEvent.wordContributionEvent.id == wordContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${wordPeerReviewEvent.getApproved()}">
                            <div class="col s4">
                                <c:set var="chipContributor" value="${wordPeerReviewEvent.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${wordPeerReviewEvent.getApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${wordPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty wordPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${wordPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside> 
    <h5 class="center">Resources</h5>

    <c:if test="${applicationScope.configProperties['content.language'] == 'TGL'}">
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
                    <a href="https://www.tagalog.com/dictionary/<c:out value='${word.text}' />" target="_blank">Tagalog.com</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <div class="card-panel deep-purple lighten-5">
            Hindi resources:
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
    <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
        <div class="card-panel deep-purple lighten-5">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#th" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/?sl=th&tl=en&op=translate&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
                <li>
                    <a href="https://thai-notes.com/tools/thai2ipa.html" target="_blank">Convert Thai to IPA</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'VIE'}">
        <div class="card-panel deep-purple lighten-5">
            Vietnamese resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://forvo.com/word/<c:out value='${word.text}' />/#vi" target="_blank">Forvo</a>
                </li>
                <li>
                    <a href="https://translate.google.com/?sl=vi&tl=en&op=translate&text=<c:out value='${word.text}' />" target="_blank">Google Translate</a>
                </li>
            </ol>
        </div>
    </c:if>
    
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="<spring:url value='/content/word/pending' />">Words pending</a>
            </li>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
    
    <c:if test="${not empty wordInflections}">
        <div class="divider" style="margin: 1.5em 0;"></div>

        <h5 class="center">Inflections</h5>

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
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center">Labeled content</h5>
    
    <b>Emojis</b><br />
    <div id="labeledEmojis">
        <c:if test="${empty labeledEmojis}">
            None
        </c:if>
        <c:forEach var="emoji" items="${labeledEmojis}">
            <a href="<spring:url value='/content/emoji/edit/${emoji.id}' />">
                <span style="font-size: 6em;">${emoji.glyph}</span>
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b>Images</b><br />
    <div id="labeledImages">
        <c:if test="${empty labeledImages}">
            None
        </c:if>
        <c:forEach var="image" items="${labeledImages}">
            <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                <img src="<spring:url value='${image.url}' />" alt="${image.title}" />
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b>Videos</b><br />
    <div id="labeledVideos">
        // TODO
    </div>
    <br />
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center">Storybook paragraphs containing word (${fn:length(storyBookParagraphsContainingWord)})</h5>
    <c:forEach var="storyBookParagraph" items="${storyBookParagraphsContainingWord}">
        <p>
            <c:set var="wordTextInBold" value="<span class='diff-highlight'>${word.text}</span>" />
             "${fn:replace(storyBookParagraph.originalText, word.text, wordTextInBold)}"<br />
            <a href="<spring:url value='/content/storybook/edit/${storyBookParagraph.storyBookChapter.storyBook.id}#ch-id-${storyBookParagraph.storyBookChapter.id}' />" target="_blank"><c:out value="${storyBookParagraph.storyBookChapter.storyBook.title}" /></a>
        </p>
    </c:forEach>
</content:aside>
