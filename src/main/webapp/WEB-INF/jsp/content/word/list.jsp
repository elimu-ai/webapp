<content:title>
    Words (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/word/list/words.csv' />">
            Export to CSV<i class="material-icons right">vertical_align_bottom</i>
        </a>
        <script>
            $(function() {
                $('#exportToCsvButton').click(function() {
                    console.info('#exportToCsvButton click');
                    Materialize.toast('Preparing CSV file. Please wait...', 4000, 'rounded');
                });
            });
        </script>

        <p>
            To add new content, click the button below. <span style="position: absolute; transform: rotate(-33deg);">👇🏽</span>
        </p>
        
        <c:if test="${not empty words}">
            <table class="bordered highlight">
                <thead>
                    <th>Frequency</th>
                    <th>Text</th>
                    <th>Letter-sound correspondences</th>
                    <%--<th>Grapheme-phoneme correspondence</th>--%>
                    <th>Word type</th>
                    <th>Root word</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="word" items="${words}">
                        <tr>
                            <td>
                                ${word.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${word.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${word.id}"></a>
                                <a class="editLink" href="<spring:url value='/content/word/edit/${word.id}' />">"<c:out value="${word.text}" />"</a>
                            </td>
                            <td style="font-size: 2em;">
                                <div id="letterSoundsContainer">
                                    <c:forEach var="letterSound" items="${word.letterSounds}">
                                        <input name="letterSounds" type="hidden" value="${letterSound.id}" />
                                        <div class="chip">
                                            <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />">
                                                " <c:forEach var="letter" items="${letterSound.letters}">
                                                    ${letter.text}<c:out value=" " />
                                                </c:forEach> "<br />
                                                / <c:forEach var="sound" items="${letterSound.sounds}">
                                                    ${sound.valueIpa}<c:out value=" " />
                                                </c:forEach> /
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </td>
                            <%--
                            <td>
                                <c:choose>
                                    <c:when test="${word.spellingConsistency == 'PERFECT'}">
                                        <c:set var="spellingConsistencyColor" value="green lighten-1" />
                                    </c:when>
                                    <c:when test="${word.spellingConsistency == 'HIGHLY_PHONEMIC'}">
                                        <c:set var="spellingConsistencyColor" value="green lighten-3" />
                                    </c:when>
                                    <c:when test="${word.spellingConsistency == 'PHONEMIC'}">
                                        <c:set var="spellingConsistencyColor" value="yellow lighten-3" />
                                    </c:when>
                                    <c:when test="${word.spellingConsistency == 'NON_PHONEMIC'}">
                                        <c:set var="spellingConsistencyColor" value="orange lighten-3" />
                                    </c:when>
                                    <c:when test="${word.spellingConsistency == 'HIGHLY_NON_PHONEMIC'}">
                                        <c:set var="spellingConsistencyColor" value="red lighten-3" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="spellingConsistencyColor" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <div class="chip ${spellingConsistencyColor}">
                                    <c:choose>
                                        <c:when test="${word.spellingConsistency == 'PERFECT'}">Perfect (100% correspondence)</c:when>
                                        <c:when test="${word.spellingConsistency == 'HIGHLY_PHONEMIC'}">Highly phonemic (80%-99% correspondence)</c:when>
                                        <c:when test="${word.spellingConsistency == 'PHONEMIC'}">Phonemic (60%-79% correspondence)</c:when>
                                        <c:when test="${word.spellingConsistency == 'NON_PHONEMIC'}">Non-phonemic (40%-59% correspondence)</c:when>
                                        <c:when test="${word.spellingConsistency == 'HIGHLY_NON_PHONEMIC'}">Highly non-phonemic (0%-39% correspondence)</c:when>
                                    </c:choose>
                                </div>
                            </td>
                            --%>
                            <td>
                                ${word.wordType}<br />
                                <c:out value=" ${emojisByWordId[word.id]}" />
                            </td>
                            <td>
                                <c:if test="${not empty word.rootWord}">
                                    <a href="<spring:url value='/content/word/edit/${word.rootWord.id}' />">
                                        ${word.rootWord.text} 
                                    </a> (${word.rootWord.wordType})
                                </c:if>
                            </td>
                            <td>
                                <p>#${word.revisionNumber}</p>
                                <p>
                                    <c:choose>
                                        <c:when test="${word.peerReviewStatus == 'APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                        </c:when>
                                        <c:when test="${word.peerReviewStatus == 'NOT_APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="peerReviewStatusColor" value="" />
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="chip ${peerReviewStatusColor}">
                                        <a href="<spring:url value='/content/word/edit/${word.id}#contribution-events' />">
                                            ${word.peerReviewStatus}
                                        </a>
                                    </span>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/word/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add word"><i class="material-icons">add</i></a>
    </div>
</content:section>
