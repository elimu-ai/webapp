<content:title>
    <fmt:message key="content" />
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <%-- TODO: show progress bar for each content type --%>
        
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
        
        <div class="col s12 m6">
            <div class="card indigo darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">looks_one</i> <fmt:message key="numbers" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/number/list' />"><fmt:message key="view.list" /> (${numberCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card teal darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">text_format</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/letter/list' />"><fmt:message key="view.list" /> (${letterCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card green darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">queue_music</i> <fmt:message key="syllables" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/syllable/list' />"><fmt:message key="view.list" /> (${syllableCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card green darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/word/list' />"><fmt:message key="view.list" /> (${wordCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card lime darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">book</i> <fmt:message key="storybooks" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/storybook/list' />"><fmt:message key="view.list" /> (${storyBookCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5><fmt:message key="multimedia" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card amber darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">audiotrack</i> <fmt:message key="audios" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/audio/list' />"><fmt:message key="view.list" /> (${audioCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card orange darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">image</i> <fmt:message key="images" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/image/list' />"><fmt:message key="view.list" /> (${imageCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card deep-orange darken-4">
                <div class="card-content white-text">
                    <span class="card-title"><i class="material-icons">movie</i> <fmt:message key="videos" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/video/list' />"><fmt:message key="view.list" /> (${videoCount})</a>
                </div>
            </div>
        </div>
        
        <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
            <a class="btn-floating btn-large deep-purple lighten-1" title="Add content">
                <i class="large material-icons">add</i>
            </a>
            <ul>
                <li><a href="<spring:url value='/content/number/create' />" class="btn-floating btn-large indigo tooltipped" data-position="left" data-delay="110" data-tooltip="<fmt:message key="add.number" />"><i class="material-icons">looks_one</i></a></li>
                <li><a href="<spring:url value='/content/letter/create' />" class="btn-floating btn-large teal darken-1 tooltipped" data-position="left" data-delay="100" data-tooltip="<fmt:message key="add.letter" />"><i class="material-icons">text_format</i></a></li>
                <%--<li><a href="<spring:url value='/content/syllable/create' />" class="btn-floating btn-large green darken-1 tooltipped" data-position="left" data-delay="100" data-tooltip="<fmt:message key="add.syllable" />"><i class="material-icons">queue_music</i></a></li>--%>
                <li><a href="<spring:url value='/content/word/create' />" class="btn-floating btn-large green tooltipped" data-position="left" data-delay="90" data-tooltip="<fmt:message key="add.word" />"><i class="material-icons">sms</i></a></li>
                <li><a href="<spring:url value='/content/storybook/create' />" class="btn-floating btn-large lime tooltipped" data-position="left" data-delay="80" data-tooltip="<fmt:message key="add.storybook" />"><i class="material-icons">book</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/audio/create' />" class="btn-floating btn-large amber tooltipped" data-position="left" data-delay="70" data-tooltip="<fmt:message key="add.audio" />"><i class="material-icons">audiotrack</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/image/create' />" class="btn-floating btn-large orange tooltipped" data-position="left" data-delay="60" data-tooltip="<fmt:message key="add.image" />"><i class="material-icons">image</i></a></li>
                <li><a href="<spring:url value='/content/multimedia/video/create' />" class="btn-floating btn-large deep-orange tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.video" />"><i class="material-icons">movie</i></a></li>
            </ul>
        </div>
    </div>
</content:section>
