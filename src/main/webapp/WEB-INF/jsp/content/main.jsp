<content:title>
    Educational content
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <div class="col s12">
            <h5>Text</h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_fields</i> Letters</span>
                </div>
                <div class="card-action">
                    <a id="letterListLink" href="<spring:url value='/content/letter/list' />">View list (${letterCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">music_note</i> Sounds</span>
                </div>
                <div class="card-action">
                    <a id="soundListLink" href="<spring:url value='/content/sound/list' />">View list (${soundCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">emoji_symbols</i> Letter-sounds</span>
                </div>
                <div class="card-action">
                    <a id="letterSoundListLink" href="<spring:url value='/content/letter-sound/list' />">View list (${letterSoundCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">queue_music</i> Syllables</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/syllable/list' />">View list (${syllableCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">looks_one</i> Numbers</span>
                </div>
                <div class="card-action">
                    <a id="numberListLink" href="<spring:url value='/content/number/list' />">View list (${numberCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> Words</span>
                </div>
                <div class="card-action">
                    <a id="wordListLink" href="<spring:url value='/content/word/list' />">View list (${wordCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">emoji_emotions</i> Emojis</span>
                </div>
                <div class="card-action">
                    <a id="emojiListLink" href="<spring:url value='/content/emoji/list' />">View list (${emojiCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5>Multimedia</h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">image</i> Images</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/image/list' />">View list (${imageCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> Storybooks</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/storybook/list' />">View list (${storyBookCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">movie</i> Videos</span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/video/list' />">View list (${videoCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>
