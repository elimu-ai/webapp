<content:title>
    Videos (${fn:length(videos)})
</content:title>

<content:section cssId="videoListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/video/list/videos.csv' />">
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
        
        <c:forEach var="video" items="${videos}">
            <div class="col s12 m6 l4">
                <a name="${video.id}"></a>
                <div class="video card cid-${video.checksumGitHub != null}">
                    <a class="valign-wrapper" href="<spring:url value='/content/multimedia/video/edit/${video.id}' />">
                        <div class="card-image" style="position: absolute; width: 100%; text-align: center;">
                            <i class="material-icons" style=" background-color: rgba(0,0,0, 0.2); color: rgba(255,255,255, 0.8); padding: 0.5rem; font-size: 3em; border-width: 2px; border-color: rgba(255,255,255, 0.8); border-style: solid; border-radius: 50%;">play_arrow</i>
                        </div>
                        <img src="<spring:url value='/video/${video.id}_r${video.revisionNumber}_thumbnail.png' />" alt="${video.title}" />
                    </a>
                    
                    <div class="card-content">
                        <h4>"${video.title}"</h4>
                        
                        <p>Literacy skills: ${video.literacySkills}</p>
                        <p>Numeracy skills: ${video.numeracySkills}</p>
                        <p>
                            Letters: 
                            <c:forEach var="letter" items="${video.letters}">
                                <div class="chip">
                                    ${letter.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            Numbers: 
                            <c:forEach var="number" items="${video.numbers}">
                                <div class="chip">
                                    ${number.value}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            Words: 
                            <c:forEach var="word" items="${video.words}">
                                <div class="chip">
                                    ${word.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p>Revision: #${video.revisionNumber}</p>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a class="editLink" href="<spring:url value='/content/multimedia/video/edit/${video.id}' />"><i class="material-icons">edit</i>Edit</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add video"><i class="material-icons">add</i></a>
    </div>
</content:section>
