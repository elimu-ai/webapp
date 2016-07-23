<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainContentPage">
    <h4><fmt:message key="latest.uploads" /></h4>
    
    <div class="section row">
        <%-- TODO: show progress bar --%>
        
        <%-- TODO: list latest content creation events --%>
        
        <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
            <a class="btn-floating btn-large red" title="Add content">
                <i class="large material-icons">add</i>
            </a>
            <ul>
                <li><a href="<spring:url value='/content/number/create' />" class="btn-floating red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a></li>
                <li><a href="<spring:url value='/content/letter/create' />" class="btn-floating yellow darken-1 tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a></li>
                <li><a href="<spring:url value='/content/word/create' />" class="btn-floating green tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating blue tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating teal tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a></li>
            </ul>
        </div>
    </div>
</content:section>
