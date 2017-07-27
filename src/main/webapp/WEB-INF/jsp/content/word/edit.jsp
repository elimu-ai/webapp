<content:title>
    <fmt:message key="edit.word" />
</content:title>

<content:section cssId="wordEditPage">
    <h4><content:gettitle /></h4>
    
    <c:choose>
        <c:when test="${empty audio}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This word has no corresponding audio.
                <a href="<spring:url value='/content/multimedia/audio/create?transcription=${word.text}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
            </div>
        </c:when>
        <c:otherwise>
            <audio controls="true" autoplay="true">
                <source src="<spring:url value='/audio/${audio.id}.${fn:toLowerCase(audio.audioFormat)}' />" />
            </audio>
        </c:otherwise>
    </c:choose>
    
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />

            <div class="row">
                <form:hidden path="locale" value="${word.locale}" />
                <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
                <form:hidden path="usageCount" value="${word.usageCount}" />
                
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="phonetics" cssErrorClass="error"><fmt:message key='phonetics' /> (IPA)</form:label>
                    <form:input path="phonetics" cssErrorClass="error" />
                    <div id="allophonesContainer">
                        <c:forEach var="allophone" items="${allophones}">
                            <a href="#" class="allophone chip" data-valuesampa="${allophone.valueSampa}">${allophone.valueIpa}</a>
                            <audio id="audio_sampa_${allophone.valueSampa}">
                                <source src="<spring:url value='/static/audio/${locale.language}/sampa_${allophone.valueSampa}.wav' />" />
                            </audio>
                        </c:forEach>
                        <script>
                            $(function() {
                                // Append IPA value to text field
                                $('#allophonesContainer .chip').click(function(event) {
                                    console.info('#allophonesContainer .chip click');
                                    event.preventDefault();
                                    var valueIpa = $(this).html();
                                    console.info('valueIpa: ' + valueIpa);
                                    $('#phonetics').val($('#phonetics').val() + valueIpa);
                                    $('#phonetics').focus();
                                });
                                
                                // Play sound when hovering its IPA value
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
                </div>
                
                <p>&nbsp;</p>
                
                <div class="input-field col s12">
                    <select id="spellingConsistency" name="spellingConsistency">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="spellingConsistency" items="${spellingConsistencies}">
                            <option value="${spellingConsistency}" <c:if test="${spellingConsistency == word.spellingConsistency}">selected="selected"</c:if>><fmt:message key="spelling.consistency.${spellingConsistency}" /></option>
                        </c:forEach>
                    </select>
                    <label for="spellingConsistency"><fmt:message key="spelling.consistency" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="wordType" name="wordType">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="wordType" items="${wordTypes}">
                            <option value="${wordType}" <c:if test="${wordType == word.wordType}">selected="selected"</c:if>>${wordType}</option>
                        </c:forEach>
                    </select>
                    <label for="wordType"><fmt:message key="word.type" /></label>
                </div>
                
                <div class="input-field col s12">
                    <textarea id="comment" name="comment" class="materialize-textarea" maxlength="255"><c:if test="${not empty param.comment}">${param.comment}</c:if></textarea>
                    <label for="comment"><fmt:message key="comment.about.the.change" /></label>
                </div>
            </div>

            <button id="submitButton" class="btn green waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/word/delete/${word.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider"></div>
    
    <h5><fmt:message key="revisions" /></h5>
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="revision" /></th>
            <th><fmt:message key="content" /></th>
            <th><fmt:message key="time" /></th>
            <th><fmt:message key="contributor" /></th>
        </thead>
        <tbody>
            <c:forEach var="wordRevisionEvent" items="${wordRevisionEvents}" varStatus="status">
                <tr>
                    <td>${fn:length(wordRevisionEvents) - status.index}</td>
                    <td>
                        <fmt:message key='text' />: "${wordRevisionEvent.text}"<br />
                        IPA: /${wordRevisionEvent.phonetics}/
                        
                        <c:if test="${not empty wordRevisionEvent.comment}">
                            <blockquote>
                                "<c:out value="${wordRevisionEvent.comment}" />"
                            </blockquote>
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${wordRevisionEvent.calendar.time}" type="both" timeStyle="short" /></td>
                    <td>
                        <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                            <div class="chip">
                                <img src="<spring:url value='${wordRevisionEvent.contributor.imageUrl}' />" alt="${wordRevisionEvent.contributor.firstName}" /> 
                                <c:out value="${wordRevisionEvent.contributor.firstName}" />&nbsp;<c:out value="${wordRevisionEvent.contributor.lastName}" />
                            </div>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <div class="previewContainer valignwrapper">
        <img src="<spring:url value='/static/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContentContainer">
            <div id="previewContent" class="previewContentGrapheme">

            </div>
        </div>
    </div>
    <script>
        $(function() {
            initializePreview();
            
            $('#text').on("change", function() {
                console.debug('#text on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var value = $('#text').val();
                if ((value != undefined) && (value != "")) {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center"><fmt:message key="labeled.content" /></h5>
    
    <b><fmt:message key="audios" /></b><br />
    <br />
    
    <b><fmt:message key="images" /></b><br />
    <div id="labeledImages">
        <c:forEach var="image" items="${labeledImages}">
            <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                <img src="<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageFormat)}' />" alt="${image.title}" />
            </a>
        </c:forEach>
    </div>
    <br />
    
    <b><fmt:message key="videos" /></b><br />
</content:aside>
