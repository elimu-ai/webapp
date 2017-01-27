<content:title>
    <fmt:message key="images" /> (${fn:length(images)})
</content:title>

<content:section cssId="imageListPage">
    <div class="section row">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <c:forEach var="image" items="${images}">
            <div class="col s12 m6 l4">
                <a name="${image.id}"></a>
                <div class="image card">
                    <a href="<spring:url value='/content/multimedia/image/edit/${image.id}' />">
                        <img src="<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageFormat)}' />" alt="${image.title}" />
                    </a>
                    
                    <div class="card-content">
                        <h4>${image.title}</h4>
                        
                        <p><fmt:message key="literacy.skills" />: ${image.literacySkills}</p>
                        <p><fmt:message key="numeracy.skills" />: ${image.numeracySkills}</p>
                        <p>
                            <fmt:message key="letters" />: 
                            <c:forEach var="letter" items="${image.letters}">
                                <div class="chip">
                                    ${letter.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            <fmt:message key="numbers" />: 
                            <c:forEach var="number" items="${image.numbers}">
                                <div class="chip">
                                    ${number.value}
                                </div>
                            </c:forEach>
                        </p>
                        <p>
                            <fmt:message key="words" />: 
                            <c:forEach var="word" items="${image.words}">
                                <div class="chip">
                                    ${word.text}
                                </div>
                            </c:forEach>
                        </p>
                        <p><fmt:message key="revision" />: #${image.revisionNumber}</p>
                        <div class="divider" style="margin: 1em 0;"></div>
                        <a class="editLink" href="<spring:url value='/content/multimedia/image/edit/${image.id}' />"><i class="material-icons">edit</i><fmt:message key="edit" /></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating btn-large orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a>
    </div>
</content:section>
