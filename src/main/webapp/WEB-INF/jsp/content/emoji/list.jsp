<content:title>
    Emojis (${fn:length(emojis)})
</content:title>

<content:section cssId="emojiListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/emoji/list/emojis.csv' />">
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
    
        <c:if test="${not empty emojis}">
            <table class="bordered highlight">
                <thead>
                    <th>Glyph</th>
                    <th>Unicode version</th>
                    <th>Unicode Emoji version</th>
                    <th>Content labels</th>
                    <th>Revision</th>
                </thead>
                <tbody>
                    <c:forEach var="emoji" items="${emojis}">
                        <tr class="emoji">
                            <td style="font-size: 6em;">
                                <a name="${emoji.id}"></a>
                                <a class="editLink" href="<spring:url value='/content/emoji/edit/${emoji.id}' />">${emoji.glyph}</a>
                            </td>
                            <td>
                                ${emoji.unicodeVersion}
                            </td>
                            <td>
                                ${emoji.unicodeEmojiVersion}
                            </td>
                            <td>
                                <c:forEach var="word" items="${emoji.words}">
                                    <div class="chip">
                                        <a href="<spring:url value='/content/word/edit/${word.id}' />">
                                            ${word.text}
                                        </a>
                                    </div>
                                </c:forEach>
                            </td>
                            <td>
                                <p>
                                    #${emoji.revisionNumber}
                                </p>
                                <p>
                                    <a href="<spring:url value='/content/storybook/edit/${emoji.id}#contribution-events' />" style="display: flex;">
                                        <span class="peerReviewStatusContainer" data-status="${emoji.peerReviewStatus}">
                                            Peer-review: <code>${emoji.peerReviewStatus}</code>
                                        </span>
                                    </a>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/emoji/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="Add emoji"><i class="material-icons">add</i></a>
    </div>
</content:section>
