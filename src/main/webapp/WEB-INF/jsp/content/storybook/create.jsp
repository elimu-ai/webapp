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
                <form:hidden path="revisionNumber" value="${storybook.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
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

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
