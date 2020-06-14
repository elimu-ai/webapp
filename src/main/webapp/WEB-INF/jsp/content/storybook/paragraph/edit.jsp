<content:title>
    <fmt:message key="edit.storybook.paragraph" />
</content:title>

<content:section cssId="storyBookParagraphEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBookParagraph">
            <tag:formErrors modelAttribute="storyBookParagraph" />
            
            <form:hidden path="storyBookChapter" value="${storyBookParagraph.storyBookChapter.id}" />
            <form:hidden path="sortOrder" value="${storyBookParagraph.sortOrder}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="originalText" cssErrorClass="error"><fmt:message key='original.text' /></form:label>
                    <form:textarea path="originalText" cssClass="materialize-textarea" cssErrorClass="error" />
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key='original.text' /></h5>
    <div class="card-panel grey lighten-5">
        <code class="language-markup">
            white-space: pre-line;<br />
            word-spacing: 1em;<br />
            letter-spacing: 0.4em;
        </code>
        <blockquote class="flow-text" style="white-space: pre-line; word-spacing: 1em; letter-spacing: 0.4em;"><c:out value="${storyBookParagraph.originalText}" /></blockquote>
    </div>
</content:aside>
