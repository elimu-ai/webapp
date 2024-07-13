<content:title>
    <fmt:message key="edit.letter.sound.correspondence" />
</content:title>

<content:section cssId="letterSoundEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letterSound">
            <tag:formErrors modelAttribute="letterSoundCorrespondence" />
            
            <form:hidden path="revisionNumber" value="${letterSound.revisionNumber}" />
            <form:hidden path="usageCount" value="${letterSound.usageCount}" />
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
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty letterSoundContributionEvents) 
                  && (letterSoundContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5><fmt:message key="peer.review" /> 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/letter-sound-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                <fmt:message key="do.you.approve.quality.of.this.letter.sound.correspondence?" />
            </p>
            
            <input type="hidden" name="letterSoundContributionEventId" value="${letterSoundContributionEvents[0].id}" />
            
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
        <c:forEach var="letterSoundContributionEvent" items="${letterSoundContributionEvents}">
            <a name="contribution-event_${letterSoundContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    <fmt:message key="revision" /> #${letterSoundContributionEvent.revisionNumber} 
                    (<fmt:formatNumber maxFractionDigits="0" value="${letterSoundContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:formatDate value="${letterSoundContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                </span>
                <a href="<spring:url value='/content/contributor/${letterSoundContributionEvent.contributor.id}' />">
                    <div class="chip">
                        <c:choose>
                            <c:when test="${not empty letterSoundContributionEvent.contributor.imageUrl}">
                                <img src="${letterSoundContributionEvent.contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty letterSoundContributionEvent.contributor.providerIdWeb3}">
                                <img src="https://effigy.im/a/<c:out value="${letterSoundContributionEvent.contributor.providerIdWeb3}" />.png" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty letterSoundContributionEvent.contributor.firstName}">
                                <c:out value="${letterSoundContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${letterSoundContributionEvent.contributor.lastName}" />
                            </c:when>
                            <c:when test="${not empty letterSoundContributionEvent.contributor.providerIdWeb3}">
                                ${fn:substring(letterSoundContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(letterSoundContributionEvent.contributor.providerIdWeb3, 38, 42)}
                            </c:when>
                        </c:choose>
                    </div>
                </a>
                <c:if test="${not empty letterSoundContributionEvent.comment}">
                    <blockquote><c:out value="${letterSoundContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="letterSoundPeerReviewEvent" items="${letterSoundPeerReviewEvents}">
                    <c:if test="${letterSoundPeerReviewEvent.letterSoundContributionEvent.id == letterSoundContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${letterSoundPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <a href="<spring:url value='/content/contributor/${letterSoundPeerReviewEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty letterSoundPeerReviewEvent.contributor.imageUrl}">
                                                <img src="${letterSoundPeerReviewEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty letterSoundPeerReviewEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${letterSoundPeerReviewEvent.contributor.providerIdWeb3}" />.png" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty letterSoundPeerReviewEvent.contributor.firstName}">
                                                <c:out value="${letterSoundPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${letterSoundPeerReviewEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty letterSoundPeerReviewEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(letterSoundPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(letterSoundPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${letterSoundPeerReviewEvent.isApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${letterSoundPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty letterSoundPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${letterSoundPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
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
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center"><fmt:message key="usages" /></h5>
    
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="word" /></th>
            <th><fmt:message key="frequency" /></th>
        </thead>
        <tbody>
            <c:forEach var="word" items="${words}">
                <%-- Check if the current letter-sound correspondence is used by the word. --%>
                <c:set var="isUsedByWord" value="false" />
                <c:forEach var="lsc" items="${word.letterSounds}">
                    <c:if test="${lsc.id == letterSound.id}">
                        <c:set var="isUsedByWord" value="true" />
                    </c:if>
                </c:forEach>
                <c:if test="${isUsedByWord}">
                    <tr>
                        <td>
                            <a href="<spring:url value='/content/word/edit/${word.id}' />">
                                "<c:out value="${word.text}" />"
                            </a><br />
                            "<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:if test="${lsc.id == letterSound.id}"><span class='diff-highlight'></c:if><c:forEach var="letter" items="${lsc.letters}">${letter.text}<c:out value=" " /></c:forEach><c:if test="${lsc.id == letterSound.id}"></span></c:if></a>&nbsp;</c:forEach>"<br />
                            <span class="grey-text">
                                /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:if test="${lsc.id == letterSound.id}"><span class='diff-highlight'></c:if><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach><c:if test="${lsc.id == letterSound.id}"></span></c:if></a>&nbsp;</c:forEach>/
                            </span>
                        </td>
                        <td>${word.usageCount}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
