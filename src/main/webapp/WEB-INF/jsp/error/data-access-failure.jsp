<%@page isErrorPage="true" %>

<content:title>
    <fmt:message key="error.http.500" />
</content:title>

<content:section cssId="errorPage" cssClass="code500">
    <p>
        An error occurred: 
        <hr />
        ${pageContext.exception['class'].name}
    </p>
    <div style="clear: both"></div>
</content:section>
