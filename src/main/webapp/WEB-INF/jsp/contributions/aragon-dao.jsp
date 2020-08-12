<content:title>
    <fmt:message key="aragon.dao" />
</content:title>

<content:section cssId="aragonDaoPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <h4>Token Holders</h4>
        <p class="grey-text">
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.aragon.org/#/elimuai/0xcfc816708740e121dd280969f05cc7e95d977177/" target="_blank">
                        elimu.ai Community Token (ECT)
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="https://mainnet.aragon.org/#/elimuai/0xee45d21cb426420257bd4a1d9513bcb499ff443a/" target="_blank">
                        elimu.ai Community Token (ECT)
                    </a>
                </c:otherwise>
            </c:choose>
        </p>
        <div id="tokenHoldersContainer">
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
            <p>
                Loading...
            </p>
        </div>
        <script>
            /**
             * Copied from AragonRestController.java
             */
            function getBaseUrl() {
                console.info("getBaseUrl")
                let domain = "62.75.236.14"; // DEV/TEST
                <c:if test="${applicationScope.configProperties['env'] == 'PROD'}">
                    domain = "85.93.91.26";
                </c:if>
                return "http://" + domain + ":3000";
            }
            
            $(function() {
                // Fetch token holders from Aragon Connect (via the REST API)
                $.ajax({
                    dataType: "json",
                    url: "<spring:url value='/rest/v2/aragon/token-holders' />",
                    success: function(tokenHolders) {
                        console.info("success");
                        
                        let htmlString = '<table class="striped">';
                        htmlString += '    <thead>';
                        htmlString += '        <tr>';
                        htmlString += '            <th>Holder</th>';
                        htmlString += '            <th>Balance</th>';
                        htmlString += '        </tr>';
                        htmlString += '    </thead>';
                        htmlString += '    <tbody>';
                        tokenHolders.forEach(function(tokenHolder, index) {
                            htmlString += '<tr>';
                            htmlString += '    <td>';
                            htmlString += '        <div class="chip">';
                            htmlString += '            <img src="' + getBaseUrl() +'/identicon/' + tokenHolder.address + '" />' + tokenHolder.address;
                            htmlString += '        </div>';
                            htmlString += '    </td>';
                            htmlString += '    <td>';
                            htmlString += '        ' + tokenHolder.balance/1000000000000000000;
                            htmlString += '    </td>';
                            htmlString += '</tr>';
                        });
                        htmlString += '</tbody>';
                        htmlString += '</table>';
                        $('#tokenHoldersContainer').html(htmlString);
                    }
                });
            });
        </script>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
        
        <h4>Votes</h4>
        <div id="votesContainer">
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
            <p>
                Loading...
            </p>
        </div>
        <script>
            <c:set var="ethereumNetwork" value="rinkeby" />
            <c:if test="${applicationScope.configProperties['env'] == 'PROD'}">
                <c:set var="ethereumNetwork" value="mainnet" />
            </c:if>
            $(function() {
                $.ajax({
                    dataType: "json",
                    url: "<spring:url value='/rest/v2/aragon/votes' />",
                    success: function(votes) {
                        console.info("success");
                        
                        let htmlString = '<table class="striped">';
                        htmlString += '    <thead>';
                        htmlString += '        <tr>';
                        htmlString += '            <th>Vote</th>';
                        htmlString += '            <th>Approval %</th>';
                        htmlString += '        </tr>';
                        htmlString += '    </thead>';
                        htmlString += '    <tbody>';
                        votes.forEach(function(vote, index) {
                            htmlString += '<tr>';
                            htmlString += '    <td><a href="https://${ethereumNetwork}.aragon.org/#/elimuai/' + vote.appAddress + '/vote/' + index + '" target="_blank">' + vote.description + '</a></td>';
                            htmlString += '    <td>' + (vote.yea/vote.votingPower*100).toFixed(2) + '%<div class="progress"><div class="determinate" style="width: ' + vote.yea/vote.votingPower*100 + '%"></div></div></td>';
                            htmlString += '</tr>';
                        });
                        htmlString += '</tbody>';
                        htmlString += '</table>';
                        $('#votesContainer').html(htmlString);
                    }
                });
            });
        </script>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
    
        <h4>Payments</h4>
        <div id="paymentsContainer">
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
            <p>
                Loading...
            </p>
        </div>
        <script>
            /**
             * Copied from AragonRestController.java
             */
            function getBaseUrl() {
                console.info("getBaseUrl");
                let domain = "62.75.236.14"; // DEV/TEST
                <c:if test="${applicationScope.configProperties['env'] == 'PROD'}">
                    domain = "85.93.91.26";
                </c:if>
                return "http://" + domain + ":3000";
            }

            /**
             * E.g. "#content" --> "<span class="chip deep-purple lighten-2 white-text">#content</span>"
             */
            function getLabeledReference(reference) {
                console.info("getLabeledReference");
                if (reference.includes('#content') 
                        && !reference.includes('#content-')) {
                    reference = reference.replace('#content', '<span class="chip deep-purple lighten-2 white-text">#content</span>');
                } else if (reference.includes('#content-ben')) {
                    reference = reference.replace('#content-ben', '<span class="chip deep-purple lighten-2 white-text">#content-ben</span>');
                } else if (reference.includes('#content-eng')) {
                    reference = reference.replace('#content-eng', '<span class="chip deep-purple lighten-2 white-text">#content-eng</span>');
                } else if (reference.includes('#content-fil')) {
                    reference = reference.replace('#content-fil', '<span class="chip deep-purple lighten-2 white-text">#content-fil</span>');
                } else if (reference.includes('#content-hin')) {
                    reference = reference.replace('#content-hin', '<span class="chip deep-purple lighten-2 white-text">#content-hin</span>');
                } else if (reference.includes('#content-swa')) {
                    reference = reference.replace('#content-creatio-swa', '<span class="chip deep-purple lighten-2 white-text">#content-swa</span>');
                } else if (reference.includes('#content-urd')) {
                    reference = reference.replace('#content-urd', '<span class="chip deep-purple lighten-2 white-text">#content-urd</span>');
                } else if (reference.includes('#engineering')) {
                    reference = reference.replace('#engineering', '<span class="chip light-green darken-2 white-text">#engineering</span>');
                } else if (reference.includes('#distribution')) {
                    reference = reference.replace('#distribution', '<span class="chip cyan darken-1 white-text">#distribution</span>');
                }
                return reference;
            }

            $(function() {
                // Fetch finance transactions from Aragon Connect (via the REST API)
                $.ajax({
                    dataType: "json",
                    url: "<spring:url value='/rest/v2/aragon/finance-transactions' />",
                    success: function(financeTransactions) {
                        console.info("success");

                        let htmlString = '<table class="striped responsive-table">';
                        htmlString += '    <thead>';
                        htmlString += '        <tr>';
                        htmlString += '            <th>Recipient</th>';
                        htmlString += '            <th>Amount</th>';
                        htmlString += '            <th>Reference</th>';
                        htmlString += '        </tr>';
                        htmlString += '    </thead>';
                        htmlString += '    <tbody>';
                        financeTransactions.forEach(function(financeTransaction, index) {
                            // Exclude incoming transactions
                            if (financeTransaction.isIncoming) {
                                return;
                            }

                            // TODO: add support for any type of token
                            let ethAmount = Number((financeTransaction.amount/1000000000000000000).toFixed(5));

                            htmlString += '<tr>';
                            htmlString += '    <td>';
                            htmlString += '        <div class="chip">';
                            htmlString += '            <img src="' + getBaseUrl() +'/identicon/' + financeTransaction.entity + '" />' + financeTransaction.entity.substring(0, 6) + "..." + financeTransaction.entity.substring(financeTransaction.entity.length - 4, financeTransaction.entity.length);
                            htmlString += '        </div><br />';
                            htmlString += '    </td>';
                            htmlString += '    <td>';
                            htmlString += '        ' + ethAmount.toFixed(5) + ' ETH';
                            htmlString += '    </td>';
                            htmlString += '    <td style="width: 50%;">';
                            htmlString += '        ' + getLabeledReference(financeTransaction.reference);
                            htmlString += '    </td>';                

                            htmlString += '</tr>';
                        });
                        htmlString += '</tbody>';
                        htmlString += '</table>';
                        $('#paymentsContainer').html(htmlString);
                    }
                });
            });
        </script>

        <div class="divider" style="margin: 1em 0;"></div>

        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.aragon.org/#/elimuai/0x7a2711f547696fff3fc1788b9295c5464e4a7edd/" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://mainnet.aragon.org/#/elimuai/0x25e71ca07476c2a65c289c7c6bd6910079e119e6/" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Decentralized Autonomous Organization (DAO)</h5>
    <p>
        The <a href="https://${ethereumNetwork}.aragon.org/#/elimuai" target="_blank">elimu.ai Community DAO</a> is empowered by <a href="https://aragon.org" target="_blank">Aragon</a>. 
        Aragon gives internet communities the power to freely organize and collaborate without borders or intermediaries.
    </p>
    <p>
        For more information, see our blog post: <a href="https://medium.com/elimu-ai/why-did-the-elimu-ai-community-decide-to-use-aragon-e9863c135111" target="_blank">Why Did the elimu.ai Community Decide to Use Aragon? 🦅</a>.
    </p>
    <a href="https://${ethereumNetwork}.aragon.org/#/elimuai">
        <img src="https://wiki.aragon.org/design/artwork/Powered_By/SVG/Powered_By_White.svg" alt="Powered by Aragon" />
    </a>
</content:aside>
