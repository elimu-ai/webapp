<content:title>
    <fmt:message key="contributors" /> (${fn:length(contributors)})
</content:title>

<content:section cssId="contributorListPage">
    <div class="row">
        <c:forEach var="contributor" items="${contributors}">
            <div class="col s6 m4 l3">
                <div class="card contributor">
                    <a href="<spring:url value='/content/contributor/${contributor.id}' />">
                        <div class="card-image">
                            <c:choose>
                                <c:when test="${not empty contributor.imageUrl}">
                                    <img src="${contributor.imageUrl}" />
                                </c:when>
                                <c:when test="${not empty contributor.providerIdWeb3}">
                                    <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<spring:url value='/static/img/placeholder.png' />" />
                                </c:otherwise>
                            </c:choose>
                            <span class="card-title">
                                <c:choose>
                                    <c:when test="${not empty contributor.firstName}">
                                        <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                    </c:when>
                                    <c:when test="${not empty contributor.providerIdWeb3}">
                                        ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                    </c:when>
                                </c:choose>
                            </span>
                        </div>
                    </a>
                    <div class="card-content">
                        <p class="grey-text">Roles: ${contributor.roles}</p>
                        <blockquote><c:out value="${contributor.motivation}" /></blockquote>
                        <c:if test="${not empty contributor.providerIdWeb3}">
                            <p>
                                <c:set var="etherscanUrl" value="https://etherscan.io" />
                                <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                                    <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                                </c:if>
                                <a class="btn tokenButton" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor.providerIdWeb3}" target="_blank">
                                    <code><span id="tokenBalance_${contributor.id}">0</span> $ELIMU</code>
                                </a>
                                <script>
                                    $(function() {
                                        var contributorAddress = '${contributor.providerIdWeb3}';
                                        getBalance(contributorAddress).then(function(result) {
                                            console.info('result: ' + result);

                                            var tokenBalance = result / 1000000000000000000;
                                            console.info('tokenBalance: ' + tokenBalance);

                                            var tokenBalanceFormatted = Intl.NumberFormat().format(tokenBalance);
                                            console.info('tokenBalanceFormatted ' + tokenBalanceFormatted);

                                            $('#tokenBalance_${contributor.id}').html(tokenBalanceFormatted);
                                        });
                                    });
                                </script>
                            </p>
                        </c:if>
                        <c:if test="${not empty contributor.usernameGitHub}">
                            <a href="http://github.com/${contributor.usernameGitHub}" target="_blank" title="GitHub">
                                <svg style="width: 32px; height: 32px; top: 6px; position: relative; right: 5px;" viewBox="0 0 50 50">
                                    <path d="M32,16c-8.8,0-16,7.2-16,16c0,7.1,4.6,13.1,10.9,15.2 c0.8,0.1,1.1-0.3,1.1-0.8c0-0.4,0-1.4,0-2.7c-4.5,1-5.4-2.1-5.4-2.1c-0.7-1.8-1.8-2.3-1.8-2.3c-1.5-1,0.1-1,0.1-1 c1.6,0.1,2.5,1.6,2.5,1.6c1.4,2.4,3.7,1.7,4.7,1.3c0.1-1,0.6-1.7,1-2.1c-3.6-0.4-7.3-1.8-7.3-7.9c0-1.7,0.6-3.2,1.6-4.3 c-0.2-0.4-0.7-2,0.2-4.2c0,0,1.3-0.4,4.4,1.6c1.3-0.4,2.6-0.5,4-0.5c1.4,0,2.7,0.2,4,0.5c3.1-2.1,4.4-1.6,4.4-1.6 c0.9,2.2,0.3,3.8,0.2,4.2c1,1.1,1.6,2.5,1.6,4.3c0,6.1-3.7,7.5-7.3,7.9c0.6,0.5,1.1,1.5,1.1,3c0,2.1,0,3.9,0,4.4 c0,0.4,0.3,0.9,1.1,0.8C43.4,45.1,48,39.1,48,32C48,23.2,40.8,16,32,16z" />
                                </svg>
                            </a>
                        </c:if>
                    </div>
                    <div class="card-action">
                        <a href="<spring:url value='/content/contributor/${contributor.id}' />">View contributions</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</content:section>
