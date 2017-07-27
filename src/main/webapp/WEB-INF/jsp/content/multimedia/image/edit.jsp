<content:title>
    <fmt:message key="edit.image" />
</content:title>

<content:section cssId="imageEditPage">
    <h4><content:gettitle /></h4>
    
    <c:choose>
        <c:when test="${empty audio}">
            <div class="card-panel amber lighten-3">
                <b>Warning:</b> This image has no corresponding audio.
                <a href="<spring:url value='/content/multimedia/audio/create?transcription=${image.title}' />" target="_blank"><fmt:message key="add.audio" /> <i class="material-icons">launch</i></a>
            </div>
        </c:when>
        <c:otherwise>
            <audio controls="true" autoplay="true">
                <source src="<spring:url value='/audio/${audio.id}.${fn:toLowerCase(audio.audioFormat)}' />" />
            </audio>
        </c:otherwise>
    </c:choose>
    
    <div class="card-panel">
        <form:form modelAttribute="image" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="image" />

            <div class="row">
                <form:hidden path="locale" value="${image.locale}" />
                <form:hidden path="revisionNumber" value="${image.revisionNumber}" />
                <form:hidden path="imageFormat" value="${number.imageFormat}" />
                <form:hidden path="contentType" value="${number.contentType}" />
                <form:hidden path="dominantColor" value="${number.dominantColor}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == image.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense"><fmt:message key="content.license" /></label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        <fmt:message key="what.literacy.skills" />
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(image.literacySkills, literacySkill)}">checked="checked"</c:if> />
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
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(image.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
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

            <button id="submitButton" class="btn orange waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/multimedia/image/delete/${image.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider"></div>
    
    <h5><fmt:message key="revisions" /></h5>
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="revision" /></th>
            <th><fmt:message key="time" /></th>
            <th><fmt:message key="contributor" /></th>
        </thead>
        <tbody>
            <c:forEach var="contentCreationEvent" items="${contentCreationEvents}" varStatus="status">
                <tr>
                    <td>#${fn:length(contentCreationEvents) - status.index}</td>
                    <td><fmt:formatDate value="${contentCreationEvent.calendar.time}" type="both" timeStyle="short" /></td>
                    <td>
                        <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                            <div class="chip">
                                <img src="<spring:url value='${contentCreationEvent.contributor.imageUrl}' />" alt="${contentCreationEvent.contributor.firstName}" /> 
                                <c:out value="${contentCreationEvent.contributor.firstName}" />&nbsp;<c:out value="${contentCreationEvent.contributor.lastName}" />
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
            <div id="previewContent" class="valign-wrapper" 
                 style="
                    background-image: url(<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageFormat)}' />);
                 ">
                <h5 class="white-text" style="
                    position: absolute; 
                    bottom: 0; 
                    width: 100%;
                    font-size: 2rem;
                    letter-spacing: .5em;
                    text-shadow: 1px 1px rgba(0,0,0, .8);
                    /*background-color: rgba(0,0,0, .1);*/
                    ">${image.title}</h5>
            </div>
        </div>
    </div>
    
    <script>
        $(function() {
            console.debug("dominantColor: ${image.dominantColor}");
            $('#previewContent').css("background-color", "${image.dominantColor}");
            $('nav').removeClass("black");
            $('nav').css("background-color", "${image.dominantColor}");
        });
    </script>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center"><fmt:message key="content.labels" /></h5>
    
    <b><fmt:message key="letters" /></b><br />
    <div id="progressLetters" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="letterLabelContainer">
        <c:forEach var="letter" items="${image.letters}">
            <div class="chip" data-letterid="${letter.id}">
                ${letter.text} 
                <a href="#" class="letterDeleteLink" data-letterid="${letter.id}">
                    <i class="material-icons">clear</i>
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
                        url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/add-content-label?letterId=" + letterId
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
                    url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/remove-content-label?letterId=" + letterId
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
        <c:forEach var="number" items="${image.numbers}">
            <div class="chip" data-numberid="${number.id}">
                ${number.value} 
                <a href="#" class="numberDeleteLink" data-numberid="${number.id}">
                    <i class="material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="numberId" class="browser-default">
        <option value="">-- <fmt:message key='add.number' /> --</option>
        <c:forEach var="number" items="${numbers}">
            <option value="${number.id}"><c:out value="${number.value}" /></option>
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
                        url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/add-content-label?numberId=" + numberId
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
                    url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/remove-content-label?numberId=" + numberId
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
        <c:forEach var="word" items="${image.words}">
            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                ${word.text} 
                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                    <i class="material-icons">clear</i>
                </a>
            </div>
        </c:forEach>
    </div>
    <select id="wordId" class="browser-default">
        <option value="">-- <fmt:message key='add.word' /> --</option>
        <c:forEach var="word" items="${words}">
            <option value="${word.id}"><c:out value="${word.text}" /></option>
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
                        url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/add-content-label?wordId=" + wordId
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
                    url: "<spring:url value='/content/multimedia/image/edit/${image.id}' />/remove-content-label?wordId=" + wordId
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
