<content:title>
    Numbers (${fn:length(numbers)})
</content:title>

<content:section cssId="numberListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/number/list/numbers.csv' />">
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
        
        <table class="bordered highlight">
            <thead>
                <th>Value</th>
                <th>Symbol</th>
                <th>Number word(s)</th>
                <th>Sounds</th>
                <th>Revision</th>
            </thead>
            <tbody>
                <c:forEach var="number" items="${numbers}">
                    <tr class="letter">
                        <td style="font-size: 2em;">
                            <a name="${number.id}"></a>
                            <a class="editLink" href="<spring:url value='/content/number/edit/${number.id}' />">${number.value}</a>
                        </td>
                        <td style="font-size: 2em;">
                            ${number.symbol}
                        </td>
                        <td style="font-size: 2em;">
                            <c:forEach var="word" items="${number.words}">
                                <a href="<spring:url value='/content/word/edit/${word.id}' />">${word.text}</a>
                            </c:forEach>
                        </td>
                        
                        <td style="font-size: 2em;">
                            <c:forEach var="word" items="${number.words}">
                                /<c:forEach var="lsc" items="${word.letterSounds}">&nbsp;<a href="<spring:url value='/content/letter-sound/edit/${lsc.id}' />"><c:forEach var="sound" items="${lsc.sounds}">${sound.valueIpa}</c:forEach></a>&nbsp;</c:forEach>/
                            </c:forEach>
                        </td>
                        <td>
                            <p>#${number.revisionNumber}</p>
                            <p>
                                <c:choose>
                                    <c:when test="${number.peerReviewStatus == 'APPROVED'}">
                                        <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                    </c:when>
                                    <c:when test="${number.peerReviewStatus == 'NOT_APPROVED'}">
                                        <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="peerReviewStatusColor" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <span class="chip ${peerReviewStatusColor}">
                                    <a href="<spring:url value='/content/number/edit/${number.id}#contribution-events' />">
                                        ${number.peerReviewStatus}
                                    </a>
                                </span>
                            </p>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/number/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add number"><i class="material-icons">add</i></a>
    </div>
</content:section>
