<content:title>
    <fmt:message key="add.storybook" />
</content:title>

<content:section cssId="storyBookCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />

            <div class="row">
                <form:hidden path="language" value="${applicationScope.configProperties['content.language']}" />
                <form:hidden path="revisionNumber" value="${storyBook.revisionNumber}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="description" cssErrorClass="error"><fmt:message key='description' /></form:label>
                    <form:input path="description" cssErrorClass="error" />
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
                    <select id="readingLevel" name="readingLevel">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="readingLevel" items="${readingLevels}">
                            <option value="${readingLevel}" <c:if test="${readingLevel == storyBook.readingLevel}">selected="selected"</c:if>><fmt:message key="reading.level.${readingLevel}" /></option>
                        </c:forEach>
                    </select>
                    <label for="readingLevel"><fmt:message key="reading.level" /></label>
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
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
