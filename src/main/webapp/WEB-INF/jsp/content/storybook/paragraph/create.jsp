<content:title>
    Add paragraph
</content:title>

<content:section cssId="storyBookParagraphCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <c:if test="${not empty storyBookParagraph.storyBookChapter.image}">
            <a href="<spring:url value='/content/multimedia/image/edit/${storyBookParagraph.storyBookChapter.image.id}' />">
                <img src="<spring:url value='${storyBookParagraph.storyBookChapter.image.url}' />" alt="${storyBookParagraph.storyBookChapter.storyBook.title}" />
            </a>
        </c:if>
        
        <form:form modelAttribute="storyBookParagraph">
            <tag:formErrors modelAttribute="storyBookParagraph" />
            
            <form:hidden path="storyBookChapter" value="${storyBookParagraph.storyBookChapter.id}" />
            <form:hidden path="sortOrder" value="${storyBookParagraph.sortOrder}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="originalText" cssErrorClass="error">Original text</form:label>
                    <form:textarea path="originalText" cssClass="materialize-textarea" cssErrorClass="error" />
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
