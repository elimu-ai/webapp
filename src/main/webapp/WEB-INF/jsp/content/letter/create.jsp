<content:title>
    <fmt:message key="add.letter" />
</content:title>

<content:section cssId="letterCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="allophones" /></label><br />
                    /<span id="allophonesContainer">
                        <c:forEach var="allophone" items="${letter.allophones}">
                            <input name="allophones" type="hidden" value="${allophone.id}" />
                            <div class="chip<c:if test="${allophone.soundType == 'VOWEL'}"> purple lighten-5</c:if><c:if test="${allophone.soundType == 'CONSONANT'}"> teal lighten-5</c:if>" data-allophoneid="${allophone.id}" data-allophonevalue="${allophone.valueIpa}">
                                ${allophone.valueIpa} 
                                <a href="#" class="allophoneDeleteLink" data-allophoneid="${allophone.id}">
                                    <i class="material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.allophoneDeleteLink').on("click", function() {
                                    console.log('.allophoneDeleteLink on click');
                                    
                                    var allophoneId = $(this).attr("data-allophoneid");
                                    console.log('allophoneId: ' + allophoneId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="allophones"][value="' + allophoneId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </span>/

                    <select id="allophones" class="browser-default" style="margin: 0.5em 0;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="allophone" items="${allophones}">
                            <option value="${allophone.id}"><c:out value="${allophone.valueIpa}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#allophones').on("change", function() {
                                console.log('#allophones on change');
                                
                                var allophoneId = $(this).val();
                                console.log('allophoneId: ' + allophoneId);
                                var allophoneValueIpa = $(this).find('option[value="' + allophoneId + '"]').text();
                                console.log('allophoneValueIpa: ' + allophoneValueIpa);
                                if (allophoneId != "") {
                                    $('#allophonesContainer').append('<input name="allophones" type="hidden" value="' + allophoneId + '" />');
                                    $('#allophonesContainer').append('<div class="chip">' + allophoneValueIpa + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/allophone/create' />" target="_blank"><fmt:message key="add.allophone" /> <i class="material-icons">launch</i></a>
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
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
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
