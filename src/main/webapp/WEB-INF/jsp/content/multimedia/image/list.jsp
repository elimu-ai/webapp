<content:title>
    Images (${fn:length(images)})
</content:title>

<content:section cssId="imageListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/image/list/images.csv' />">
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
            To add new content, click the button below.
        </p>
        
        <c:forEach var="image" items="${images}">
            <div class="col s12 m6 l4">
                <a name="${image.id}"></a>
                <div class="image card">
                    <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                        <div class="card-image cid-${image.cid != null}" style="background-image: url(<spring:url value='${image.url}' />);">
                            <span class="card-title"><c:out value="${image.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p>Literacy skills: ${image.literacySkills}</p>
                        <p>Numeracy skills: ${image.numeracySkills}</p>
                        <p>
                            Letters: 
                            <c:forEach var="letter" items="${image.letters}">
                                <div class="chip">
                                    ${letter.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            Numbers: 
                            <c:forEach var="number" items="${image.numbers}">
                                <div class="chip">
                                    ${number.value}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            Words: 
                            <c:forEach var="word" items="${image.words}">
                                <div class="chip">
                                    <a href="<spring:url value='/content/word/edit/${word.id}' />">
                                        ${word.text} <c:out value=" ${emojisByWordId[word.id]}" />
                                    </a>
                                </div>
                            </c:forEach>
                        </p>
                        <p>Revision: #${image.revisionNumber}</p>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a class="editLink" href="<spring:url value='/content/multimedia/image/edit/${image.id}' />"><i class="material-icons">edit</i>Edit</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add image"><i class="material-icons">add</i></a>
    </div>
</content:section>
