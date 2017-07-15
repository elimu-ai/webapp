<content:title>
    <fmt:message key="add.storybook" />
</content:title>

<content:section cssId="storyBookCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="revisionNumber" value="${storyBook.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == storyBook.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense"><fmt:message key="content.license" /></label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="input-field col s12">
                    <select id="gradeLevel" name="gradeLevel">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="gradeLevel" items="${gradeLevels}">
                            <option value="${gradeLevel}" <c:if test="${gradeLevel == storyBook.gradeLevel}">selected="selected"</c:if>><fmt:message key="grade.level.${gradeLevel}" /></option>
                        </c:forEach>
                    </select>
                    <label for="gradeLevel"><fmt:message key="grade.level" /></label>
                </div>
                
                <div class="input-field col s12">
                    <select id="coverImage" name="coverImage">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="coverImage" items="${coverImages}">
                            <option value="${coverImage.id}" <c:if test="${coverImage.id == storyBook.coverImage.id}">selected="selected"</c:if>>${coverImage.title}</option>
                        </c:forEach>
                    </select>
                    <label for="coverImage"><fmt:message key="cover.image" /></label>
                    <c:if test="${not empty storyBook.coverImage}">
                        <img src="<spring:url value='/image/${storyBook.coverImage.id}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}' />" alt="${storyBook.title}" />
                    </c:if>
                </div>
                
                <p>&nbsp;</p>
                
                <div class="input-field col s12">
                    <form:label path="paragraphs" cssErrorClass="error"><fmt:message key='paragraphs' /></form:label>
                    <input name="paragraphs" type="text" />
                    <a id="addParagraphLink" class="btn" href="#">+</a>
                    <script>
                        $(function() {
                            $('#addParagraphLink').click(function(event) {
                                console.info('#addParagraphLink click');
                                event.preventDefault();
                                $('#addParagraphLink').before('<input name="paragraphs" type="text" />');
                            });
                        });
                    </script>
                </div>
            </div>

            <button id="submitButton" class="btn lime waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
