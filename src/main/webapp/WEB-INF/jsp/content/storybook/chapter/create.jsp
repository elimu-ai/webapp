<content:title>
    Add storybook chapter
</content:title>

<content:section cssId="storyBookChapterCreatePage">
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
                            <option value="${image.id}">${image.title}</option>
                        </c:forEach>
                    </select>
                    <label for="image">Image</label>
                </div>
            </div>
            
            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
