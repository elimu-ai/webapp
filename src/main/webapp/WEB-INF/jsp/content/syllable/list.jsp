<content:title>
    Syllables (${fn:length(syllables)})
</content:title>

<content:section cssId="syllableListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/syllable/list/syllables.csv' />">
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
        
        <%--<p>
            To add new content, click the button below.
        </p>--%>
    </div>
    <div class="section row">
        <c:forEach var="syllable" items="${syllables}">
            <div class="col s12 m6 l4">
                <a name="${syllable.id}"></a>
                <div class="syllable card-panel">
                    <h4><c:out value="${syllable.text}" /></h4>
                    
                    <p>Frequency: ${syllable.usageCount}</p>
                    <p>Revision: #${syllable.revisionNumber}</p>
                    <div class="divider" style="margin: 1em 0;"></div>
                    <a class="editLink" href="<spring:url value='/content/syllable/edit/${syllable.id}' />"><i class="material-icons">edit</i>Edit</a>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <%--<div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/syllable/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add syllable"><i class="material-icons">add</i></a>
    </div>--%>
</content:section>
