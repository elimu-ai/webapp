<content:title>
    <fmt:message key="add.letter.sound.correspondence" />
</content:title>

<content:section cssId="letterSoundCorrespondenceCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letterSoundCorrespondence">
            <tag:formErrors modelAttribute="letterSoundCorrespondence" />
            
            <input type="hidden" name="timeStart" value="${timeStart}" />
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="letters" /></label><br />
                    "<span id="lettersContainer">
                        <c:forEach var="letter" items="${letterSoundCorrespondence.letters}">
                            <input name="letters" type="hidden" value="${letter.id}" />
                            <div class="chip" data-letterid="${letter.id}" data-lettervalue="${letter.text}"
                                 style="font-size: 2rem; padding: 1rem; height: auto;">
                                ${letter.text} 
                                <a href="#" class="letterDeleteLink" data-letterid="${letter.id}">
                                    <i class="close material-icons">clear</i>
                                </a>
                            </div>
                        </c:forEach>
                        <script>
                            $(function() {
                                $('.letterDeleteLink').on("click", function() {
                                    console.log('.letterDeleteLink on click');
                                    
                                    var letterId = $(this).attr("data-letterid");
                                    console.log('letterId: ' + letterId);
                                    
                                    $(this).parent().remove();
                                    
                                    var $hiddenInput = $('input[name="letters"][value="' + letterId + '"]');
                                    $hiddenInput.remove();
                                });
                            });
                        </script>
                    </span>"

                    <select id="letters" class="browser-default" style="font-size: 2rem; margin: 0.5rem 0; height: auto;">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="letter" items="${letters}">
                            <option value="${letter.id}"><c:out value="${letter.text}" /></option>
                        </c:forEach>
                    </select>
                    <script>
                        $(function() {
                            $('#letters').on("change", function() {
                                console.log('#letters on change');
                                
                                var letterId = $(this).val();
                                console.log('letterId: ' + letterId);
                                var letterText = $(this).find('option[value="' + letterId + '"]').text();
                                console.log('letterText: ' + letterText);
                                if (letterId != "") {
                                    $('#lettersContainer').append('<input name="letters" type="hidden" value="' + letterId + '" />');
                                    $('#lettersContainer').append('<div class="chip">' + letterText + '</div>');
                                    $(this).val("");
                                }
                            });
                        });
                    </script>
                    
                    <a href="<spring:url value='/content/letter/create' />" target="_blank"><fmt:message key="add.letter" /> <i class="material-icons">launch</i></a>
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label><fmt:message key="allophones" /></label><br />
                    /<span id="allophonesContainer">
                        <c:forEach var="allophone" items="${letterSoundCorrespondence.allophones}">
                            <input name="allophones" type="hidden" value="${allophone.id}" />
                            <div class="chip" data-allophoneid="${allophone.id}" data-allophonevalue="${allophone.valueIpa}"
                                 style="font-size: 2rem; padding: 1rem; height: auto;">
                                ${allophone.valueIpa} 
                                <a href="#" class="allophoneDeleteLink" data-allophoneid="${allophone.id}">
                                    <i class="close material-icons">clear</i>
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

                    <select id="allophones" class="browser-default" style="font-size: 2rem; margin: 0.5rem 0; height: auto;">
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
