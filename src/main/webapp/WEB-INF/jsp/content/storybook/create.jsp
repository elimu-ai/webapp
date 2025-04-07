<content:title>
    Add storybook
</content:title>

<content:section cssId="storyBookCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook">
            <tag:formErrors modelAttribute="storyBook" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error">Title</form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="description" cssErrorClass="error">Description</form:label>
                    <form:input path="description" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- Select --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == storyBook.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense">Content license</label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error">Attribution URL</form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="input-field col s12">
                    <select id="readingLevel" name="readingLevel">
                        <option value="">-- Select --</option>
                        <c:forEach var="readingLevel" items="${readingLevels}">
                            <option value="${readingLevel}" <c:if test="${readingLevel == storyBook.readingLevel}">selected="selected"</c:if>>
                                <c:choose>
                                    <c:when test="${readingLevel == 'LEVEL1'}">Level 1. Beginning to Read</c:when>
                                    <c:when test="${readingLevel == 'LEVEL2'}">Level 2. Learning to Read</c:when>
                                    <c:when test="${readingLevel == 'LEVEL3'}">Level 3. Reading Independently</c:when>
                                    <c:when test="${readingLevel == 'LEVEL4'}">Level 4. Reading Proficiently</c:when>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                    <label for="readingLevel">Reading level</label>
                </div>
                
                <div class="input-field col s12">
                    <select id="coverImage" name="coverImage">
                        <option value="">-- Select --</option>
                        <c:forEach var="coverImage" items="${coverImages}">
                            <option value="${coverImage.id}" <c:if test="${coverImage.id == storyBook.coverImage.id}">selected="selected"</c:if>>${coverImage.title}</option>
                        </c:forEach>
                    </select>
                    <label for="coverImage">Cover image</label>
                    <c:if test="${not empty storyBook.coverImage}">
                        <img src="<spring:url value='${storyBook.coverImage.url}' />" alt="${storyBook.title}" />
                    </c:if>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://bookdash.org/books/" target="_blank">Book Dash</a>
            </li>
        </ol>
    </div>
</content:aside>
