<content:title>
    <fmt:message key="edit.letter" />
</content:title>

<content:section cssId="letterEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <form:hidden path="revisionNumber" value="${letter.revisionNumber}" />
            <form:hidden path="usageCount" value="${letter.usageCount}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>

            <div class="row">
                <div class="input-field col">
                    <select id="diacritic" name="diacritic">
                        <option value="false" <c:if test="${not letter.diacritic}">selected="selected"</c:if>><fmt:message key="no" /></option>
                        <option value="true" <c:if test="${letter.diacritic}">selected="selected"</c:if>><fmt:message key="yes" /></option>
                    </select>
                    <label for="diacritic"><fmt:message key="diacritic" /></label>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/letter/delete/${letter.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="resources" /></h5>
    <div class="card-panel deep-purple lighten-5">
        <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
            Hindi resources:
            <ul>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">Wikipedia: Help:IPA/Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Devanagari#Letters" target="_blank">Wikipedia: Devanagari - Letters</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/hindi.htm" target="_blank">Omniglot: Hindi (हिन्दी)</a>
                </li>
            </ul>
        </c:if>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        General resources:
        <ul>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/master/LOCALIZATION.md" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ul>
    </div>
</content:aside>
