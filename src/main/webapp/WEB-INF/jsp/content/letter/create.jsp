<content:title>
    Add letter
</content:title>

<content:section cssId="letterCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error">Text</form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>
                
            <div class="row">
                <div class="input-field col">
                    <select id="diacritic" name="diacritic">
                        <option value="false" <c:if test="${not letter.diacritic}">selected="selected"</c:if>>No</option>
                        <option value="true" <c:if test="${letter.diacritic}">selected="selected"</c:if>>Yes</option>
                    </select>
                    <label for="diacritic">Diacritic</label>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
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
        <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">Wikipedia: Help:IPA/Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Devanagari#Letters" target="_blank">Wikipedia: Devanagari - Letters</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/hindi.htm" target="_blank">Omniglot: Hindi (हिन्दी)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Thai" target="_blank">Wikipedia: Help:IPA/Thai</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Thai_script" target="_blank">Wikipedia: Thai script</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/thai.htm" target="_blank">Omniglot: Thai (ภาษาไทย)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        <c:if test="${applicationScope.configProperties['content.language'] == 'VIE'}">
            Vietnamese resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Vietnamese" target="_blank">Wikipedia: Help:IPA/Vietnamese</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Vietnamese_alphabet" target="_blank">Wikipedia: Vietnamese alphabet</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/vietnamese.htm" target="_blank">Omniglot: Vietnamese (tiếng việt / 㗂越)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>
</content:aside>
