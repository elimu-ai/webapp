<content:title>
    <fmt:message key="aragon.dao" />
</content:title>

<content:section cssId="aragonDaoPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <h4>Token Holders</h4>
        <p class="grey-text">
            <a href="https://mainnet.aragon.org/#/elimuai/0xee45d21cb426420257bd4a1d9513bcb499ff443a/" target="_blank">
                elimu.ai Community Token (ECT)
            </a>
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
                // Fetch Aragon token holders from the REST API
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
                            htmlString += '    <td><a href="https://mainnet.aragon.org/#/elimuai/' + vote.appAddress + '/vote/' + index + '" target="_blank">Vote #' + index + '</a> ("' + vote.metadata + '")</td>';
                            htmlString += '    <td>' + vote.yea/vote.votingPower*100 + '%<div class="progress"><div class="determinate" style="width: ' + vote.yea/vote.votingPower*100 + '%"></div></div></td>';
                            htmlString += '</tr>';
                        });
                        htmlString += '</tbody>';
                        htmlString += '</table>';
                        $('#votesContainer').html(htmlString);
                    }
                });
            });
        </script>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Decentralized Autonomous Organization (DAO)</h5>
    <p>
        The <a href="https://mainnet.aragon.org/#/elimuai" target="_blank">elimu.ai Community DAO</a> is empowered by <a href="https://aragon.org" target="_blank">Aragon</a>. 
        Aragon gives internet communities the power to freely organize and collaborate without borders or intermediaries.
    </p>
    <p>
        For more information, see our blog post: <a href="https://medium.com/elimu-ai/why-did-the-elimu-ai-community-decide-to-use-aragon-e9863c135111" target="_blank">Why Did the elimu.ai Community Decide to Use Aragon? 🦅</a>.
    </p>
    <a href="https://mainnet.aragon.org/#/elimuai">
        <img src="https://wiki.aragon.org/design/artwork/Powered_By/SVG/Powered_By_White.svg" alt="Powered by Aragon" />
    </a>
</content:aside>
