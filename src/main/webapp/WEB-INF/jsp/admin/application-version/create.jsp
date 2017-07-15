<content:title>
    <fmt:message key="upload.new.apk.file" />
</content:title>

<content:section cssId="applicationVersionCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="applicationVersion" enctype="multipart/form-data">
            <tag:formErrors modelAttribute="applicationVersion" />
            
            <p>
                <fmt:message key='package.name' />: ${applicationVersion.application.packageName}
            </p>

            <div class="row">
                <form:hidden path="application" value="${applicationVersion.application.id}" />
                <form:hidden path="contributor" value="${contributor.id}" />
                <div class="input-field col s6">
                    <form:label path="versionCode" cssErrorClass="error"><fmt:message key='version.code' /></form:label>
                    <form:input path="versionCode" cssErrorClass="error" type="number" />
                </div>
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
                
            <div class="input-field col s12">
                <form:label path="startCommand" cssErrorClass="error"><fmt:message key='start.command' /> (adb shell &lt;command&gt;)</form:label>
                <form:input path="startCommand" cssErrorClass="error" />
            </div>

            <button id="submitButton" class="btn green waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
