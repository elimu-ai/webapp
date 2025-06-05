<content:title>
    Storybooks (${fn:length(storyBooksLevel1) + fn:length(storyBooksLevel2) + fn:length(storyBooksLevel3) + fn:length(storyBooksLevel4) + fn:length(storyBooksUnleveled)})
</content:title>

<content:section cssId="storyBookListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/storybook/list/storybooks.csv' />">
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
            To add new content, click the button below. <span style="position: absolute; transform: rotate(-33deg);">👇🏽</span>
        </p>
    </div>
    
    <div class="row">
        <h4>Level 1. Beginning to Read</h4>
        <c:if test="${empty storyBooksLevel1}">
            None
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel1}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                    </c:if>
                    <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p>
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'PENDING'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge deep-purple lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge blue lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge orange darken-2"></span>
                                </c:when>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4>Level 2. Learning to Read</h4>
        <c:if test="${empty storyBooksLevel2}">
            None
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel2}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                    </c:if>
                    <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p>
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'PENDING'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge deep-purple lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge blue lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge orange darken-2"></span>
                                </c:when>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4>Level 3. Reading Independently</h4>
        <c:if test="${empty storyBooksLevel3}">
            None
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel3}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                    </c:if>
                    <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p>
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'PENDING'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge deep-purple lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge blue lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge orange darken-2"></span>
                                </c:when>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <div class="row">
        <h4>Level 4. Reading Proficiently</h4>
        <c:if test="${empty storyBooksLevel4}">
            None
        </c:if>
        <c:forEach var="storyBook" items="${storyBooksLevel4}">
            <div class="col s12 m6 l4">
                <a name="${storyBook.id}"></a>
                <div class="storyBook card">
                    <div class="headband"></div>
                    <c:set var="coverImageUrl" value="" />
                    <c:if test="${not empty storyBook.coverImage}">
                        <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                    </c:if>
                    <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                        <div class="card-image checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                            <span class="card-title"><c:out value="${storyBook.title}" /></span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                        <p>
                            <c:choose>
                                <c:when test="${storyBook.peerReviewStatus == 'PENDING'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge deep-purple lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge blue lighten-2"></span>
                                </c:when>
                                <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                    <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge orange darken-2"></span>
                                </c:when>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <c:if test="${not empty storyBooksUnleveled}">
        <div class="row">
            <h4>Reading level: Unleveled</h4>
            <c:forEach var="storyBook" items="${storyBooksUnleveled}">
                <div class="col s12 m6 l4">
                    <a name="${storyBook.id}"></a>
                    <div class="storyBook card">
                        <div class="headband"></div>
                        <c:set var="coverImageUrl" value="" />
                        <c:if test="${not empty storyBook.coverImage}">
                            <c:set var="coverImageUrl" value="${storyBook.coverImage.url}" />
                        </c:if>
                        <a class="editLink" href="<spring:url value='/content/storybook/edit/${storyBook.id}' />">
                            <div class="card-image checksumGitHub-${storyBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                                <span class="card-title"><c:out value="${storyBook.title}" /></span>
                            </div>
                        </a>
                        <div class="card-content">
                            <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${storyBook.description}" /></p>
                            <p>
                                <c:choose>
                                    <c:when test="${storyBook.peerReviewStatus == 'PENDING'}">
                                        <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge deep-purple lighten-2"></span>
                                    </c:when>
                                    <c:when test="${storyBook.peerReviewStatus == 'APPROVED'}">
                                        <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge blue lighten-2"></span>
                                    </c:when>
                                    <c:when test="${storyBook.peerReviewStatus == 'NOT_APPROVED'}">
                                        <span data-badge-caption="Peer-review: ${storyBook.peerReviewStatus}" class="new badge orange darken-2"></span>
                                    </c:when>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/storybook/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add storybook"><i class="material-icons">add</i></a>
        <ul>
            <li><a href="<spring:url value='/content/storybook/create-from-epub' />" class="btn-floating btn-large tooltipped" data-position="left" data-tooltip="Upload ePUB file"><i class="material-icons">cloud_upload</i></a></li>
        </ul>
    </div>
</content:section>
