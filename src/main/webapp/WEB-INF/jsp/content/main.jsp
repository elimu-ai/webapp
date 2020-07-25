<content:title>
    <fmt:message key="educational.content" />
</content:title>

<content:section cssId="mainContentPage">
    <div class="section row">
        <div class="col s12">
            <h5><fmt:message key="text" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">record_voice_over</i> <fmt:message key="allophones" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/allophone/list' />"><fmt:message key="view.list" /> (${allophoneCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">looks_one</i> <fmt:message key="numbers" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${numberCount/1001 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/number/list' />"><fmt:message key="view.list" /> (${numberCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">text_format</i> <fmt:message key="letters" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/letter/list' />"><fmt:message key="view.list" /> (${letterCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">queue_music</i> <fmt:message key="syllables" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/syllable/list' />"><fmt:message key="view.list" /> (${syllableCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">sms</i> <fmt:message key="words" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${wordCount/2000 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/word/list' />"><fmt:message key="view.list" /> (${wordCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">emoji_emotions</i> <fmt:message key="emojis" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/emoji/list' />"><fmt:message key="view.list" /> (${emojiCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12">
            <h5><fmt:message key="multimedia" /></h5>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">image</i> <fmt:message key="images" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/image/list' />"><fmt:message key="view.list" /> (${imageCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">audiotrack</i> <fmt:message key="audios" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/audio/list' />"><fmt:message key="view.list" /> (${audioCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">book</i> <fmt:message key="storybooks" /></span>
                </div>
                <div class="card-action">
                    <div class="progress">
                        <div class="determinate" style="width: ${storyBookCount/104 * 100}%"></div>
                    </div>
                    <a href="<spring:url value='/content/storybook/list' />"><fmt:message key="view.list" /> (${storyBookCount})</a>
                </div>
            </div>
        </div>
        
        <div class="col s12 m6">
            <div class="card">
                <div class="card-content">
                    <span class="card-title"><i class="material-icons">movie</i> <fmt:message key="videos" /></span>
                </div>
                <div class="card-action">
                    <a href="<spring:url value='/content/multimedia/video/list' />"><fmt:message key="view.list" /> (${videoCount})</a>
                </div>
            </div>
        </div>
    </div>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="top.contributors" /> 🏆</h5>
    <div class="card-panel deep-purple lighten-5">
        <b><fmt:message key="storybooks" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributorWithStoryBookContributions" items="${contributorsWithStoryBookContributions}">
                <li>
                    <div class="chip">
                        <img src="<spring:url value='${contributorWithStoryBookContributions.imageUrl}' />" alt="${contributorWithStoryBookContributions.firstName}" /> 
                        <c:out value="${contributorWithStoryBookContributions.firstName}" />&nbsp;<c:out value="${contributorWithStoryBookContributions.lastName}" />
                    </div> (${storyBookContributionsCountMap[contributorWithStoryBookContributions.id]})
                </li>
            </c:forEach>
        </ol>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <b><fmt:message key="words" /></b><br />
        <ol style="list-style-type: inherit;">
            <c:forEach var="contributorWithWordContributions" items="${contributorsWithWordContributions}">
                <li>
                    <div class="chip">
                        <img src="<spring:url value='${contributorWithWordContributions.imageUrl}' />" alt="${contributorWithWordContributions.firstName}" /> 
                        <c:out value="${contributorWithWordContributions.firstName}" />&nbsp;<c:out value="${contributorWithWordContributions.lastName}" />
                    </div> (${wordContributionsCountMap[contributorWithWordContributions.id]})
                </li>
            </c:forEach>
        </ol>
    </div>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center">Token Holders</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            Active contributors get rewarded with 
            <a href="https://mainnet.aragon.org/#/elimuai/0xee45d21cb426420257bd4a1d9513bcb499ff443a/" target="_blank">elimu.ai Community Tokens</a>.
        </p>
        <p>
            All token holders can participate in the community's decision making.
        </p>
        <div id="tokenHoldersContainer">
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
            <p>
                Loading...
            </p>
        </div>
        <script>
            /**
             * Copied from AragonRestController.java
             */
            function getBaseUrl() {
                console.info("getBaseUrl")
                let domain = "62.75.236.14"; // DEV/TEST
                <c:if test="${applicationScope.configProperties['env'] == 'PROD'}">
                    domain = "85.93.91.26";
                </c:if>
                return "http://" + domain + ":3000";
            }

            $(function() {
                // Fetch Aragon token holders from Aragon Connect (via the REST API)
                $.ajax({
                    dataType: "json",
                    url: "<spring:url value='/rest/v2/aragon/token-holders' />",
                    success: function(tokenHolders) {
                        console.info("success");

                        let htmlString = '<table class="striped">';
                        htmlString += '    <thead>';
                        htmlString += '        <tr>';
                        htmlString += '            <th>Holder</th>';
                        htmlString += '            <th>Balance</th>';
                        htmlString += '        </tr>';
                        htmlString += '    </thead>';
                        htmlString += '    <tbody>';
                        tokenHolders.forEach(function(tokenHolder, index) {
                            htmlString += '<tr>';
                            htmlString += '    <td>';
                            htmlString += '        <div class="chip">';
                            htmlString += '            <img src="' + getBaseUrl() +'/identicon/' + tokenHolder.address + '" />' + tokenHolder.address.substring(0, 6) + "..." + tokenHolder.address.substring(tokenHolder.address.length - 4, tokenHolder.address.length);
                            htmlString += '        </div>';
                            htmlString += '    </td>';
                            htmlString += '    <td>';
                            htmlString += '        ' + tokenHolder.balance/1000000000000000000;
                            htmlString += '    </td>';
                            htmlString += '</tr>';
                        });
                        htmlString += '</tbody>';
                        htmlString += '</table>';
                        $('#tokenHoldersContainer').html(htmlString);
                    }
                });
            });
        </script>
    </div>
</content:aside>
