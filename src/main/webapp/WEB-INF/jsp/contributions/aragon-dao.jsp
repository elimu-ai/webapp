<content:title>
    <fmt:message key="aragon.dao" />
</content:title>

<content:section cssId="aragonDaoPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <h4>Token Holders 💎</h4>
        <p>
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:when>
                <c:otherwise>
                    <a href="https://etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:otherwise>
            </c:choose>  
            is the governance token used by the elimu.ai Community DAO. Active contributors get rewarded with tokens, 
            and token holders can participate in the community's decision-making.
        </p>
        <div id="tokenHoldersContainer">
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.etherscan.io/token/tokenholderchart/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                        View all token holders <i class="material-icons">launch</i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="https://etherscan.io/token/tokenholderchart/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                        View all token holders <i class="material-icons">launch</i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
        
        <h4>Votes 👍👎</h4>
        <div id="votesContainer">
            <p>
                Token holders can make new proposals, and can voice their support (or not support) for proposals made by 
                other token holders.
            </p>
            <p>
                <c:choose>
                    <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                        <a href="https://voice-rinkeby.aragon.org/tokens/info/#/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                            View all votes <i class="material-icons">launch</i>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="https://voice.aragon.org/tokens/info/#/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank">
                            View all votes <i class="material-icons">launch</i>
                        </a>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
    
        <h4>Payouts 💰</h4>
        <div id="paymentsContainer">
            <p>
                Funds received from <a href="<spring:url value='/contributions/donate' />">donors</a> and the DAO treasury are paid out each month to contributors who provided 
                value to the community in any of these categories of work:
            </p>
            <ol>
                <c:choose>
                    <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                        <li>
                            <a href="https://rinkeby.etherscan.io/address/0xa406C9B6c115A65da97EC7256d0A1fF51F281f71" target="_blank">
                                #content 🎶🎙
                            </a>
                        </li>
                        <li>
                            <a href="https://rinkeby.etherscan.io/address/0xB1C409722B23Cba26dE8660b180d4B55Ed8CB4c0" target="_blank">
                                #engineering 👩🏽‍💻📱
                            </a>
                        </li>
                        <li>
                            <a href="https://rinkeby.etherscan.io/address/0x282EF5377C35E90C33a94833d69dBA880430c9cB" target="_blank">
                                #distribution 🛵💨
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="https://etherscan.io/address/0xa406C9B6c115A65da97EC7256d0A1fF51F281f71" target="_blank">
                                #content 🎶🎙
                            </a>
                        </li>
                        <li>
                            <a href="https://etherscan.io/address/0xB1C409722B23Cba26dE8660b180d4B55Ed8CB4c0" target="_blank">
                                #engineering 👩🏽‍💻📱
                            </a>
                        </li>
                        <li>
                            <a href="https://etherscan.io/address/0x282EF5377C35E90C33a94833d69dBA880430c9cB" target="_blank">
                                #distribution 🛵💨
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ol>
        </div>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Decentralized Autonomous Organization (DAO)</h5>
    <p>
        The 
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://govern-rinkeby.aragon.org/#/daos/elimu.eth" target="_blank">
                    elimu.ai Community DAO
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://govern.aragon.org/#/daos/elimu.eth" target="_blank">
                    elimu.ai Community DAO
                </a>
            </c:otherwise>
        </c:choose>
        is empowered by <a href="https://aragon.org" target="_blank">Aragon</a>. 
        Aragon gives internet communities the power to freely organize and collaborate without borders or intermediaries.
    </p>
    <p>
        For more information, see our blog post: <a href="https://medium.com/elimu-ai/introducing-elimu-our-community-token-7767eebed862" target="_blank">Introducing $ELIMU, Our Community Token 💎</a>
    </p>
    <c:choose>
        <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
            <a href="https://govern-rinkeby.aragon.org/#/daos/elimu.eth" target="_blank">
                <img src="https://user-images.githubusercontent.com/15718174/135745588-c9bf363b-b591-4f4f-a0b6-70ad9ab42263.png" alt="Powered by Aragon" />
            </a>
        </c:when>
        <c:otherwise>
            <a href="https://govern.aragon.org/#/daos/elimu.eth" target="_blank">
                <img src="https://user-images.githubusercontent.com/15718174/135745588-c9bf363b-b591-4f4f-a0b6-70ad9ab42263.png" alt="Powered by Aragon" />
            </a>
        </c:otherwise>
    </c:choose>
</content:aside>
