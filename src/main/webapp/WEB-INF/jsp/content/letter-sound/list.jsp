<content:title>
    <fmt:message key="letter.sound.correspondences" /> (${fn:length(letterSounds)})
</content:title>

<content:section cssId="letterSoundListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/letter-sound/list/letter-sounds.csv' />">
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
            <fmt:message key="to.add.new.content.click.the.button.below" /> You can also 
            <a href="<spring:url value='/content/letter-sound/peer-reviews' />">peer-review</a> letter-sound correspondences.
        </p>
    
        <c:if test="${not empty letterSounds}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="usage.count" /></th>
                    <th><fmt:message key="letters" /></th>
                    <th></th>
                    <th><fmt:message key="sounds" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="letterSound" items="${letterSounds}">
                        <tr class="letterSound">
                            <td>
                                <a name="${letterSound.id}"></a>
                                
                                ${letterSound.usageCount}
                            </td>
                            <td style="font-size: 2em;">
                                " <c:forEach var="letter" items="${letterSound.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />">${letter.text}</a> </c:forEach> "
                            </td>
                            <td style="font-size: 2em;">
                                ➞
                            </td>
                            <td style="font-size: 2em;">
                                / <c:forEach var="sound" items="${letterSound.sounds}"><a href="<spring:url value='/content/sound/edit/${sound.id}' />">${sound.valueIpa}</a> </c:forEach> /
                            </td>
                            <td>
                                <p>#${letterSound.revisionNumber}</p>
                                <p>
                                    <c:choose>
                                        <c:when test="${letterSound.peerReviewStatus == 'APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                        </c:when>
                                        <c:when test="${letterSound.peerReviewStatus == 'NOT_APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="peerReviewStatusColor" value="" />
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="chip ${peerReviewStatusColor}">
                                        <a href="<spring:url value='/content/letter-sound/edit/${letterSound.id}#contribution-events' />">
                                            ${letterSound.peerReviewStatus}
                                        </a>
                                    </span>
                                </p>
                            </td>
                            <td>
                                <a class="editLink" href="<spring:url value='/content/letter-sound/edit/${letterSound.id}' />"><span class="material-icons">edit</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/letter-sound/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter.sound.correspondence" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
