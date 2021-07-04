<content:title>
    <fmt:message key="contributors" /> (${fn:length(contributors)})
</content:title>

<content:section cssId="contributorListPage">
    <div class="row">
        <c:forEach var="contributor" items="${contributors}">
            <div class="col s6 m4 l3">
                <div class="card">
                    <div class="card-image">
                        <c:choose>
                            <c:when test="${not empty contributor.imageUrl}">
                                <img src="${contributor.imageUrl}" />
                            </c:when>
                            <c:when test="${not empty contributor.providerIdWeb3}">
                                <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor.providerIdWeb3}" />" />
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value='/static/img/publish-nya.png' />" />
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
                    <div class="card-content">
                        <p class="grey-text">Roles: ${contributor.roles}</p>
                        <blockquote><c:out value="${contributor.motivation}" /></blockquote>
                        <c:if test="${not empty contributor.providerIdWeb3}">
                            <c:set var="etherscanUrl" value="https://etherscan.io" />
                            <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                                <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                            </c:if>
                            <a class="btn tokenButton" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor.providerIdWeb3}" target="_blank">
                                <code><span id="tokenBalance_${contributor.id}">0</span> ELIMU</code>
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
                        </c:if>
                    </div>
                    <div class="card-action">
                        <a href="#">View contributions</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</content:section>
