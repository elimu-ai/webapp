<content:title>
    Applications (${fn:length(applications)})
</content:title>

<content:section cssId="applicationListPage">
    <div class="section row">
        <%-- Show number of apps in each EGRA/EGMA category --%>
        <div class="row">            
            <div class="col s12 m6">
                <h5>APKs Covering EGRA Skills</h5>
                
                <c:forEach var="literacySkillCount" items="${literacySkillCountMap}">
                    <c:choose>
                        <c:when test="${literacySkillCount.key == 'CONCEPTS_ABOUT_PRINT'}">Concepts about print</c:when>
                        <c:when test="${literacySkillCount.key == 'PHONEMIC_AWARENESS'}">Phonemic awareness</c:when>
                        <c:when test="${literacySkillCount.key == 'ORAL_VOCABULARY'}">Oral vocabulary</c:when>
                        <c:when test="${literacySkillCount.key == 'LISTENING_COMPREHENSION'}">Listening comprehension</c:when>
                        <c:when test="${literacySkillCount.key == 'LETTER_IDENTIFICATION'}">Letter identification</c:when>
                        <c:when test="${literacySkillCount.key == 'SYLLABLE_NAMING'}">Syllable naming</c:when>
                        <c:when test="${literacySkillCount.key == 'NONWORD_READING'}">Nonword Reading</c:when>
                        <c:when test="${literacySkillCount.key == 'FAMILIAR_WORD_READING'}">Familiar word reading</c:when>
                        <c:when test="${literacySkillCount.key == 'ORAL_READING_FLUENCY'}">Oral reading fluency</c:when>
                        <c:when test="${literacySkillCount.key == 'DICTATION'}">Dictation (Sentence writing)</c:when>
                        <c:when test="${literacySkillCount.key == 'MAZE_CLOZE'}">Maze/Cloze (Reading comprehension)</c:when>                                    
                    </c:choose>
                    (${literacySkillCount.value})<br />
                    <div class="progress">
                        <div class="determinate" style="width: ${literacySkillCount.value * 100 / maxLiteracySkillCount}%"></div>
                    </div>
                </c:forEach>
            </div>

            <div class="col s12 m6">
                <h5>APKs Covering EGMA Skills</h5>
                
                <c:forEach var="numeracySkillCount" items="${numeracySkillCountMap}">
                    <c:choose>
                        <c:when test="${numeracySkillCount.key == 'ORAL_COUNTING'}">Oral counting</c:when>
                        <c:when test="${numeracySkillCount.key == 'ONE_TO_ONE_CORRESPONDENCE'}">One-to-one correspondence</c:when>
                        <c:when test="${numeracySkillCount.key == 'NUMBER_IDENTIFICATION'}">Number identification</c:when>
                        <c:when test="${numeracySkillCount.key == 'QUANTITY_DISCRIMINATION'}">Quantity discrimination</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'MISSING_NUMBER'}">Missing number</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'ADDITION'}">Addition</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'SUBTRACTION'}">Subtraction</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'MULTIPLICATION'}">Multiplication</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'WORD_PROBLEMS'}">Word problems</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'SHAPE_IDENTIFICATION'}">Shape identification</c:when>                                    
                        <c:when test="${numeracySkillCount.key == 'SHAPE_NAMING'}">Shape naming</c:when>                                    
                    </c:choose>
                    (${numeracySkillCount.value})<br />
                    <div class="progress">
                        <div class="determinate" style="width: ${numeracySkillCount.value * 100 / maxLiteracySkillCount}%"></div>
                    </div>
                </c:forEach>
            </div>
        </div>
        
        <hr />
        
        <p>
            To add new content, click the button below.
        </p>
        
        <c:if test="${not empty applications}">
            <table class="bordered highlight">
                <thead>
                    <th>Package name</th>
                    <th>Literacy skills</th>
                    <th>Numeracy skills</th>
                    <th>Status</th>
                    <th>Creator</th>
                </thead>
                <tbody>
                    <c:forEach var="application" items="${applications}">
                        <tr>
                            <td>
                                <a name="${application.id}"></a>
                                <a class="editLink" href="<spring:url value='/admin/application/edit/${application.id}' />"><code>${application.packageName}</code></a>
                            </td>
                            <td>
                                ${application.literacySkills}
                            </td>
                            <td>
                                ${application.numeracySkills}
                            </td>
                            <td>
                                ${application.applicationStatus}
                            </td>
                            <td>
                                <c:set var="chipContributor" value="${application.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/admin/application/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add application"><i class="material-icons">add</i></a>
    </div>
</content:section>
