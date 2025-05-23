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
                    <th>Word</th>
                    <th>Word type</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="word" items="${words}">
                        <tr>
                            <td>
                                ${word.usageCount}
                                <div class="progress">
                                    <div class="determinate" style="width: ${word.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td>
                                <div style="float: right; text-align: right;">
                                    <label>word.getText()</label>
                                    <div style="font-size: 2em;">
                                        "${word.text}"
                                    </div>
                                </div>
                                
                                <label>word.toString()</label>
                                <div style="font-size: 2em;">
                                    "${word}"
                                </div>

                                <div id="letterSoundsContainer">
                                    <label>Sound-to-letter correspondences</label><br />
                                    <c:forEach var="letterSound" items="${word.letterSounds}">
                                        <div class="chip">
                                            <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />">
                                                / <c:forEach var="sound" items="${letterSound.sounds}">
                                                    ${sound.valueIpa}<c:out value=" " />
                                                </c:forEach> /<br />
                                                ↓<br />
                                                " <c:forEach var="letter" items="${letterSound.letters}">
                                                    ${letter.text}<c:out value=" " />
                                                </c:forEach> "
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </td>
                            <td>
                                ${word.wordType}
                                <div style="font-size: 2em;">
                                    <c:out value=" ${emojisByWordId[word.id]}" />
                                </div>
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
