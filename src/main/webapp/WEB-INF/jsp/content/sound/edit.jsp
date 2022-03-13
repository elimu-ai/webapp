<content:title>
    <fmt:message key="edit.sound" />
</content:title>

<content:section cssId="soundEditPage">
    <c:choose>
        <c:when test="${sound.peerReviewStatus == 'APPROVED'}">
            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
        </c:when>
        <c:when test="${sound.peerReviewStatus == 'NOT_APPROVED'}">
            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
        </c:when>
        <c:otherwise>
            <c:set var="peerReviewStatusColor" value="" />
        </c:otherwise>
    </c:choose>
    <div class="chip right ${peerReviewStatusColor}" style="margin-top: 1.14rem;">
        <a href="#contribution-events">
            <fmt:message key="peer.review" />: ${sound.peerReviewStatus}
        </a>
    </div>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="sound">
            <tag:formErrors modelAttribute="sound" />
            
            <form:hidden path="revisionNumber" value="${sound.revisionNumber}" />
            <form:hidden path="usageCount" value="${sound.usageCount}" />
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="valueIpa" cssErrorClass="error"><fmt:message key='value' /> (IPA)</form:label>
                    <form:input path="valueIpa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <form:label path="valueSampa" cssErrorClass="error"><fmt:message key='value' /> (X-SAMPA)</form:label>
                    <form:input path="valueSampa" cssErrorClass="error" />
                </div>
                
                <div class="input-field col s12">
                    <select id="soundType" name="soundType">
                        <option value="">-- <fmt:message key='select' /> --</option>
                        <c:forEach var="soundType" items="${soundTypes}">
                            <option value="${soundType}" <c:if test="${soundType == sound.soundType}">selected="selected"</c:if>><c:out value="${soundType}" /></option>
                        </c:forEach>
                    </select>
                    <label for="soundType"><fmt:message key="sound.type" /></label>
                </div>
            </div>
            
            <div class="row">
                <div class="input-field col s12">
                    <label for="contributionComment"><fmt:message key='comment' /></label>
                    <textarea id="contributionComment" name="contributionComment" class="materialize-textarea" placeholder="A comment describing your contribution." maxlength="1000"><c:if test="${not empty param.contributionComment}"><c:out value="${param.contributionComment}" /></c:if></textarea>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <a name="contribution-events"></a>
    <h5><fmt:message key="contributions" /> 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="soundContributionEvent" items="${soundContributionEvents}">
            <a name="contribution-event_${soundContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    <fmt:message key="revision" /> #${soundContributionEvent.revisionNumber} 
                    (<fmt:formatNumber maxFractionDigits="0" value="${soundContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:formatDate value="${soundContributionEvent.time.time}" pattern="yyyy-MM-dd HH:mm" />
                </span>
                <a href="<spring:url value='/content/contributor/${soundContributionEvent.contributor.id}' />">
                    <div class="chip">
                        <c:choose>
                            <c:when test="${not empty soundContributionEvent.contributor.imageUrl}">
                                <img src="${soundContributionEvent.contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty soundContributionEvent.contributor.providerIdWeb3}">
                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${soundContributionEvent.contributor.providerIdWeb3}" />" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty soundContributionEvent.contributor.firstName}">
                                <c:out value="${soundContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${soundContributionEvent.contributor.lastName}" />
                            </c:when>
                            <c:when test="${not empty soundContributionEvent.contributor.providerIdWeb3}">
                                ${fn:substring(soundContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(soundContributionEvent.contributor.providerIdWeb3, 38, 42)}
                            </c:when>
                        </c:choose>
                    </div>
                </a>
                <c:if test="${not empty soundContributionEvent.comment}">
                    <blockquote><c:out value="${soundContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="soundPeerReviewEvent" items="${soundPeerReviewEvents}">
                    <c:if test="${soundPeerReviewEvent.soundContributionEvent.id == soundContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${soundPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <a href="<spring:url value='/content/contributor/${soundPeerReviewEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty soundPeerReviewEvent.contributor.imageUrl}">
                                                <img src="${soundPeerReviewEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty soundPeerReviewEvent.contributor.providerIdWeb3}">
                                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${soundPeerReviewEvent.contributor.providerIdWeb3}" />" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty soundPeerReviewEvent.contributor.firstName}">
                                                <c:out value="${soundPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${soundPeerReviewEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty soundPeerReviewEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(soundPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(soundPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${soundPeerReviewEvent.isApproved()}">
                                            APPROVED
                                        </c:when>
                                        <c:otherwise>
                                            NOT_APPROVED
                                        </c:otherwise>
                                    </c:choose>
                                </code>
                            </div>
                            <div class="col s4" style="text-align: right;">
                                <fmt:formatDate value="${soundPeerReviewEvent.time.time}" pattern="yyyy-MM-dd HH:mm" /> 
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
    <h5 class="center"><fmt:message key="audio" /></h5>
    <audio controls="true">
        <source src="<spring:url value='/static/sound/sampa_${sound.valueSampa}.wav' />" />
    </audio>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
        <h5 class="center"><fmt:message key="resources" /></h5>
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
        
        <div class="divider" style="margin: 1.5em 0;"></div>
    </c:if>
        
    <div class="card-panel deep-purple lighten-5">
        General resources:
        <ol style="list-style-type: inherit;">
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/master/LOCALIZATION.md" target="_blank">elimu.ai Wiki</a>
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
    
    <h5 class="center"><fmt:message key="usages" /></h5>
    
    <table class="bordered highlight">
        <thead>
            <th><fmt:message key="frequency" /></th>
            <th><fmt:message key="letters" /></th>
            <th></th>
            <th><fmt:message key="sounds" /></th>
        </thead>
        <tbody>
            <c:forEach var="letterSoundCorrespondence" items="${letterSoundCorrespondences}">
                <%-- Check if the current sound is used by the letter-sound correspondence. --%>
                <c:set var="isUsedByLetterSoundCorrespondence" value="false" />
                <c:forEach var="s" items="${letterSoundCorrespondence.sounds}">
                    <c:if test="${sound.id == s.id}">
                        <c:set var="isUsedByLetterSoundCorrespondence" value="true" />
                    </c:if>
                </c:forEach>
                <c:if test="${isUsedByLetterSoundCorrespondence}">
                    <tr>
                        <td>
                            ${letterSoundCorrespondence.usageCount}
                        </td>
                        <td>
                            " <c:forEach var="letter" items="${letterSoundCorrespondence.letters}"><a href="<spring:url value='/content/letter/edit/${letter.id}' />">${letter.text} </a> </c:forEach> "
                        </td>
                        <td>
                            ➞
                        </td>
                        <td>
                            / <c:forEach var="s" items="${letterSoundCorrespondence.sounds}"><a href="<spring:url value='/content/sound/edit/${s.id}' />"><c:if test="${s.id == sound.id}"><span class='diff-highlight'></c:if>${s.valueIpa}<c:if test="${s.id == sound.id}"></span></c:if></a> </c:forEach> /
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</content:aside>
