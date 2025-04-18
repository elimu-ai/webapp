<content:title>
    Edit video
</content:title>

<content:section cssId="videoEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel cid-${video.checksumGitHub != null}">
        <video poster="<spring:url value='/video/${video.id}_r${video.revisionNumber}_thumbnail.png' />" controls>
            <source src="<spring:url value='${video.url}' />" />
        </video>

        <form:form modelAttribute="video" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="video" />

            <div class="row">
                <form:hidden path="revisionNumber" value="${video.revisionNumber}" />
                <form:hidden path="videoFormat" value="${video.videoFormat}" />
                <form:hidden path="contentType" value="${video.contentType}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error">Title</form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- Select --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == video.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense">Content license</label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error">Attribution URL</form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        What <i>literacy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(video.literacySkills, literacySkill)}">checked="checked"</c:if> />
                        <label for="${literacySkill}">
                            <c:choose>
                                <c:when test="${literacySkill == 'CONCEPTS_ABOUT_PRINT'}">Concepts about print</c:when>
                                <c:when test="${literacySkill == 'PHONEMIC_AWARENESS'}">Phonemic awareness</c:when>
                                <c:when test="${literacySkill == 'ORAL_VOCABULARY'}">Oral vocabulary</c:when>
                                <c:when test="${literacySkill == 'LISTENING_COMPREHENSION'}">Listening comprehension</c:when>
                                <c:when test="${literacySkill == 'LETTER_IDENTIFICATION'}">Letter identification</c:when>
                                <c:when test="${literacySkill == 'SYLLABLE_NAMING'}">Syllable naming</c:when>
                                <c:when test="${literacySkill == 'NONWORD_READING'}">Nonword Reading</c:when>
                                <c:when test="${literacySkill == 'FAMILIAR_WORD_READING'}">Familiar word reading</c:when>
                                <c:when test="${literacySkill == 'ORAL_READING_FLUENCY'}">Oral reading fluency</c:when>
                                <c:when test="${literacySkill == 'DICTATION'}">Dictation (Sentence writing)</c:when>
                                <c:when test="${literacySkill == 'MAZE_CLOZE'}">Maze/Cloze (Reading comprehension)</c:when>                                    
                            </c:choose>
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        What <i>numeracy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(video.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <c:choose>
                                <c:when test="${numeracySkill == 'ORAL_COUNTING'}">Oral counting</c:when>
                                <c:when test="${numeracySkill == 'ONE_TO_ONE_CORRESPONDENCE'}">One-to-one correspondence</c:when>
                                <c:when test="${numeracySkill == 'NUMBER_IDENTIFICATION'}">Number identification</c:when>
                                <c:when test="${numeracySkill == 'QUANTITY_DISCRIMINATION'}">Quantity discrimination</c:when>                                    
                                <c:when test="${numeracySkill == 'MISSING_NUMBER'}">Missing number</c:when>                                    
                                <c:when test="${numeracySkill == 'ADDITION'}">Addition</c:when>                                    
                                <c:when test="${numeracySkill == 'SUBTRACTION'}">Subtraction</c:when>                                    
                                <c:when test="${numeracySkill == 'MULTIPLICATION'}">Multiplication</c:when>                                    
                                <c:when test="${numeracySkill == 'WORD_PROBLEMS'}">Word problems</c:when>                                    
                                <c:when test="${numeracySkill == 'SHAPE_IDENTIFICATION'}">Shape identification</c:when>                                    
                                <c:when test="${numeracySkill == 'SHAPE_NAMING'}">Shape naming</c:when>                                    
                            </c:choose>
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span>File (M4V/MP4)</span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span>Thumbnail (PNG)</span>
                        <form:input path="thumbnail" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                Edit <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/multimedia/video/delete/${video.id}' />" class="waves-effect waves-red red-text btn-flat right">Delete</a>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Content labels</h5>
    
    <b>Letters</b><br />
    <div id="progressLetters" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="letterLabelContainer">
        <c:forEach var="letter" items="${video.letters}">
            <div class="chip" data-letterid="${letter.id}">
                ${letter.text} 
                <a href="#" class="letterDeleteLink" data-letterid="${letter.id}">
                    <i class="close material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="letterId" class="browser-default">
        <option value="">-- Add letter --</option>
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
                        url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/add-content-label?letterId=" + letterId
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
                    url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/remove-content-label?letterId=" + letterId
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
    
    <b>Numbers</b><br />
    <div id="progressNumbers" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="numberLabelContainer">
        <c:forEach var="number" items="${video.numbers}">
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
        <option value="">-- Add number --</option>
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
                        url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/add-content-label?numberId=" + numberId
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
                    url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/remove-content-label?numberId=" + numberId
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
    
    <b>Words</b><br />
    <div id="progressWords" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="wordLabelContainer">
        <c:forEach var="word" items="${video.words}">
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
                        url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/add-content-label?wordId=" + wordId
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
                    url: "<spring:url value='/content/multimedia/video/edit/${video.id}' />/remove-content-label?wordId=" + wordId
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

    <div class="divider" style="margin-bottom: 1em;"></div>

    <label>checksum_md5</label><br />
    <code>${video.checksumMd5}</code><br />
    <br />

    <label>file_url</label><br />
    <code>${video.url}</code><br />
    <br />

    <label>checksum_github</label><br />
    <code>${video.checksumGitHub}</code><br />
</content:aside>
