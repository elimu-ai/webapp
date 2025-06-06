<content:title>
    Add number
</content:title>

<content:section cssId="numberCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error">Value (Number)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <form:label path="symbol" cssErrorClass="error">Symbol</form:label>
                    <form:input path="symbol" cssErrorClass="error" />
                </div>
            </div>
            
            <div class="row">    
                <div class="col s12">
                    <label>Number word(s)</label>
                    <div id="numberWordsContainer">
                        <c:forEach var="word" items="${number.words}">
                            <input name="words" type="hidden" value="${word.id}" />
                            <div class="chip" data-wordid="${word.id}" data-wordvalue="${word.text}">
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">
                                    ${word.text}<c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" />
                                </a>
                                <a href="#" class="wordDeleteLink" data-wordid="${word.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.wordDeleteLink').on("click", function() {
                                    console.log('.wordDeleteLink on click');
                                    
                                    var wordId = $(this).attr("data-wordid");
                                    console.log('wordId: ' + wordId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="words"][value="' + wordId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </div>

                    <select id="numberWords" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- Select --</option>
                        <c:forEach var="word" items="${words}">
                            <option value="${word.id}"><c:out value="${word.text}" /><c:if test="${not empty word.wordType}"> (${word.wordType})</c:if><c:out value=" ${emojisByWordId[word.id]}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#numberWords').on("change", function() {
                                console.log('#numberWords on change');
                                
                                var wordId = $(this).val();
                                console.log('wordId: ' + wordId);
                                var wordText = $(this).find('option[value="' + wordId + '"]').text();
                                console.log('wordText: ' + wordText);
                                if (wordId != "") {
                                    $('#numberWordsContainer').append('<input name="words" type="hidden" value="' + wordId + '" />');
                                    $('#numberWordsContainer').append('<div class="chip">' + wordText + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/word/create' />" target="_blank">Add word <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution."><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
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

    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <div class="card-panel deep-purple lighten-5">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikibooks.org/wiki/Hindi/Numbers" target="_blank">Wikibooks</a>
                </li>
                <li>
                    <a href="https://omniglot.com/language/numbers/hindi.htm" target="_blank">Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
        <div class="card-panel deep-purple lighten-5">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Thai_numerals" target="_blank">Thai numerals - Wikipedia</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/language/numbers/thai.htm" target="_blank">Numbers in Thai - Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'VIE'}">
        <div class="card-panel deep-purple lighten-5">
            Vietnamese resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Vietnamese_numerals" target="_blank">Vietnamese numerals - Wikipedia</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/language/numbers/vietnamese.htm" target="_blank">Numbers in Vietnamese - Omniglot</a>
                </li>
            </ol>
        </div>
    </c:if>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
</content:aside>
