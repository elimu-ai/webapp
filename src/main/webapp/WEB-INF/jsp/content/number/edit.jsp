
<content:title>
    Edit number
</content:title>

<content:section cssId="numberEditPage">
    <a href="#contribution-events" class="right" style="margin-top: 1.75rem;">
        <span class="peerReviewStatusContainer" data-status="${number.peerReviewStatus}">
            Peer-review: <code>${number.peerReviewStatus}</code>
        </span>
    </a>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />
            
            <form:hidden path="revisionNumber" value="${number.revisionNumber}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error">Value (Number)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <form:label path="symbol" cssErrorClass="error">Symbol</form:label>
                    <form:input path="symbol" cssErrorClass="error" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label>Number word(s)</label>
                    <div id="numberWordsContainer">
                        <c:forEach var="word" items="${number.words}">
                            <input name="words" type="hidden" value="${word.id}" />
                            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">
                                    ${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" />
                                </a>
                                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.wordDeleteLink').on("click", function() {
                                    console.log('.wordDeleteLink on click');
                                    
                                    var wordId = $(this).attr("data-wordid");
                                    console.log('wordId: ' + wordId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="words"][value="' + wordId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </div>
                    
                    <select id="numberWords" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- Select --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}"><c:out value="${word.text}" /><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#numberWords').on("change", function() {
                                console.log('#numberWords on change');
                                
                                var wordId = $(this).val();
                                console.log('wordId: ' + wordId);
                                var wordText = $(this).find('option[value="' + wordId + '"]').text();
                                console.log('wordText: ' + wordText);
                                if (wordId != "") {
                                    $('#numberWordsContainer').append('<input name="words" type="hidden" value="' + wordId + '" />');
                                    $('#numberWordsContainer').append('<div class="chip">' + wordText + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/word/create' />" target="_blank">Add word <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/number/delete/${number.id}' />" class="waves-effect waves-red red-text btn-flat right">Delete</a>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty numberContributionEvents) 
                  && (numberContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5>Peer-review 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/number-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                Do you approve the quality of this number?
            </p>
            
            <input type="hidden" name="numberContributionEventId" value="${numberContributionEvents[0].id}" />
            
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
        <c:forEach var="numberContributionEvent" items="${numberContributionEvents}">
            <a name="contribution-event_${numberContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${numberContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${numberContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${numberContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                <c:if test="${not empty numberContributionEvent.comment}">
                    <blockquote><c:out value="${numberContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="numberPeerReviewEvent" items="${numberPeerReviewEvents}">
                    <c:if test="${numberPeerReviewEvent.numberContributionEvent.id == numberContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${numberPeerReviewEvent.getApproved()}">
                            <div class="col s4">
                                <c:set var="chipContributor" value="${numberPeerReviewEvent.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${numberPeerReviewEvent.getApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${numberPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty numberPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${numberPeerReviewEvent.comment}" /></div>
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

    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <div class="card-panel deep-purple lighten-5">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikibooks.org/wiki/Hindi/Numbers" target="_blank">Wikibooks</a>
                </li>
                <li>
                    <a href="https://omniglot.com/language/numbers/hindi.htm" target="_blank">Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
        <div class="card-panel deep-purple lighten-5">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Thai_numerals" target="_blank">Thai numerals - Wikipedia</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/language/numbers/thai.htm" target="_blank">Numbers in Thai - Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'VIE'}">
        <div class="card-panel deep-purple lighten-5">
            Vietnamese resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Vietnamese_numerals" target="_blank">Vietnamese numerals - Wikipedia</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/language/numbers/vietnamese.htm" target="_blank">Numbers in Vietnamese - Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
</content:aside>
