<content:title>
    <fmt:message key="videos" /> (${fn:length(videos)})
</content:title>

<content:section cssId="videoListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="video" items="${videos}">
            <div class="col s12 m6 l4">
                <a name="${video.id}"></a>
                <div class="video card">
                    <video controls="true">
                        <source src="<spring:url value='/video/${video.id}.${fn:toLowerCase(video.videoFormat)}' />" />
                    </video>
                    
                    <div class="card-content">
                        <h4>"${video.title}"</h4>
                        <p><fmt:message key="revision" />: ${video.revisionNumber}</p>
                        <p><fmt:message key="literacy.skills" />: ${video.literacySkills}</p>
                        <p><fmt:message key="numeracy.skills" />: ${video.numeracySkills}</p>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a class="editLink" href="<spring:url value='/content/multimedia/video/edit/${video.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating btn-large teal tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a>
    </div>
</content:section>
