<content:title>
    <fmt:message key="sounds" /> (${fn:length(allophones)})
</content:title>

<content:section cssId="allophoneListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/sound/list/sounds.csv' />">
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
    
        <c:if test="${not empty allophones}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="frequency" /></th>
                    <th><fmt:message key="ipa.value" /></th>
                    <th><fmt:message key="sampa.value" /></th>
                    <th><fmt:message key="sound.type" /></th>
                    <th><fmt:message key="audio" /></th>
                    <th><fmt:message key="revision" /></th>
                </thead>
                <tbody>
                    <c:forEach var="sound" items="${allophones}">
                        <tr class="allophone">
                            <td>
                                ${sound.usageCount}<br />
                                <div class="progress">
                                    <div class="determinate" style="width: ${sound.usageCount * 100 / maxUsageCount}%"></div>
                                </div>
                            </td>
                            <td style="font-size: 2em;">
                                <a name="${sound.id}"></a>
                                <a href="<spring:url value='/content/sound/edit/${sound.id}' />">/${sound.valueIpa}/</a>
                            </td>
                            <td>
                                ${sound.valueSampa}
                            </td>
                            <td>
                                ${sound.soundType}
                            </td>
                            <td>
                                <audio controls="true">
                                    <source src="<spring:url value='/static/sound/sampa_${sound.valueSampa}.wav' />" />
                                </audio>
                            </td>
                            <td>
                                <p>#${sound.revisionNumber}</p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/sound/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.sound" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
