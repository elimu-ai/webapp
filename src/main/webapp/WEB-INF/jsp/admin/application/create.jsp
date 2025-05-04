<content:title>
    Add application
</content:title>

<content:section cssId="applicationCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="application">
            <tag:formErrors modelAttribute="application" />

            <div class="row">
                <form:hidden path="applicationStatus" value="${application.applicationStatus}" />
                <form:hidden path="contributor" value="${contributor.id}" />
                <div class="col s12 m6 input-field">
                    <form:label path="packageName" cssErrorClass="error">Package name</form:label>
                    <form:input path="packageName" cssErrorClass="error" placeholder="ai.elimu.soundcards" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12">
                    <label>App type</label><br />
                    <input type="checkbox" name="infrastructural" id="infrastructural" value="on" <c:if test="${application.infrastructural}">checked="checked"</c:if> />
                    <label for="infrastructural">
                        Infrastructural
                    </label>
                    <script>
                        $(function() {
                            $('#infrastructural').on('change', function() {
                                console.info('#infrastructural on change');
                                
                                if ($(this).is(':checked')) {
                                    $('#skillsContainer').fadeOut();
                                } else {
                                    $('#skillsContainer').fadeIn();
                                }
                            });
                        });
                    </script>
                </div>
            </div>
                    
            <div id="skillsContainer" <c:if test="${application.infrastructural}">style="display: none;"</c:if>>
                <div class="row">
                    <div class="col s12 m6">
                        <h5>Literacy skills</h5>
                        <blockquote>
                            What <i>literacy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="literacySkill" items="${literacySkills}">
                            <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(application.literacySkills, literacySkill)}">checked="checked"</c:if> />
                            <label for="${literacySkill}">
                                <c:choose>
                                    <c:when test="${literacySkill == 'CONCEPTS_ABOUT_PRINT'}">Concepts about print</c:when>
                                    <c:when test="${literacySkill == 'PHONEMIC_AWARENESS'}">Phonemic awareness</c:when>
                                    <c:when test="${literacySkill == 'ORAL_VOCABULARY'}">Oral vocabulary</c:when>
                                    <c:when test="${literacySkill == 'LISTENING_COMPREHENSION'}">Listening comprehension</c:when>
                                    <c:when test="${literacySkill == 'LETTER_IDENTIFICATION'}">Letter identification</c:when>
                                    <c:when test="${literacySkill == 'SYLLABLE_NAMING'}">Syllable naming</c:when>
                                    <c:when test="${literacySkill == 'NONWORD_READING'}">Nonword Reading</c:when>
                                    <c:when test="${literacySkill == 'FAMILIAR_WORD_READING'}">Familiar word reading</c:when>
                                    <c:when test="${literacySkill == 'ORAL_READING_FLUENCY'}">Oral reading fluency</c:when>
                                    <c:when test="${literacySkill == 'DICTATION'}">Dictation (Sentence writing)</c:when>
                                    <c:when test="${literacySkill == 'MAZE_CLOZE'}">Maze/Cloze (Reading comprehension)</c:when>                                    
                                </c:choose>
                            </label><br />
                        </c:forEach>
                    </div>

                    <div class="col s12 m6">
                        <h5>Numeracy skills</h5>
                        <blockquote>
                            What <i>numeracy</i> skill(s) does the application teach?
                        </blockquote>
                        <c:forEach var="numeracySkill" items="${numeracySkills}">
                            <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(application.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                            <label for="${numeracySkill}">
                                <c:choose>
                                    <c:when test="${numeracySkill == 'ORAL_COUNTING'}">Oral counting</c:when>
                                    <c:when test="${numeracySkill == 'ONE_TO_ONE_CORRESPONDENCE'}">One-to-one correspondence</c:when>
                                    <c:when test="${numeracySkill == 'NUMBER_IDENTIFICATION'}">Number identification</c:when>
                                    <c:when test="${numeracySkill == 'QUANTITY_DISCRIMINATION'}">Quantity discrimination</c:when>                                    
                                    <c:when test="${numeracySkill == 'MISSING_NUMBER'}">Missing number</c:when>                                    
                                    <c:when test="${numeracySkill == 'ADDITION'}">Addition</c:when>                                    
                                    <c:when test="${numeracySkill == 'SUBTRACTION'}">Subtraction</c:when>                                    
                                    <c:when test="${numeracySkill == 'MULTIPLICATION'}">Multiplication</c:when>                                    
                                    <c:when test="${numeracySkill == 'WORD_PROBLEMS'}">Word problems</c:when>                                    
                                    <c:when test="${numeracySkill == 'SHAPE_IDENTIFICATION'}">Shape identification</c:when>                                    
                                    <c:when test="${numeracySkill == 'SHAPE_NAMING'}">Shape naming</c:when>                                    
                                </c:choose>
                            </label><br />
                        </c:forEach>
                    </div>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Add <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
