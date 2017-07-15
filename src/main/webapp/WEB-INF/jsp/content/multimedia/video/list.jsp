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
                    <a class="valign-wrapper" href="<spring:url value='/content/multimedia/video/edit/${video.id}' />">
                        <div style="position: absolute; width: 100%; text-align: center;">
                            <i class="material-icons" style=" background-color: rgba(0,0,0, 0.2); color: rgba(255,255,255, 0.8); padding: 0.5rem; font-size: 3em; border-width: 2px; border-color: rgba(255,255,255, 0.8); border-style: solid; border-radius: 50%;">play_arrow</i>
                        </div>
                        <img src="<spring:url value='/video/${video.id}/thumbnail.png' />" alt="${video.title}" />
                    </a>
                    
                    <div class="card-content">
                        <h4>"${video.title}"</h4>
                        
                        <p><fmt:message key="literacy.skills" />: ${video.literacySkills}</p>
                        <p><fmt:message key="numeracy.skills" />: ${video.numeracySkills}</p>
                        <p>
                            <fmt:message key="letters" />: 
                            <c:forEach var="letter" items="${video.letters}">
                                <div class="chip">
                                    ${letter.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            <fmt:message key="numbers" />: 
                            <c:forEach var="number" items="${video.numbers}">
                                <div class="chip">
                                    ${number.value}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            <fmt:message key="words" />: 
                            <c:forEach var="word" items="${video.words}">
                                <div class="chip">
                                    ${word.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p><fmt:message key="revision" />: #${video.revisionNumber}</p>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a class="editLink" href="<spring:url value='/content/multimedia/video/edit/${video.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating btn-large deep-orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a>
    </div>
</content:section>
