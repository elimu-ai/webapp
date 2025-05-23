<content:title>
    Add emoji
</content:title>

<content:section cssId="emojiCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="emoji">
            <tag:formErrors modelAttribute="emoji" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="glyph" cssErrorClass="error">Glyph</form:label>
                    <form:input path="glyph" cssErrorClass="error" placeholder="🦋" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="unicodeVersion" cssErrorClass="error">Unicode version</form:label>
                    <form:input path="unicodeVersion" cssErrorClass="error" placeholder="10.0" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="unicodeEmojiVersion" cssErrorClass="error">Unicode Emoji version</form:label>
                    <form:input path="unicodeEmojiVersion" cssErrorClass="error" placeholder="5.0" />
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <ul>
            <li>
                <a href="https://emojipedia.org" target="_blank">📙 Emojipedia</a>
            </li>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
        </ul>
    </div>
</content:aside>
