<content:title>
    Edit sound
</content:title>

<content:section cssId="soundEditPage">
    <a href="#contribution-events" class="right" style="margin-top: 1.75rem;">
        <span class="peerReviewStatusContainer" data-status="${sound.peerReviewStatus}">
            Peer-review: <code>${sound.peerReviewStatus}</code>
        </span>
    </a>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="sound">
            <tag:formErrors modelAttribute="sound" />
            
            <form:hidden path="revisionNumber" value="${sound.revisionNumber}" />
            <form:hidden path="usageCount" value="${sound.usageCount}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error">Value (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error">Value (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="soundType" name="soundType">
                        <option value="">-- Select --</option>
                        <c:forEach var="soundType" items="${soundTypes}">
                            <option value="${soundType}" <c:if test="${soundType == sound.soundType}">selected="selected"</c:if>><c:out value="${soundType}" /></option>
                        </c:forEach>
                    </select>
                    <label for="soundType">Sound type</label>
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
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <a name="contribution-events"></a>
    <h5>Contributions 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="soundContributionEvent" items="${soundContributionEvents}">
            <a name="contribution-event_${soundContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    Revision #${soundContributionEvent.revisionNumber} 
                    (<fmt:formatDate value="${soundContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />)
                </span>
                <c:set var="chipContributor" value="${soundContributionEvent.contributor}" />
                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                <c:if test="${not empty soundContributionEvent.comment}">
                    <blockquote><c:out value="${soundContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="soundPeerReviewEvent" items="${soundPeerReviewEvents}">
                    <c:if test="${soundPeerReviewEvent.soundContributionEvent.id == soundContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${soundPeerReviewEvent.getApproved()}">
                            <div class="col s4">
                                <c:set var="chipContributor" value="${soundPeerReviewEvent.contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${soundPeerReviewEvent.getApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${soundPeerReviewEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" /> 
                            </div>
                            <c:if test="${not empty soundPeerReviewEvent.comment}">
                                <div class="col s12 comment"><c:out value="${soundPeerReviewEvent.comment}" /></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Audio</h5>
    <audio controls="true" autoplay="true">
        <source src="<spring:url value='/static/sound/sampa_${sound.valueSampa}.wav' />" />
    </audio>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h5 class="center">Resources</h5>

    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <div class="card-panel deep-purple lighten-5">
            Hindi resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">IPA for Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Hindustani_phonology" target="_blank">Hindustani phonology</a>
                </li>
                <li>
                    <a href="https://omniglot.com/writing/hindi.htm#alphabet" target="_blank">Devanāgarī alphabet for Hindi</a>
                </li>
            </ol>
        </div>
    </c:if>
    <c:if test="${applicationScope.configProperties['content.language'] == 'THA'}">
        <div class="card-panel deep-purple lighten-5">
            Thai resources:
            <ol style="list-style-type: inherit;">
                <li>
                    <a href="https://en.wikipedia.org/wiki/Thai_script" target="_blank">Thai script - Wikipedia</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Thai" target="_blank">IPA/Thai - Wikipedia</a>
                </li>
            </ol>
        </div>
    </c:if>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
            <li>
                <a href="https://en.wikipedia.org/wiki/International_Phonetic_Alphabet">International Phonetic Alphabet (IPA)</a>
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
                <%-- Check if the current sound is used by the letter-sound. --%>
                <c:set var="isUsedByLetterSound" value="false" />
                <c:forEach var="s" items="${letterSound.sounds}">
                    <c:if test="${sound.id == s.id}">
                        <c:set var="isUsedByLetterSound" value="true" />
                    </c:if>
                </c:forEach>
                <c:if test="${isUsedByLetterSound}">
                    <tr>
                        <td>
                            ${letterSound.usageCount}
                        </td>
                        <td>
                            " <c:forEach var="letter" items="${letterSound.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />">${letter.text}</a> </c:forEach> "
                        </td>
                        <td>
                            ➞
                        </td>
                        <td>
                            / <c:forEach var="s" items="${letterSound.sounds}"><a href="<spring:url value='/content/sound/edit/${s.id}' />"><c:if test="${s.id == sound.id}"><span class='diff-highlight'></c:if>${s.valueIpa}<c:if test="${s.id == sound.id}"></span></c:if></a> </c:forEach> /
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
