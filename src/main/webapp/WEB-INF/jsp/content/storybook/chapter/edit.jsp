<content:title>
    Edit chapter ${storyBookChapter.sortOrder + 1}
</content:title>

<content:section cssId="storyBookChapterEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBookChapter">
            <tag:formErrors modelAttribute="storyBookChapter" />
            
            <form:hidden path="storyBook" value="${storyBookChapter.storyBook.id}" />
            <form:hidden path="sortOrder" value="${storyBookChapter.sortOrder}" />
            
            <div class="row">
                <div class="input-field col s12">
                    <select id="image" name="image">
                        <option value="">-- Select --</option>
                        <c:forEach var="image" items="${images}">
                            <option value="${image.id}" <c:if test="${image.id == storyBookChapter.image.id}">selected="selected"</c:if>>${image.title}</option>
                        </c:forEach>
                    </select>
                    <label for="image">Image</label>
                    <c:if test="${not empty storyBookChapter.image}">
                        <a href="<spring:url value='/content/multimedia/image/edit/${storyBookChapter.image.id}' />">
                            <img class="checksumGitHub-${storyBookChapter.image.checksumGitHub != null}" src="<spring:url value='${storyBookChapter.image.url}' />" alt="${storyBook.title}" />
                        </a>
                    </c:if>
                </div>
            </div>
            
            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
