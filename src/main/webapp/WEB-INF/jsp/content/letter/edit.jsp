<content:title>
    <fmt:message key="edit.letter" />
</content:title>

<content:section cssId="letterEditPage">
    <c:choose>
        <c:when test="${letter.peerReviewStatus == 'APPROVED'}">
            <c:set var="peerReviewStatusColor" value="teal lighten-5" />
        </c:when>
        <c:when test="${letter.peerReviewStatus == 'NOT_APPROVED'}">
            <c:set var="peerReviewStatusColor" value="deep-orange lighten-4" />
        </c:when>
        <c:otherwise>
            <c:set var="peerReviewStatusColor" value="" />
        </c:otherwise>
    </c:choose>
    <div class="chip right ${peerReviewStatusColor}" style="margin-top: 1.14rem;">
        <a href="#contribution-events">
            <fmt:message key="peer.review" />: ${letter.peerReviewStatus}
        </a>
    </div>
    
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="letter">
            <tag:formErrors modelAttribute="letter" />
            
            <form:hidden path="revisionNumber" value="${letter.revisionNumber}" />
            <form:hidden path="usageCount" value="${letter.usageCount}" />
            <input type="hidden" name="timeStart" value="${timeStart}" />

            <div class="row">
                <div class="input-field col s12">
                    <form:label path="text" cssErrorClass="error"><fmt:message key='text' /></form:label>
                    <form:input path="text" cssErrorClass="error" />
                </div>
            </div>

            <div class="row">
                <div class="input-field col">
                    <select id="diacritic" name="diacritic">
                        <option value="false" <c:if test="${not letter.diacritic}">selected="selected"</c:if>><fmt:message key="no" /></option>
                        <option value="true" <c:if test="${letter.diacritic}">selected="selected"</c:if>><fmt:message key="yes" /></option>
                    </select>
                    <label for="diacritic"><fmt:message key="diacritic" /></label>
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
            <a href="<spring:url value='/content/letter/delete/${letter.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider" style="margin: 2em 0;"></div>
    
    <a name="contribution-events"></a>
    <h5><fmt:message key="contributions" /> 👩🏽‍💻</h5>
    <div id="contributionEvents" class="collection">
        <c:forEach var="letterContributionEvent" items="${letterContributionEvents}">
            <a name="contribution-event_${letterContributionEvent.id}"></a>
            <div class="collection-item">
                <span class="badge">
                    <fmt:message key="revision" /> #${letterContributionEvent.revisionNumber} 
                    (<fmt:formatNumber maxFractionDigits="0" value="${letterContributionEvent.timeSpentMs / 1000 / 60}" /> min). 
                    <fmt:formatDate value="${letterContributionEvent.timestamp.time}" pattern="yyyy-MM-dd HH:mm" />
                </span>
                <a href="<spring:url value='/content/contributor/${letterContributionEvent.contributor.id}' />">
                    <div class="chip">
                        <c:choose>
                            <c:when test="${not empty letterContributionEvent.contributor.imageUrl}">
                                <img src="${letterContributionEvent.contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty letterContributionEvent.contributor.providerIdWeb3}">
                                <img src="https://effigy.im/a/<c:out value="${letterContributionEvent.contributor.providerIdWeb3}" />.png" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty letterContributionEvent.contributor.firstName}">
                                <c:out value="${letterContributionEvent.contributor.firstName}" />&nbsp;<c:out value="${letterContributionEvent.contributor.lastName}" />
                            </c:when>
                            <c:when test="${not empty letterContributionEvent.contributor.providerIdWeb3}">
                                ${fn:substring(letterContributionEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(letterContributionEvent.contributor.providerIdWeb3, 38, 42)}
                            </c:when>
                        </c:choose>
                    </div>
                </a>
                <c:if test="${not empty letterContributionEvent.comment}">
                    <blockquote><c:out value="${letterContributionEvent.comment}" /></blockquote>
                </c:if>
                
                <%-- List peer reviews below each contribution event --%>
                <c:forEach var="letterPeerReviewEvent" items="${letterPeerReviewEvents}">
                    <c:if test="${letterPeerReviewEvent.letterContributionEvent.id == letterContributionEvent.id}">
                        <div class="row peerReviewEvent indent" data-approved="${letterPeerReviewEvent.isApproved()}">
                            <div class="col s4">
                                <a href="<spring:url value='/content/contributor/${letterPeerReviewEvent.contributor.id}' />">
                                    <div class="chip">
                                        <c:choose>
                                            <c:when test="${not empty letterPeerReviewEvent.contributor.imageUrl}">
                                                <img src="${letterPeerReviewEvent.contributor.imageUrl}" />
                                            </c:when>
                                            <c:when test="${not empty letterPeerReviewEvent.contributor.providerIdWeb3}">
                                                <img src="https://effigy.im/a/<c:out value="${letterPeerReviewEvent.contributor.providerIdWeb3}" />.png" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<spring:url value='/static/img/placeholder.png' />" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty letterPeerReviewEvent.contributor.firstName}">
                                                <c:out value="${letterPeerReviewEvent.contributor.firstName}" />&nbsp;<c:out value="${letterPeerReviewEvent.contributor.lastName}" />
                                            </c:when>
                                            <c:when test="${not empty letterPeerReviewEvent.contributor.providerIdWeb3}">
                                                ${fn:substring(letterPeerReviewEvent.contributor.providerIdWeb3, 0, 6)}...${fn:substring(letterPeerReviewEvent.contributor.providerIdWeb3, 38, 42)}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </a>
                            </div>
                            <div class="col s4">
                                <code class="peerReviewStatus">
                                    <c:choose>
                                        <c:when test="${letterPeerReviewEvent.isApproved()}">
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
    <h5 class="center"><fmt:message key="resources" /></h5>
    <div class="card-panel deep-purple lighten-5">
        <c:if test="${applicationScope.configProperties['content.language'] == 'HIN'}">
            Hindi resources:
            <ul>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Help:IPA/Hindi_and_Urdu" target="_blank">Wikipedia: Help:IPA/Hindi and Urdu</a>
                </li>
                <li>
                    <a href="https://en.wikipedia.org/wiki/Devanagari#Letters" target="_blank">Wikipedia: Devanagari - Letters</a>
                </li>
                <li>
                    <a href="https://www.omniglot.com/writing/hindi.htm" target="_blank">Omniglot: Hindi (हिन्दी)</a>
                </li>
            </ul>
            
            <div class="divider" style="margin: 1em 0;"></div>
        </c:if>
        
        General resources:
        <ul>
            <li>
                <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">elimu.ai Wiki: Localization</a>
            </li>
            <li>
                <a href="https://docs.google.com/document/d/e/2PACX-1vSZ7fc_Rcz24PGYaaRiy3_UUj_XZGl_jWs931RiGkcI2ft4DrN9PMb28jbndzisWccg3h5W_ynyxVU5/pub#h.835fthbx76vy" target="_blank">Creating Localizable Learning Apps</a>
            </li>
        </ul>
    </div>
</content:aside>
