<content:title>
    Edit letter
</content:title>

<content:section cssId="letterEditPage">
    <a href="#contribution-events" class="right" style="margin-top: 1.75rem;">
        <span class="peerReviewStatusContainer" data-status="${letter.peerReviewStatus}">
            Peer-review: <code>${letter.peerReviewStatus}</code>
        </span>
    </a>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <form:hidden path="revisionNumber" value="${letter.revisionNumber}" />
            <form:hidden path="usageCount" value="${letter.usageCount}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error">Text</form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>

            <div class="row">
                <div class="input-field col">
                    <select id="diacritic" name="diacritic">
                        <option value="false" <c:if test="${not letter.diacritic}">selected="selected"</c:if>>No</option>
                        <option value="true" <c:if test="${letter.diacritic}">selected="selected"</c:if>>Yes</option>
                    </select>
                    <label for="diacritic">Diacritic</label>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment">Comment</label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn-large waves-effect waves-light" type="submit" <c:if test="${empty contributor}">disabled</c:if>>
                Edit <i class="material-icons right">send</i>
            </button>
            <c:if test="${not empty contributor}">
                <a href="<spring:url value='/content/letter/delete/${letter.id}' />" class="waves-effect waves-red red-text btn-flat right">Delete</a>
            </c:if>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <a name="contribution-events"></a>
    <h5>Contributions 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="letterContributionEvent" items="${letterContributionEvents}">
            <a name="contribution-event_${letterContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${letterContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${letterContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${letterContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                <c:if test="${not empty letterContributionEvent.comment}">
                    <blockquote><c:out value="${letterContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="letterPeerReviewEvent" items="${letterPeerReviewEvents}">
                    <c:if test="${letterPeerReviewEvent.letterContributionEvent.id == letterContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${letterPeerReviewEvent.getApproved()}">
                            <div class="col s4">
                                <c:set var="chipContributor" value="${letterReviewEvent.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${letterPeerReviewEvent.getApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${letterPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty letterPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${letterPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Resources</h5>
    <div class="card-panel deep-purple lighten-5">
        <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">Wikipedia: Help:IPA/Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Devanagari#Letters" target="_blank">Wikipedia: Devanagari - Letters</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/hindi.htm" target="_blank">Omniglot: Hindi (हिन्दी)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Thai" target="_blank">Wikipedia: Help:IPA/Thai</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Thai_script" target="_blank">Wikipedia: Thai script</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/thai.htm" target="_blank">Omniglot: Thai (ภาษาไทย)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        <c:if test="${applicationScope.configProperties['content.language'] == 'VIE'}">
            Vietnamese resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Vietnamese" target="_blank">Wikipedia: Help:IPA/Vietnamese</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Vietnamese_alphabet" target="_blank">Wikipedia: Vietnamese alphabet</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/vietnamese.htm" target="_blank">Omniglot: Vietnamese (tiếng việt / 㗂越)</a>
                </li>
            </ol>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ol>
    </div>

    <div class="divider" style="margin: 1.5em 0;"></div>

    <h5 class="center">Letter-sound correspondences</h5>

    <table class="bordered highlight">
        <thead>
            <th>Frequency</th>
            <th>Letters</th>
            <th></th>
            <th>Sounds</th>
        </thead>
        <tbody>
            <c:forEach var="letterSound" items="${letterSounds}">
                <%-- Check if the current letter is used by the letter-sound. --%>
                <c:set var="isUsedByLetterSound" value="false" />
                <c:forEach var="l" items="${letterSound.letters}">
                    <c:if test="${letter.id == l.id}">
                        <c:set var="isUsedByLetterSound" value="true" />
                    </c:if>
                </c:forEach>
                <c:if test="${isUsedByLetterSound}">
                    <tr>
                        <td>
                            ${letterSound.usageCount}
                        </td>
                        <td>
                            " <c:forEach var="l" items="${letterSound.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />"><c:if test="${l.id == letter.id}"><span class='diff-highlight'></c:if>${l.text}<c:if test="${l.id == letter.id}"></span></c:if></a> </c:forEach> "
                        </td>
                        <td>
                            ➞
                        </td>
                        <td>
                            / <c:forEach var="sound" items="${letterSound.sounds}"><a href="<spring:url value='/content/sound/edit/${sound.id}' />">${sound.valueIpa}</a> </c:forEach> /
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
