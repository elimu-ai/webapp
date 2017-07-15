<content:title>
    <fmt:message key="audios" /> (${fn:length(audios)})
</content:title>

<content:section cssId="audioListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>

        <c:if test="${not empty audios}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="transcription" /></th>
                    <th><fmt:message key="audio" /></th>
                    <th><fmt:message key="literacy.skills" /></th>
                    <th><fmt:message key="numeracy.skills" /></th>
                    <th><fmt:message key="revision" /></th>
                    <th><fmt:message key="edit" /></th>
                </thead>
                <tbody>
                    <c:forEach var="audio" items="${audios}">
                        <tr class="audio">
                            <td>
                                <a name="${audio.id}"></a>
                                "<c:out value="${audio.transcription}" />"
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/audio/${audio.id}.${fn:toLowerCase(audio.audioFormat)}' />" />
                                </audio>
                            </td>
                            <td>
                                ${audio.literacySkills}
                            </td>
                            <td>
                                ${audio.numeracySkills}
                            </td>
                            <td>
                                <p>#${audio.revisionNumber}</p>
                            </td>
                            <td><a class="editLink" href="<spring:url value='/content/multimedia/audio/edit/${audio.id}' />"><span class="material-icons">edit</span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating btn-large amber tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a>
    </div>
</content:section>
