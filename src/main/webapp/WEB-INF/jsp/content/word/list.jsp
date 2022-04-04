<content:title>
    <fmt:message key="words" /> (${fn:length(words)})
</content:title>

<content:section cssId="wordListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/word/list/words.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
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
            <fmt:message key="to.add.new.content.click.the.button.below" /> You can also <a href="<spring:url value='/content/word/peer-reviews' />">peer-review</a> words.
        </p>
        
        <c:if test="${not empty words}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="text" /></th>
                    <th><fmt:message key="letter.sound.correspondences" /></th>
                    <%--<th><fmt:message key="spelling.consistency" /></th>--%>
                    <th><fmt:message key="word.type" /></th>
                    <th><fmt:message key="root.word" /></th>
                    <th><fmt:message key="revision" /></th>
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
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">"<c:out value="${word.text}" />"</a>
                            </td>
                            <td style="font-size: 2em;">
                                <div id="letterSoundCorrespondencesContainer">
                                    <c:forEach var="letterSoundCorrespondence" items="${word.letterSoundCorrespondences}">
                                        <input name="letterSoundCorrespondences" type="hidden" value="${letterSoundCorrespondence.id}" />
                                        <div class="chip">
                                            <a href="<spring:url value='/content/letter-sound-correspondence/edit/${letterSoundCorrespondence.id}' />">
                                                " <c:forEach var="letter" items="${letterSoundCorrespondence.letters}">
                                                    ${letter.text}<c:out value=" " />
                                                </c:forEach> "<br />
                                                / <c:forEach var="sound" items="${letterSoundCorrespondence.sounds}">
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
                                    <fmt:message key="spelling.consistency.${word.spellingConsistency}" />
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
        <a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
