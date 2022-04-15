<content:title>
    <fmt:message key="add.sound" />
</content:title>

<content:section cssId="soundCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="sound">
            <tag:formErrors modelAttribute="sound" />
            
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error"><fmt:message key='value' /> (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error"><fmt:message key='value' /> (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" />
                </div>
            
                <div class="input-field col s12">
                    <select id="soundType" name="soundType">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="soundType" items="${soundTypes}">
                            <option value="${soundType}" <c:if test="${soundType == sound.soundType}">selected="selected"</c:if>><c:out value="${soundType}" /></option>
                        </c:forEach>
                    </select>
                    <label for="soundType"><fmt:message key="sound.type" /></label>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <h5 class="center"><fmt:message key="resources" /></h5>
        <div class="card-panel deep-purple lighten-5">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">IPA for Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Hindustani_phonology" target="_blank">Hindustani phonology</a>
                </li>
                <li>
                    <a href="https://omniglot.com/writing/hindi.htm#alphabet" target="_blank">Devanāgarī alphabet for Hindi</a>
                </li>
            </ol>
        </div>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
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
            <li>
                <a href="https://en.wikipedia.org/wiki/International_Phonetic_Alphabet">International Phonetic Alphabet (IPA)</a>
            </li>
        </ol>
    </div>
</content:aside>
