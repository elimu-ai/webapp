<div class="row">
    <div class="col s6 m4 l3">
        <div class="card contributor">
            <div class="card-image">
                <c:choose>
                    <c:when test="${not empty contributor2.imageUrl}">
                        <img src="${contributor2.imageUrl}" />
                    </c:when>
                    <c:when test="${not empty contributor2.providerIdWeb3}">
                        <img src="http://62.75.236.14:3000/identicon/<c:out value="${contributor2.providerIdWeb3}" />" />
                    </c:when>
                    <c:otherwise>
                        <img src="<spring:url value='/static/img/placeholder.png' />" />
                    </c:otherwise>
                </c:choose>
                <span class="card-title">
                    <c:choose>
                        <c:when test="${not empty contributor2.firstName}">
                            <c:out value="${contributor2.firstName}" />&nbsp;<c:out value="${contributor2.lastName}" />
                        </c:when>
                        <c:when test="${not empty contributor2.providerIdWeb3}">
                            ${fn:substring(contributor2.providerIdWeb3, 0, 6)}...${fn:substring(contributor2.providerIdWeb3, 38, 42)}
                        </c:when>
                    </c:choose>
                </span>
            </div>
            <div class="card-content">
                <p class="grey-text">Roles: ${contributor2.roles}</p>
                <blockquote><c:out value="${contributor2.motivation}" /></blockquote>
                <c:if test="${not empty contributor2.providerIdWeb3}">
                    <p>
                        <c:set var="etherscanUrl" value="https://etherscan.io" />
                        <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                            <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                        </c:if>
                        <a class="btn tokenButton" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor2.providerIdWeb3}" target="_blank">
                            <code><span id="tokenBalance_${contributor.id}">0</span> $ELIMU</code>
                        </a>
                        <script>
                            $(function() {
                                var contributorAddress = '${contributor2.providerIdWeb3}';
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
                <c:if test="${not empty contributor2.usernameGitHub}">
                    <a href="http://github.com/${contributor2.usernameGitHub}" target="_blank" title="GitHub">
                        <svg style="width: 32px; height: 32px; top: 6px; position: relative; right: 5px;" viewBox="0 0 50 50">
                            <path d="M32,16c-8.8,0-16,7.2-16,16c0,7.1,4.6,13.1,10.9,15.2 c0.8,0.1,1.1-0.3,1.1-0.8c0-0.4,0-1.4,0-2.7c-4.5,1-5.4-2.1-5.4-2.1c-0.7-1.8-1.8-2.3-1.8-2.3c-1.5-1,0.1-1,0.1-1 c1.6,0.1,2.5,1.6,2.5,1.6c1.4,2.4,3.7,1.7,4.7,1.3c0.1-1,0.6-1.7,1-2.1c-3.6-0.4-7.3-1.8-7.3-7.9c0-1.7,0.6-3.2,1.6-4.3 c-0.2-0.4-0.7-2,0.2-4.2c0,0,1.3-0.4,4.4,1.6c1.3-0.4,2.6-0.5,4-0.5c1.4,0,2.7,0.2,4,0.5c3.1-2.1,4.4-1.6,4.4-1.6 c0.9,2.2,0.3,3.8,0.2,4.2c1,1.1,1.6,2.5,1.6,4.3c0,6.1-3.7,7.5-7.3,7.9c0.6,0.5,1.1,1.5,1.1,3c0,2.1,0,3.9,0,4.4 c0,0.4,0.3,0.9,1.1,0.8C43.4,45.1,48,39.1,48,32C48,23.2,40.8,16,32,16z" />
                        </svg>
                    </a>
                </c:if>
            </div>
        </div>
    </div>

    <div class="col s6 m8 l9">
        <div class="card-panel">
            <script src="<spring:url value='/static/js/chart.bundle.min-2.8.0.js' />"></script>
            <link rel="stylesheet" href="<spring:url value='/static/css/chart.min-2.8.0.css' />" />
            <canvas id="myChart" width="400" height="200"></canvas>
            <script>
                var ctx = document.getElementById('myChart');
                var data = {
                    labels: ['Storybooks', 'Audios', 'Words', 'Numbers'],
                    datasets: [
                        {
                            label: 'Contributions',
                            backgroundColor: 'rgba(149,117,205, 0.5)',
                            data: [${storyBookContributionsCount}, ${audioContributionsCount}, ${wordContributionsCount}, ${numberContributionsCount}]
                        },
                        {
                            label: 'Peer-reviews',
                            backgroundColor: 'rgba(255,183,77, 0.5)',
                            data: [${storyBookPeerReviewsCount}, ${audioPeerReviewsCount}, ${wordPeerReviewsCount}, ${numberPeerReviewsCount}]
                        }
                    ]
                };
                var options = {};
                var myRadarChart = new Chart(ctx, {
                    type: 'radar',
                    data: data,
                    options: options
                });
            </script>
        </div>
    </div>
</div>
