<content:title>
    <fmt:message key="edit.image" />
</content:title>

<content:section cssId="imageEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="image" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="image" />

            <div class="row">
                <form:hidden path="locale" value="${image.locale}" />
                <form:hidden path="revisionNumber" value="${image.revisionNumber}" />
                <form:hidden path="imageFormat" value="${number.imageFormat}" />
                <form:hidden path="contentType" value="${number.contentType}" />
                <form:hidden path="dominantColor" value="${number.dominantColor}" />
                
                <div class="input-field col s12">
                    <form:label path="title" cssErrorClass="error"><fmt:message key='title' /></form:label>
                    <form:input path="title" cssErrorClass="error" />
                </div>
                <div class="input-field col s12">
                    <i class="material-icons prefix">link</i>
                    <form:label path="attributionUrl" cssErrorClass="error"><fmt:message key='attribution.url' /></form:label>
                    <form:input path="attributionUrl" cssErrorClass="error" type="url" />
                </div>
                <div class="file-field input-field col s12">
                    <div class="btn">
                        <span><fmt:message key='file' /></span>
                        <form:input path="bytes" type="file" />
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" />
                    </div>
                </div>
                <%--<div class="input-field col s12">
                    <form:select path="imageType" cssErrorClass="error">
                        <c:set var="select"><fmt:message key='select' /></c:set>
                        <form:option value="" label="-- ${select} --" />
                        <form:options items="${imageTypes}" />
                    </form:select>
                    <form:label path="imageType" cssErrorClass="error"><fmt:message key='image.type' /></form:label>
                </div>--%>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/multimedia/image/delete/${image.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider"></div>
    
    <%--<p>
        <fmt:message key="last.update" />:<br />
        <div class="chip">
            <img src="${image.contributor.imageUrl}" alt="" class="circle responsive-img">
            <c:out value="${image.contributor.firstName}" />&nbsp;<c:out value="${image.contributor.lastName}" />
        </div> 
        <fmt:formatDate value="${image.calendar.time}" type="both" timeStyle="short" />
    </p>--%>
    
    <div id="disqus_thread"></div>
    <script>
    /**
    * RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
    * LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
    */
    /*
    var disqus_config = function () {
    this.page.url = PAGE_URL; // Replace PAGE_URL with your page's canonical URL variable
    this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };
    */
    (function() { // DON'T EDIT BELOW THIS LINE
    var d = document, s = d.createElement('script');

    s.src = '//literacyapp.disqus.com/embed.js';

    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
    })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
</content:section>

<content:aside>
    <%--<h5><fmt:message key="preview" /></h5>--%>
    
    <div class="previewContainer valignwrapper">
        <img src="<spring:url value='/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContentContainer">
            <div id="previewContent" class="valign-wrapper" 
                 style="
                    background-image: url(<spring:url value='/image/${image.id}.${fn:toLowerCase(image.imageFormat)}' />);
                 ">
                <h5 class="white-text" style="
                    position: absolute; 
                    bottom: 0; 
                    width: 100%;
                    text-shadow: 1px 1px rgba(0,0,0, .8);
                    /*background-color: rgba(0,0,0, .1);*/
                    ">${image.title}</h5>
            </div>
        </div>
    </div>
    
    <script>
        $(function() {
            console.debug("dominantColor: ${image.dominantColor}");
            $('#previewContent').css("background-color", "${image.dominantColor}");
            $('nav').removeClass("black");
            $('nav').css("background-color", "${image.dominantColor}");
        });
    </script>
</content:aside>
