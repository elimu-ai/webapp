<content:title>
    <fmt:message key="storybooks" /> (${fn:length(storyBooksLevel1) + fn:length(storyBooksLevel2) + fn:length(storyBooksLevel3) + fn:length(storyBooksLevel4) + fn:length(storyBooksUnleveled)})
</content:title>

<content:section cssId="storyBookListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/storybook/list/storybooks.csv' />">
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
            <fmt:message key="to.add.new.content.click.the.button.below" /> You can also <a href="<spring:url value='/content/storybook/peer-reviews' />">peer-review</a> storybooks.
        </p>
    </div>
    
    <div class="row">
        <h4><fmt:message key="reading.level.LEVEL1" /></h4>
        <c:if test="${empty storyBooksLevel1}">
            <fmt:message key="none" />
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel1}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                    </c:if>
                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                        <p><fmt:message key="revision" />: #${storyBook.revisionNumber}</p>
                        <p>
                            <fmt:message key="peer.review" />: 
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="peerReviewStatusColor" value="" />
                                </c:otherwise>
                            </c:choose>
                            <span class="chip ${peerReviewStatusColor}">
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />">
                                    ${storyBook.peerReviewStatus}
                                </a>
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4><fmt:message key="reading.level.LEVEL2" /></h4>
        <c:if test="${empty storyBooksLevel2}">
            <fmt:message key="none" />
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel2}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                    </c:if>
                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                        <p><fmt:message key="revision" />: ${storyBook.revisionNumber}</p>
                        <p>
                            <fmt:message key="peer.review" />: 
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="peerReviewStatusColor" value="" />
                                </c:otherwise>
                            </c:choose>
                            <span class="chip ${peerReviewStatusColor}">
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />">
                                    ${storyBook.peerReviewStatus}
                                </a>
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4><fmt:message key="reading.level.LEVEL3" /></h4>
        <c:if test="${empty storyBooksLevel3}">
            <fmt:message key="none" />
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel3}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                    </c:if>
                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                        <p><fmt:message key="revision" />: ${storyBook.revisionNumber}</p>
                        <p>
                            <fmt:message key="peer.review" />: 
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="peerReviewStatusColor" value="" />
                                </c:otherwise>
                            </c:choose>
                            <span class="chip ${peerReviewStatusColor}">
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />">
                                    ${storyBook.peerReviewStatus}
                                </a>
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4><fmt:message key="reading.level.LEVEL4" /></h4>
        <c:if test="${empty storyBooksLevel4}">
            <fmt:message key="none" />
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel4}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                    </c:if>
                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                        <p><fmt:message key="revision" />: ${storyBook.revisionNumber}</p>
                        <p>
                            <fmt:message key="peer.review" />: 
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="peerReviewStatusColor" value="" />
                                </c:otherwise>
                            </c:choose>
                            <span class="chip ${peerReviewStatusColor}">
                                <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />">
                                    ${storyBook.peerReviewStatus}
                                </a>
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <c:if test="${not empty storyBooksUnleveled}">
        <div class="row">
            <h4><fmt:message key="reading.level" />: Unleveled</h4>
            <c:forEach var="storyBook" items="${storyBooksUnleveled}">
                <div class="col s12 m6 l4">
                    <a name="${storyBook.id}"></a>
                    <div class="storyBook card">
                        <div class="headband"></div>
                        <c:set var="coverImageUrl" value="/static/img/placeholder.png" />
                        <c:if test="${not empty storyBook.coverImage}">
                            <c:set var="coverImageUrl" value="/image/${storyBook.coverImage.id}_r${storyBook.coverImage.revisionNumber}.${fn:toLowerCase(storyBook.coverImage.imageFormat)}" />
                        </c:if>
                        <a href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                            <div class="card-image" style="background-image: url(<spring:url value='${coverImageUrl}' />);">
                                <span class="card-title"><c:out value="${storyBook.title}" /></span>
                            </div>
                        </a>
                        <div class="card-content">
                            <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                            <p><fmt:message key="reading.level.${storyBook.readingLevel}" /></p>
                            <p><fmt:message key="revision" />: ${storyBook.revisionNumber}</p>
                            <p>
                                <fmt:message key="peer.review" />: 
                                <c:choose>
                                    <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                        <c:set var="peerReviewStatusColor" value="teal lighten-5" />
                                    </c:when>
                                    <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                        <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="peerReviewStatusColor" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <span class="chip ${peerReviewStatusColor}">
                                    <a href="<spring:url value='/content/storybook/edit/${storyBook.id}#contribution-events' />">
                                        ${storyBook.peerReviewStatus}
                                    </a>
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/storybook/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.storybook" />"><i class="material-icons">add</i></a>
        <ul>
            <li><a href="<spring:url value='/content/storybook/create-from-epub' />" class="btn-floating btn-large tooltipped" data-position="left" data-tooltip="<fmt:message key="upload.epub.file" />"><i class="material-icons">cloud_upload</i></a></li>
        </ul>
    </div>
</content:section>
