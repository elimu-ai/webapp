<div class="row">
    <div class="col s6 m4 l3">
        <div class="card contributor">
            <div class="card-image">
                <c:choose>
                    <c:when test="${not empty contributor2.imageUrl}">
                        <img src="${contributor2.imageUrl}" />
                    </c:when>
                    <c:when test="${not empty contributor2.providerIdWeb3}">
                        <img src="https://effigy.im/a/<c:out value="${contributor2.providerIdWeb3}" />.png" />
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
                <blockquote><c:out value="${contributor2.motivation}" /></blockquote>
                <p class="grey-text">Joined: <fmt:formatDate value="${contributor2.registrationTime.time}" pattern="yyyy-MM-dd" /></p>
                <p>Roles: ${contributor2.roles}</p>
                <c:if test="${not empty contributor2.providerIdWeb3}">
                    <p style="margin-top: 1em;">
                        <c:set var="etherscanUrl" value="https://etherscan.io" />
                        <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                            <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                        </c:if>
                        <a class="btn tokenButton" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor2.providerIdWeb3}" target="_blank">
                            <code><span id="tokenBalance_${contributor2.id}">0</span> $ELIMU</code>
                        </a>
                        <script>
                            $(function() {
                                var contributorAddress = '${contributor2.providerIdWeb3}';
                                getBalance(contributorAddress).then(function(result) {
                                    console.info('result: ' + result);

                                    var tokenBalance = result / 1000000000000000000;
                                    console.info('tokenBalance: ' + tokenBalance);

                                    var tokenBalanceFormatted = Intl.NumberFormat().format(Math.round(tokenBalance));
                                    console.info('tokenBalanceFormatted ' + tokenBalanceFormatted);

                                    $('#tokenBalance_${contributor2.id}').html(tokenBalanceFormatted);
                                });
                            });
                        </script>
                    </p>
                </c:if>
                <p style="margin-top: 1em;">
                    <c:if test="${not empty contributor2.usernameGitHub}">
                        <a href="https://github.com/${contributor2.usernameGitHub}" target="_blank" title="GitHub">
                            <svg width="32px" height="32px" viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    fill-rule="evenodd"
                                    clip-rule="evenodd"
                                    d="M7.49936 0.850006C3.82767 0.850006 0.849976 3.8273 0.849976 7.50023C0.849976 10.4379 2.75523 12.9306 5.39775 13.8104C5.73047 13.8712 5.85171 13.6658 5.85171 13.4895C5.85171 13.3315 5.846 12.9135 5.84273 12.3587C3.99301 12.7604 3.60273 11.4671 3.60273 11.4671C3.30022 10.6988 2.86423 10.4942 2.86423 10.4942C2.26044 10.0819 2.90995 10.0901 2.90995 10.0901C3.57742 10.137 3.9285 10.7755 3.9285 10.7755C4.52167 11.7916 5.48512 11.4981 5.86396 11.3279C5.92438 10.8984 6.09625 10.6053 6.28608 10.4391C4.80948 10.2709 3.25695 9.70063 3.25695 7.15241C3.25695 6.42615 3.51618 5.83298 3.94157 5.368C3.87299 5.1998 3.64478 4.52375 4.00689 3.60807C4.00689 3.60807 4.56494 3.42926 5.83538 4.28941C6.36568 4.14204 6.93477 4.06856 7.50018 4.0657C8.06518 4.06856 8.63386 4.14204 9.16498 4.28941C10.4346 3.42926 10.9918 3.60807 10.9918 3.60807C11.3548 4.52375 11.1266 5.1998 11.0584 5.368C11.4846 5.83298 11.7418 6.42615 11.7418 7.15241C11.7418 9.70716 10.1868 10.2693 8.70571 10.4338C8.94412 10.6392 9.15681 11.045 9.15681 11.6655C9.15681 12.5542 9.14865 13.2715 9.14865 13.4895C9.14865 13.6675 9.26867 13.8745 9.60588 13.8095C12.2464 12.9282 14.15 10.4375 14.15 7.50023C14.15 3.8273 11.1723 0.850006 7.49936 0.850006Z"
                                    fill="currentColor" />
                            </svg>
                        </a>
                    </c:if>
                    <a href="https://discord.gg/9rz4XYJJDE" target="_blank" title="Discord">
                        <svg width="32px" height="32px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-discord">
                            <path d="M6.552 6.712c-.456 0-.816.4-.816.888s.368.888.816.888c.456 0 .816-.4.816-.888.008-.488-.36-.888-.816-.888zm2.92 0c-.456 0-.816.4-.816.888s.368.888.816.888c.456 0 .816-.4.816-.888s-.36-.888-.816-.888z"/>
                            <path d="M13.36 0H2.64C1.736 0 1 .736 1 1.648v10.816c0 .912.736 1.648 1.64 1.648h9.072l-.424-1.48 1.024.952.968.896L15 16V1.648C15 .736 14.264 0 13.36 0zm-3.088 10.448s-.288-.344-.528-.648c1.048-.296 1.448-.952 1.448-.952-.328.216-.64.368-.92.472-.4.168-.784.28-1.16.344a5.604 5.604 0 0 1-2.072-.008 6.716 6.716 0 0 1-1.176-.344 4.688 4.688 0 0 1-.584-.272c-.024-.016-.048-.024-.072-.04-.016-.008-.024-.016-.032-.024-.144-.08-.224-.136-.224-.136s.384.64 1.4.944c-.24.304-.536.664-.536.664-1.768-.056-2.44-1.216-2.44-1.216 0-2.576 1.152-4.664 1.152-4.664 1.152-.864 2.248-.84 2.248-.84l.08.096c-1.44.416-2.104 1.048-2.104 1.048s.176-.096.472-.232c.856-.376 1.536-.48 1.816-.504.048-.008.088-.016.136-.016a6.521 6.521 0 0 1 4.024.752s-.632-.6-1.992-1.016l.112-.128s1.096-.024 2.248.84c0 0 1.152 2.088 1.152 4.664 0 0-.68 1.16-2.448 1.216z"/>
                        </svg>
                    </a>
                </p>
            </div>
        </div>
    </div>

    <div class="col s6 m8 l9">
        <div class="card-panel">
            <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
            <canvas id="chart" width="400" height="200"></canvas>
            <script>
                var ctx = document.getElementById('chart');
                var data = {
                    labels: ['Storybooks', 'Words', 'Numbers', 'Audios'],
                    datasets: [
                        {
                            data: [${storyBookContributionsCount}, ${wordContributionsCount}, ${numberContributionsCount}, ${audioContributionsCount}],
                            label: 'Contributions',
                            backgroundColor: 'rgba(149,117,205, 0.5)', // #9575cd deep-purple lighten-2
                            tension: 0.5
                        },
                        {
                            data: [${storyBookPeerReviewsCount}, ${wordPeerReviewsCount}, ${numberPeerReviewsCount}, ${audioPeerReviewsCount}],
                            label: 'Peer-reviews',
                            backgroundColor: 'rgba(77,182,172, 0.5)', // #4db6ac teal lighten-2
                            tension: 0.5
                        }
                    ]
                };
                var options = {};
                new Chart(ctx, {
                    type: 'radar',
                    data: data,
                    options: options
                });
            </script>
        </div>
    </div>
</div>
