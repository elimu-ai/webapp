<content:title>
    <fmt:message key="edit.image" />
</content:title>

<content:section cssId="imageEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="image" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="image" />

            <div class="row">
                <div class="input-field col s12">
                    <form:select path="language" cssErrorClass="error">
                        <c:set var="select"><fmt:message key='select' /></c:set>
                        <form:option value="" label="-- ${select} --" />
                        <form:options items="${languages}" />
                    </form:select>
                    <form:label path="language" cssErrorClass="error"><fmt:message key='language' /></form:label>
                </div>
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

            <button class="btn waves-effect waves-light" type="submit" name="action">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>

<content:aside>
    <%--<h5><fmt:message key="preview" /></h5>--%>
    
    <div class="previewContainer valignwrapper" style="position: relative;">
        <img src="<spring:url value='/img/device-nexus-5.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContent" style="position: absolute; top: 35%; font-size: 5em; width: 100%; text-align: center;">
            <img src="<spring:url value='/image/${image.title}.${fn:toLowerCase(image.imageType)}' />" 
                 alt="${image.title}"
                 style="width: 64%;" />
        </div>
    </div>
</content:aside>
