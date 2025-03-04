<content:title>
    <fmt:message key="emojis" /> (${fn:length(emojis)})
</content:title>

<content:section cssId="emojiListPage">
    <div class="section row">
        <a id="exportToCsvButton" class="right btn waves-effect waves-light grey-text white" 
           href="<spring:url value='/content/emoji/list/emojis.csv' />">
            <fmt:message key="export.to.csv" /><i class="material-icons right">vertical_align_bottom</i>
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
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
    
        <c:if test="${not empty emojis}">
            <table class="bordered highlight">
                <thead>
                    <th><fmt:message key="glyph" /></th>
                    <th><fmt:message key="unicode.version" /></th>
                    <th><fmt:message key="unicode.emoji.version" /></th>
                    <th><fmt:message key="content.labels" /></th>
                    <th><fmt:message key="revision" /></th>
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
                                #${emoji.revisionNumber}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a id="createButton" href="<spring:url value='/content/emoji/create' />" class="btn-floating btn-large tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.emoji" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
