<content:title>
    Letters (${fn:length(letters)})
</content:title>

<content:section cssId="letterListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/letter/list/letters.csv' />">
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
    
        <c:if test="${not empty letters}">
            <table class="bordered highlight">
                <thead>
                    <th>Frequency</th>
                    <th>Letter</th>
                    <th>Diacritic</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="letter" items="${letters}">
                        <tr class="letter">
                            <td>
                                ${letter.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${letter.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${letter.id}"></a>
                                <a class="editLink" href="<spring:url value='/content/letter/edit/${letter.id}' />">"<c:out value='${letter.text}' />"</a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${letter.diacritic}">
                                        Yes
                                    </c:when>
                                    <c:otherwise>
                                        No
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <p>#${letter.revisionNumber}</p>
                                <p>
                                    <c:choose>
                                        <c:when test="${letter.peerReviewStatus == 'APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                        </c:when>
                                        <c:when test="${letter.peerReviewStatus == 'NOT_APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="peerReviewStatusColor" value="" />
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="chip ${peerReviewStatusColor}">
                                        <a href="<spring:url value='/content/letter/edit/${letter.id}#contribution-events' />">
                                            ${letter.peerReviewStatus}
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
        <a id="createButton" href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add letter"><i class="material-icons">add</i></a>
    </div>
</content:section>
