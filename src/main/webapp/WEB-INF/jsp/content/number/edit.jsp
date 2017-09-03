<content:title>
    <fmt:message key="edit.number" />
</content:title>

<content:section cssId="numberEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />

            <div class="row">
                <form:hidden path="locale" value="${number.locale}" />
                <form:hidden path="revisionNumber" value="${number.revisionNumber}" />
                
                <c:if test="${number.locale.language == 'ar'}">
                    <div id="symbolContainer" class="input-field col s12"<c:if test="${number.locale.language != 'ar'}"> style="display: none;"</c:if>>
                        <form:label path="symbol" cssErrorClass="error"><fmt:message key='symbol' /></form:label>
                        <form:input path="symbol" cssErrorClass="error" />
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error"><fmt:message key='value' /> (<fmt:message key='number' />)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
                
                <div class="col s12">
                    <label><fmt:message key="number.words" /></label>
                    <div id="numberWordsContainer">
                        <c:forEach var="word" items="${number.words}">
                            <input name="words" type="hidden" value="${word.id}" />
                            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                                ${word.text} 
                                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                                    <i class="material-icons">clear</i>
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
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}"><c:out value="${word.text}" /></option>
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
                    
                    <a href="<spring:url value='/content/word/create' />" target="_blank"><fmt:message key="add.word" /> <i class="material-icons">launch</i></a>
                </div>
            </div>

            <button id="submitButton" class="btn indigo waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/number/delete/${number.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
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
                    <td>${fn:length(contentCreationEvents) - status.index}</td>
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
            <div id="previewContent" class="previewContentGrapheme">

            </div>
        </div>
    </div>
    <script>
        $(function() {
            initializePreview();
            
            $('#symbol, #value').on("change", function() {
                console.debug('#symbol/#value on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var symbol = $('#symbol').val();
                var value = $('#value').val();
                if ((symbol != undefined) && (symbol != "")) {
                    $('#previewContent').html(symbol);
                } else {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
</content:aside>
