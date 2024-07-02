<content:title>
    <fmt:message key="audios" /> (${fn:length(audios)})
</content:title>

<content:section cssId="audioListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/audio/list/audios.csv' />">
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
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>

        <c:if test="${not empty audios}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="title" />/<br /><fmt:message key="transcription" /></th>
                    <th><fmt:message key="audio" /></th>
                    <%--
                    <th><fmt:message key="literacy.skills" /></th>
                    <th><fmt:message key="numeracy.skills" /></th>
                    --%>
                    <th><fmt:message key="word" /></th>
                    <th><fmt:message key="time.last.update" /></th>
                    <th><fmt:message key="platform" /></th>
                    <th><fmt:message key="revision" /></th>
                </thead>
                <tbody>
                    <c:forEach var="audio" items="${audios}">
                        <tr class="audio">
                            <td>
                                <a name="${audio.id}"></a>
                                <p><a href="<spring:url value='/content/multimedia/audio/edit/${audio.id}' />">"<c:out value="${audio.title}" />"</a></p>
                                <p class="grey-text">"<c:out value="${audio.transcription}" />"</p>
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/audio/${audio.id}_r${audio.revisionNumber}.${fn:toLowerCase(audio.audioFormat)}' />" />
                                </audio>
                            </td>
                            <%--
                            <td>
                                ${audio.literacySkills}
                            </td>
                            <td>
                                ${audio.numeracySkills}
                            </td>
                            --%>
                            <td>
                                <a href="<spring:url value='/content/word/edit/${audio.word.id}' />">
                                    "${audio.word.text}"
                                </a>
                            </td>
                            <td>
                                <fmt:formatDate value="${audio.timeLastUpdate.time}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <code>// TODO</code>
                                <%-- https://github.com/elimu-ai/webapp/projects/6#card-60872823 --%>
                            </td>
                            <td>
                                <p>#${audio.revisionNumber}</p>
                                <p>
                                    <c:choose>
                                        <c:when test="${audio.peerReviewStatus == 'APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                        </c:when>
                                        <c:when test="${audio.peerReviewStatus == 'NOT_APPROVED'}">
                                            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="peerReviewStatusColor" value="" />
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="chip ${peerReviewStatusColor}">
                                        <a href="<spring:url value='/content/multimedia/audio/edit/${audio.id}#contribution-events' />">
                                            ${audio.peerReviewStatus}
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
        <a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
