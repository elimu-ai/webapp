<content:title>
    <fmt:message key="edit.word" />
</content:title>

<content:section cssId="wordEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="word">
            <tag:formErrors modelAttribute="word" />

            <div class="row">
                <form:hidden path="locale" value="${word.locale}" />
                <form:hidden path="revisionNumber" value="${word.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="phonetics" cssErrorClass="error"><fmt:message key='phonetics' /> (IPA)</form:label>
                    <form:input path="phonetics" cssErrorClass="error" />
                    <div id="allophonesContainer">
                        <c:forEach var="allophone" items="${allophones}">
                            <a href="#" class="chip">${allophone.valueIpa}</a>
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
                            });
                        </script>
                    </div>
                </div>
                
                <div class="input-field col s12">
                    <textarea id="comment" name="comment" class="materialize-textarea" maxlength="255"><c:if test="${not empty param.comment}">${param.comment}</c:if></textarea>
                    <label for="comment"><fmt:message key="comment.about.the.change" /></label>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
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
        <img src="<spring:url value='/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
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
</content:aside>
