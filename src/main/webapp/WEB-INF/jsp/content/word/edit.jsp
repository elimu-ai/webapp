<content:title>
    <fmt:message key="edit.word" />
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
            <fmt:message key="peer.review" />: ${word.peerReviewStatus}
        </a>
    </div>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />
            
            <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
            <form:hidden path="usageCount" value="${word.usageCount}" />
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
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution (for example a URL pointing to the source you used for determining word's IPA transcription)."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <sec:authorize access="hasRole('ROLE_EDITOR')">
                <a href="<spring:url value='/content/word/delete/${word.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
            </sec:authorize>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty wordContributionEvents) 
                  && (wordContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5><fmt:message key="peer.review" /> 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/word-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                <fmt:message key="do.you.approve.quality.of.this.word?" />
            </p>
            
            <input type="hidden" name="wordContributionEventId" value="${wordContributionEvents[0].id}" />
            
            <input type="radio" id="approved_true" name="approved" value="true" />
            <label for="approved_true"><fmt:message key="yes" /> (approve)</label><br />

            <input type="radio" id="approved_false" name="approved" value="false" />
            <label for="approved_false"><fmt:message key="no" /> (request changes)</label><br />
            
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
                <label for="comment"><fmt:message key="comment" /></label>
                <textarea id="comment" name="comment" class="materialize-textarea"></textarea>

                <button class="btn waves-effect waves-light" type="submit">
                    <fmt:message key="submit" /> <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
        
        <div class="divider" style="margin: 2em 0;"></div>
    </c:if>
    
    <a name="contribution-events"></a>
    <h5><fmt:message key="contributions" /> 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="wordContributionEvent" items="${wordContributionEvents}">
            <div class="collection-item">
                <span class="badge">
                    <fmt:formatDate value="${wordContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                    (<fmt:formatNumber maxFractionDigits="0" value="${wordContributionEvent.timeSpentMs / 1000 / 60}" /> min)
                </span>
                <div class="chip">
                    <img src="<spring:url value='${wordContributionEvent.contributor.imageUrl}' />" alt="${wordContributionEvent.contributor.firstName}" /> 
                    <c:out value="${wordContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${wordContributionEvent.contributor.lastName}" />
                </div>
                <blockquote><c:out value="${wordContributionEvent.comment}" /></blockquote>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="wordPeerReviewEvent" items="${wordPeerReviewEvents}">
                    <c:if test="${wordPeerReviewEvent.wordContributionEvent.id == wordContributionEvent.id}">
                        <div class="row peerReviewEvent" data-approved="${wordPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <div class="chip">
                                    <img src="<spring:url value='${wordPeerReviewEvent.contributor.imageUrl}' />" alt="${wordPeerReviewEvent.contributor.firstName}" /> 
                                    <c:out value="${wordPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${wordPeerReviewEvent.contributor.lastName}" />
                                </div>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${wordPeerReviewEvent.isApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${wordPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty wordPeerReviewEvent.comment}">
                                <div class="col s12">
                                    "<c:out value="${wordPeerReviewEvent.comment}" />"
                                </div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="audio" /></h5>
    <c:choose>
        <c:when test="${empty audio}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This word has no corresponding audio.<br />
                <a href="<spring:url value='/content/multimedia/audio/create?autoFillTranscription=${word.text}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
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
            
            <div class="divider" style="margin: 1.5em 0;"></div>
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
    
    <c:if test="${not empty wordInflections}">
        <div class="divider" style="margin: 1.5em 0;"></div>

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
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
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
