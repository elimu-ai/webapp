<content:title>
    <fmt:message key="edit.video" />
</content:title>

<content:section cssId="videoEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="video" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="video" />

            <div class="row">
                <form:hidden path="locale" value="${video.locale}" />
                <form:hidden path="revisionNumber" value="${video.revisionNumber}" />
                <form:hidden path="videoFormat" value="${number.videoFormat}" />
                <form:hidden path="contentType" value="${number.contentType}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == video.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
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
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(video.literacySkills, literacySkill)}">checked="checked"</c:if> />
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
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(video.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span><fmt:message key='file' /> (M4V/MP4)</span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span><fmt:message key='thumbnail' /> (PNG)</span>
                        <form:input path="thumbnail" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn deep-orange waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/multimedia/video/delete/${video.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
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
            <c:forEach var="videoRevisionEvent" items="${videoRevisionEvents}" varStatus="status">
                <tr>
                    <td>#${fn:length(videoRevisionEvents) - status.index}</td>
                    <td>
                        <fmt:message key='title' />: "${videoRevisionEvent.title}"
                        
                        <c:if test="${not empty videoRevisionEvent.comment}">
                            <blockquote>
                                "<c:out value="${videoRevisionEvent.comment}" />"
                            </blockquote>
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${videoRevisionEvent.calendar.time}" type="both" timeStyle="short" /></td>
                    <td>
                        <a href="<spring:url value='/content/community/contributors' />" target="_blank">
                            <div class="chip">
                                <img src="<spring:url value='${videoRevisionEvent.contributor.imageUrl}' />" alt="${videoRevisionEvent.contributor.firstName}" /> 
                                <c:out value="${videoRevisionEvent.contributor.firstName}" />&nbsp;<c:out value="${videoRevisionEvent.contributor.lastName}" />
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
    
    <div class="video card">
        <a class="valign-wrapper" href="<spring:url value='/video/${video.id}.${fn:toLowerCase(video.videoFormat)}' />" target="_blank">
            <div style="position: absolute; width: 100%; text-align: center;">
                <i class="material-icons" style=" background-color: rgba(0,0,0, 0.2); color: rgba(255,255,255, 0.8); padding: 0.5rem; font-size: 3em; border-width: 2px; border-color: rgba(255,255,255, 0.8); border-style: solid; border-radius: 50%;">play_arrow</i>
            </div>
            <img src="<spring:url value='/video/${video.id}/thumbnail.png' />" alt="${video.title}" />
        </a>
    </div>
    
    <div class="divider" style="margin-top: 1em;"></div>
    
    <h5 class="center"><fmt:message key="content.labels" /></h5>
    
    <b><fmt:message key="letters" /></b><br />
    <div id="progressLetters" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="letterLabelContainer">
        <c:forEach var="letter" items="${video.letters}">
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
    
    <b><fmt:message key="numbers" /></b><br />
    <div id="progressNumbers" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="numberLabelContainer">
        <c:forEach var="number" items="${video.numbers}">
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
    
    <b><fmt:message key="words" /></b><br />
    <div id="progressWords" class="progress" style="display: none;">
        <div class="indeterminate"></div>
    </div>
    <div id="wordLabelContainer">
        <c:forEach var="word" items="${video.words}">
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
</content:aside>
