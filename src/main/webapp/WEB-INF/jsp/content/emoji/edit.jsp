<content:title>
    Edit emoji
</content:title>

<content:section cssId="emojiEditPage">
    <a href="#contribution-events" class="right" style="margin-top: 1.75rem;">
        <span class="peerReviewStatusContainer" data-status="${emoji.peerReviewStatus}">
            Peer-review: <code>${emoji.peerReviewStatus}</code>
        </span>
    </a>

    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="emoji">
            <tag:formErrors modelAttribute="emoji" />
            
            <form:hidden path="revisionNumber" value="${emoji.revisionNumber}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="glyph" cssErrorClass="error">Glyph</form:label>
                    <form:input path="glyph" cssErrorClass="error" placeholder="🦋" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="unicodeVersion" cssErrorClass="error">Unicode version</form:label>
                    <form:input path="unicodeVersion" cssErrorClass="error" placeholder="10.0" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="unicodeEmojiVersion" cssErrorClass="error">Unicode Emoji version</form:label>
                    <form:input path="unicodeEmojiVersion" cssErrorClass="error" placeholder="5.0" />
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/emoji/delete/${emoji.id}' />" class="waves-effect waves-red red-text btn-flat right">Delete</a>
        </form:form>
    </div>

    <div class="divider" style="margin: 2em 0;"></div>

    <a name="contribution-events"></a>
    <h5>Contributions 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="emojiContributionEvent" items="${emojiContributionEvents}">
            <a name="contribution-event_${emojiContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${emojiContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${emojiContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${emojiContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                <c:if test="${not empty emojiContributionEvent.comment}">
                    <blockquote><c:out value="${emojiContributionEvent.comment}" /></blockquote>
                </c:if>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <ul>
            <li>
                <a href="https://emojipedia.org/<c:out value='${emoji.glyph}' />" target="_blank">📙 Emojipedia</a>
            </li>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md#" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
        </ul>
    </div>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center">Content labels</h5>
    
    <b>Words</b><br />
    <div id="progressWords" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="wordLabelContainer">
        <c:forEach var="word" items="${emoji.words}">
            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                <a href="<spring:url value='/content/word/edit/${word.id}' />">
                    ${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" />
                </a>
                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                    <i class="close material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="wordId" class="browser-default">
        <option value="">-- Add word --</option>
        <c:forEach var="word" items="${words}">
            <option value="${word.id}"><c:out value="${word.text}" /><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /></option>
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
                        url: "<spring:url value='/content/emoji/edit/${emoji.id}' />/add-content-label?wordId=" + wordId
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
                    url: "<spring:url value='/content/emoji/edit/${emoji.id}' />/remove-content-label?wordId=" + wordId
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
