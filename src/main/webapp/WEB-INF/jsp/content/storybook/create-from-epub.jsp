<content:title>
    Upload ePUB file
</content:title>

<content:section cssId="storyBookCreateFromEPubPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="storyBook" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="storyBook" />
            <c:if test="${not empty existingStoryBook}">
                <div class="row">
                    <div class="col s12 m8 l6">
                        <div class="storyBook card">
                            <div class="headband"></div>
                            <c:set var="coverImageUrl" value="" />
                            <c:if test="${not empty existingStoryBook.coverImage}">
                                <c:set var="coverImageUrl" value="${existingStoryBook.coverImage.url}" />
                            </c:if>
                            <a class="editLink" href="<spring:url value='/content/storybook/edit/${existingStoryBook.id}' />">
                                <div class="card-image checksumGitHub-${existingStoryBook.coverImage.checksumGitHub != null}" style="background-image: url(<spring:url value='${coverImageUrl}' />); background-color: #DDD;">
                                    <span class="card-title"><c:out value="${existingStoryBook.title}" /></span>
                                </div>
                            </a>
                            <div class="card-content">
                                <p class="grey-text" style="margin-bottom: 0.5em;"><c:out value="${existingStoryBook.description}" /></p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <div class="col s12" style="padding: 3em; background: #F4F4F4; border: 2px dashed #CCC; border-radius: 8px;">
                <p class="center grey-text">
                    Drag & drop your ePUB file here, or select a file.
                </p>
                <div class="file-field input-field col s8 offset-s2 center">
                    <div class="btn <c:if test="${empty contributor}">disabled</c:if>">
                        <span>File</span>
                        <%--<form:input path="bytes" type="file" />--%>
                        <input name="bytes" id="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                    <script>
                        $(function() {
                            $('#bytes').on('change', function() {
                                console.info('#bytes on change');
                                
                                $(this).parents('form').submit();
                                Materialize.toast('Processing ePUB. Please wait...', 4000, 'rounded');
                            });
                        });
                    </script>
                </div>
            </div>
            <div style="clear: both;"></div>
        </form:form>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://digitallibrary.io" target="_blank">Global Digital Library</a>
            </li>
            <li>
                <a href="https://reader.letsreadasia.org" target="_blank">Let's Read</a>
            </li>
            <li>
                <a href="https://storyweaver.org.in/stories" target="_blank">StoryWeaver</a>
            </li>
        </ol>
    </div>
</content:aside>
