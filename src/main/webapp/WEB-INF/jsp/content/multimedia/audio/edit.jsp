<content:title>
    <fmt:message key="edit.audio" />
</content:title>

<content:section cssId="audioEditPage">
    <c:choose>
        <c:when test="${audio.peerReviewStatus == 'APPROVED'}">
            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
        </c:when>
        <c:when test="${audio.peerReviewStatus == 'NOT_APPROVED'}">
            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
        </c:when>
        <c:otherwise>
            <c:set var="peerReviewStatusColor" value="" />
        </c:otherwise>
    </c:choose>
    <div class="chip right ${peerReviewStatusColor}" style="margin-top: 1.14rem;">
        <a href="#contribution-events">
            <fmt:message key="peer.review" />: ${audio.peerReviewStatus}
        </a>
    </div>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="audio" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="audio" />
            
            <form:hidden path="revisionNumber" value="${audio.revisionNumber}" />
            <form:hidden path="audioFormat" value="${audio.audioFormat}" />
            <form:hidden path="contentType" value="${audio.contentType}" />
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <select id="word" name="word">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}" <c:if test="${word.id == audio.word.id}">selected="selected"</c:if>>${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /></option>
                        </c:forEach>
                    </select>
                    <label for="word"><fmt:message key="word" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="storyBookParagraph" name="storyBookParagraph">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="storyBookParagraph" items="${storyBookParagraphs}">
                            <option value="${storyBookParagraph.id}" <c:if test="${storyBookParagraph.id == audio.storyBookParagraph.id}">selected="selected"</c:if>>${storyBookParagraph.id} (<c:out value="${storyBookParagraph.storyBookChapter.storyBook.title}" />)</option>
                        </c:forEach>
                    </select>
                    <label for="storyBookParagraph"><fmt:message key="storybook.paragraph" /></label>
                </div>
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="transcription" cssErrorClass="error"><fmt:message key='transcription' /></form:label>
                    <form:textarea path="transcription" cssClass="materialize-textarea" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == audio.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense"><fmt:message key="content.license" /></label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <%--
                <div class="col s12 m6">
                    <blockquote>
                        <fmt:message key="what.literacy.skills" />
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(audio.literacySkills, literacySkill)}">checked="checked"</c:if> />
                        <label for="${literacySkill}">
                            <fmt:message key="literacy.skill.${literacySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        <fmt:message key="what.numeracy.skills" />
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(audio.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                --%>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span><fmt:message key='file' /></span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
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
            <a href="<spring:url value='/content/multimedia/audio/delete/${audio.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <%-- Display peer review form if the current contributor is not the same as that of the latest contribution event --%>
    <c:if test="${(not empty audioContributionEvents) 
                  && (audioContributionEvents[0].contributor.id != contributor.id)}">
        <a name="peer-review"></a>
        <h5><fmt:message key="peer.review" /> 🕵🏽‍♀📖️️️️</h5>
        
        <form action="<spring:url value='/content/audio-peer-review-event/create' />" method="POST" class="card-panel">
            <p>
                <fmt:message key="do.you.approve.quality.of.this.audio?" />
            </p>
            
            <input type="hidden" name="audioContributionEventId" value="${audioContributionEvents[0].id}" />
            
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
                <textarea id="comment" name="comment" class="materialize-textarea" maxlength="1000"></textarea>

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
        <c:forEach var="audioContributionEvent" items="${audioContributionEvents}">
            <div class="collection-item">
                <span class="badge">
                    <fmt:message key="revision" /> #${audioContributionEvent.revisionNumber} 
                    (<fmt:formatNumber maxFractionDigits="0" value="${audioContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:formatDate value="${audioContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                </span>
                <a href="<spring:url value='/content/contributor/${audioContributionEvent.contributor.id}' />">
                    <div class="chip">
                        <c:choose>
                            <c:when test="${not empty audioContributionEvent.contributor.imageUrl}">
                                <img src="${audioContributionEvent.contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty audioContributionEvent.contributor.providerIdWeb3}">
                                <img src="https://effigy.im/a/<c:out value="${audioContributionEvent.contributor.providerIdWeb3}" />.png" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty audioContributionEvent.contributor.firstName}">
                                <c:out value="${audioContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${audioContributionEvent.contributor.lastName}" />
                            </c:when>
                            <c:when test="${not empty audioContributionEvent.contributor.providerIdWeb3}">
                                ${fn:substring(audioContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(audioContributionEvent.contributor.providerIdWeb3, 38, 42)}
                            </c:when>
                        </c:choose>
                    </div>
                </a>
                <c:if test="${not empty audioContributionEvent.comment}">
                    <blockquote><c:out value="${audioContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="audioPeerReviewEvent" items="${audioPeerReviewEvents}">
                    <c:if test="${audioPeerReviewEvent.audioContributionEvent.id == audioContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${audioPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <a href="<spring:url value='/content/contributor/${audioPeerReviewEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty audioPeerReviewEvent.contributor.imageUrl}">
                                                <img src="${audioPeerReviewEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty audioPeerReviewEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${audioPeerReviewEvent.contributor.providerIdWeb3}" />.png" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty audioPeerReviewEvent.contributor.firstName}">
                                                <c:out value="${audioPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${audioPeerReviewEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty audioPeerReviewEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(audioPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(audioPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${audioPeerReviewEvent.isApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${audioPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty audioPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${audioPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <audio controls="true" autoplay="true">
        <source src="<spring:url value='/audio/${audio.id}_r${audio.revisionNumber}.${fn:toLowerCase(audio.audioFormat)}' />" />
    </audio>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center"><fmt:message key="content.labels" /></h5>
    
    <b><fmt:message key="letters" /></b><br />
    <div id="progressLetters" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="letterLabelContainer">
        <c:forEach var="letter" items="${audio.letters}">
            <div class="chip" data-letterid="${letter.id}">
                ${letter.text} 
                <a href="#" class="letterDeleteLink" data-letterid="${letter.id}">
                    <i class="close material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="letterId" class="browser-default">
        <option value="">-- <fmt:message key='add.letter' /> --</option>
        <c:forEach var="letter" items="${letters}">
            <option value="${letter.id}"><c:out value="${letter.text}" /></option>
        </c:forEach>
    </select>
    <script>
        $(function() {
            $('#letterId').on('change', function() {
                console.info('#letterId on change');
                var letterId = $(this).val();
                console.info('letterId: ' + letterId);
                var letterText = $(this).find('option[value="' + letterId + '"]').html();
                console.info('letterText ' + letterText);
                if (letterId != '') {
                    $('#progressLetters').show();
                    
                    var jqXHR = $.ajax({
                        type: "POST",
                        url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/add-content-label?letterId=" + letterId
                    });
                    jqXHR.done(function() {
                        console.info('letterId ajax done');
                        $('#letterLabelContainer').append('<div class="chip">' + letterText + '</div>');
                    });
                    jqXHR.fail(function() {
                        console.info('letterId ajax error');
                        
                    });
                    jqXHR.always(function() {
                        console.info('letterId ajax always');
                        $('#progressLetters').hide();
                    });
                }
            });
            
            $('.letterDeleteLink').on('click', function(event) {
                console.info('.letterDeleteLink on click');
                event.preventDefault();
                var $link = $(this);
                var letterId = $link.attr('data-letterid');
                console.info('letterId: ' + letterId);
                $('#progressLetters').show();

                var jqXHR = $.ajax({
                    type: "POST",
                    url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/remove-content-label?letterId=" + letterId
                });
                jqXHR.done(function() {
                    console.info('letterId ajax done');
                    $('.chip[data-letterid="' + letterId + '"]').remove();
                });
                jqXHR.fail(function() {
                    console.info('letterId ajax error');

                });
                jqXHR.always(function() {
                    console.info('letterId ajax always');
                    $('#progressLetters').hide();
                });
            });
        });
    </script>
    
    <b><fmt:message key="numbers" /></b><br />
    <div id="progressNumbers" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="numberLabelContainer">
        <c:forEach var="number" items="${audio.numbers}">
            <div class="chip" data-numberid="${number.id}">
                <a href="<spring:url value='/content/number/edit/${number.id}' />">
                    ${number.value}<c:if test="${not empty number.symbol}"> (${number.symbol})</c:if>
                </a>
                <a href="#" class="numberDeleteLink" data-numberid="${number.id}">
                    <i class="close material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="numberId" class="browser-default">
        <option value="">-- <fmt:message key='add.number' /> --</option>
        <c:forEach var="number" items="${numbers}">
            <option value="${number.id}"><c:out value="${number.value}" /><c:if test="${not empty number.symbol}"> (${number.symbol})</c:if></option>
        </c:forEach>
    </select>
    <script>
        $(function() {
            $('#numberId').on('change', function() {
                console.info('#numberId on change');
                var numberId = $(this).val();
                console.info('numberId: ' + numberId);
                var numberText = $(this).find('option[value="' + numberId + '"]').html();
                console.info('numberText ' + numberText);
                if (numberId != '') {
                    $('#progressNumbers').show();
                    
                    var jqXHR = $.ajax({
                        type: "POST",
                        url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/add-content-label?numberId=" + numberId
                    });
                    jqXHR.done(function() {
                        console.info('numberId ajax done');
                        $('#numberLabelContainer').append('<div class="chip">' + numberText + '</div>');
                    });
                    jqXHR.fail(function() {
                        console.info('numberId ajax error');
                        
                    });
                    jqXHR.always(function() {
                        console.info('numberId ajax always');
                        $('#progressNumbers').hide();
                    });
                }
            });
            
            $('.numberDeleteLink').on('click', function(event) {
                console.info('.numberDeleteLink on click');
                event.preventDefault();
                var $link = $(this);
                var numberId = $link.attr('data-numberid');
                console.info('numberId: ' + numberId);
                $('#progressNumbers').show();

                var jqXHR = $.ajax({
                    type: "POST",
                    url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/remove-content-label?numberId=" + numberId
                });
                jqXHR.done(function() {
                    console.info('numberId ajax done');
                    $('.chip[data-numberid="' + numberId + '"]').remove();
                });
                jqXHR.fail(function() {
                    console.info('numberId ajax error');

                });
                jqXHR.always(function() {
                    console.info('numberId ajax always');
                    $('#progressNumbers').hide();
                });
            });
        });
    </script>
    
    <b><fmt:message key="words" /></b><br />
    <div id="progressWords" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="wordLabelContainer">
        <c:forEach var="word" items="${audio.words}">
            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                <a href="<spring:url value='/content/word/edit/${word.id}' />">
                    ${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if>
                </a>
                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                    <i class="close material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="wordId" class="browser-default">
        <option value="">-- <fmt:message key='add.word' /> --</option>
        <c:forEach var="word" items="${words}">
            <option value="${word.id}"><c:out value="${word.text}" /><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if></option>
        </c:forEach>
    </select>
    <script>
        $(function() {
            $('#wordId').on('change', function() {
                console.info('#wordId on change');
                var wordId = $(this).val();
                console.info('wordId: ' + wordId);
                var wordText = $(this).find('option[value="' + wordId + '"]').html();
                console.info('wordText ' + wordText);
                if (wordId != '') {
                    $('#progressWords').show();
                    
                    var jqXHR = $.ajax({
                        type: "POST",
                        url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/add-content-label?wordId=" + wordId
                    });
                    jqXHR.done(function() {
                        console.info('wordId ajax done');
                        $('#wordLabelContainer').append('<div class="chip">' + wordText + '</div>');
                    });
                    jqXHR.fail(function() {
                        console.info('wordId ajax error');
                        
                    });
                    jqXHR.always(function() {
                        console.info('wordId ajax always');
                        $('#progressWords').hide();
                    });
                }
            });
            
            $('.wordDeleteLink').on('click', function(event) {
                console.info('.wordDeleteLink on click');
                event.preventDefault();
                var $link = $(this);
                var wordId = $link.attr('data-wordid');
                console.info('wordId: ' + wordId);
                $('#progressWords').show();

                var jqXHR = $.ajax({
                    type: "POST",
                    url: "<spring:url value='/content/multimedia/audio/edit/${audio.id}' />/remove-content-label?wordId=" + wordId
                });
                jqXHR.done(function() {
                    console.info('wordId ajax done');
                    $('.chip[data-wordid="' + wordId + '"]').remove();
                });
                jqXHR.fail(function() {
                    console.info('wordId ajax error');

                });
                jqXHR.always(function() {
                    console.info('wordId ajax always');
                    $('#progressWords').hide();
                });
            });
        });
    </script>
</content:aside>
