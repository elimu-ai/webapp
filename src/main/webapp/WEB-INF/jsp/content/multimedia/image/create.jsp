<content:title>
    Add image
</content:title>

<content:section cssId="imageCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="image" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="image" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error">Title</form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="contentLicense" name="contentLicense">
                        <option value="">-- Select --</option>
                        <c:forEach var="contentLicense" items="${contentLicenses}">
                            <option value="${contentLicense}" <c:if test="${contentLicense == image.contentLicense}">selected="selected"</c:if>><c:out value="${contentLicense}" /></option>
                        </c:forEach>
                    </select>
                    <label for="contentLicense">Content license</label>
                </div>
                
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error">Attribution URL</form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                
                <div class="col s12 m6">
                    <blockquote>
                        What <i>literacy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(image.literacySkills, literacySkill)}">checked="checked"</c:if> />
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
                    <blockquote>
                        What <i>numeracy</i> skill(s) does the content teach?
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(image.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
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
                
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span>File</span>
                        <input name="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
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
