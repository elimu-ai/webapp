<c:choose>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/application/')}">
        <%@ include file="/WEB-INF/jsp/application/layout.jsp" %>
    </c:when>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
        <%@ include file="/WEB-INF/jsp/content/layout.jsp" %>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html>
        <html lang="en" data-content-language="${fn:toLowerCase(applicationScope.configProperties['content.language'])}">
            <head>
                <title><content:gettitle /> | ${fn:toLowerCase(applicationScope.configProperties['content.language'])}.elimu.ai</title>

                <meta charset="UTF-8" />

                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                
                <meta name="description" content="elimu.ai's mission is to build innovative learning software that empowers out-of-school children to teach themselves basic reading📖, writing✍🏽 and math🔢 within 6 months." />
                <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />

                <meta property="og:image" content="https://${pageContext.request.serverName}/static/img/logo-256x256.png" />
                <meta property="twitter:image" content="https://${pageContext.request.serverName}/static/img/logo-256x256.png" />

                <%-- CSS --%>
                <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css" />
                <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
                
                <%-- JavaScripts --%>
                <script src="<spring:url value='/static/js/jquery-3.6.0.min.js' />"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
                <script src="<spring:url value='/static/js/init.js' />"></script>
                <script src="<spring:url value='/static/js/difflib-0.2.4.min.js' />"></script>
                <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
            </head>

            <body>
                <a href="https://github.com/elimu-ai" class="github-corner" aria-label="View source on GitHub"><svg width="80" height="80" viewBox="0 0 250 250" style="fill:#151513; color:#fff; position: absolute; top: 0; border: 0; right: 0;" aria-hidden="true"><path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path><path d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2" fill="currentColor" style="transform-origin: 130px 106px;" class="octo-arm"></path><path d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z" fill="currentColor" class="octo-body"></path></svg></a><style>.github-corner:hover .octo-arm{animation:octocat-wave 560ms ease-in-out}@keyframes octocat-wave{0%,100%{transform:rotate(0)}20%,60%{transform:rotate(-25deg)}40%,80%{transform:rotate(10deg)}}@media (max-width:500px){.github-corner:hover .octo-arm{animation:none}.github-corner .octo-arm{animation:octocat-wave 560ms ease-in-out}}</style>
                
                <div id="formLoadingOverlay" style="display: none;">
                    <div class="preloader-wrapper big active">
                        <div class="spinner-layer spinner-blue-only">
                            <div class="circle-clipper left">
                                <div class="circle"></div>
                            </div><div class="gap-patch">
                                <div class="circle"></div>
                            </div><div class="circle-clipper right">
                                <div class="circle"></div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <nav class="deep-purple lighten-1">
                    <div class="nav-wrapper container">
                        <a id="logo-container" href="<spring:url value='/' />" class="brand-logo">
                            <img src="<spring:url value='/static/img/logo-text-256x77.png' />" alt="elimu.ai" />
                        </a>
                        <c:if test="${empty contributor}">
                            <ul class="right hide-on-med-and-down">
                                <li><a href="<spring:url value='/sign-on' />">Sign on</a></li>
                            </ul>
                            <ul id="nav-mobile" class="side-nav">
                                <c:choose>
                                    <c:when test="${empty contributor.providerIdWeb3}">
                                        <li>
                                            <a class="btn tokenButtonSideNav" href="<spring:url value='/sign-on/web3' />">
                                                <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
                                                    <g>
                                                        <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                                                        <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                                                        <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                                                        <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                                                        <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                                                        <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
                                                    </g>
                                                </svg>&nbsp;Connect wallet
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <c:set var="etherscanUrl" value="https://etherscan.io" />
                                            <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                                                <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                                            </c:if>
                                            <a class="btn tokenButtonSideNav" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor.providerIdWeb3}" target="_blank">
                                                <code><span id="tokenBalanceSideNav">0</span> $ELIMU</code>
                                            </a>
                                            <script>
                                                $(function() {
                                                    var contributorAddress = '${contributor.providerIdWeb3}';
                                                    getBalance(contributorAddress).then(function(result) {
                                                        console.info('result: ' + result);

                                                        var tokenBalance = result / 1000000000000000000;
                                                        console.info('tokenBalance: ' + tokenBalance);

                                                        var tokenBalanceFormatted = Intl.NumberFormat().format(Math.round(tokenBalance));
                                                        console.info('tokenBalanceFormatted ' + tokenBalanceFormatted);

                                                        $('#tokenBalanceSideNav').html(tokenBalanceFormatted);
                                                    });
                                                });
                                            </script>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <li><a class="btn signOnBtn" href="<spring:url value='/sign-on' />">Sign on</a></li>
                            </ul>
                        </c:if>
                        <c:if test="${not empty contributor}">
                            <ul id="nav-mobile" class="side-nav">
                                <li>
                                    <a class="btn tokenButtonSideNav" href="<spring:url value='/sign-on/web3' />">
                                        <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
                                            <g>
                                                <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                                                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                                                <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                                                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                                                <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                                                <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
                                            </g>
                                        </svg>&nbsp;Connect wallet
                                    </a>
                                </li>
                            </ul>
                            <ul class="right">
                                <c:set var="chipContributor" value="${contributor}" />
                                <%@ include file="/WEB-INF/jsp/contributor/chip-contributor.jsp" %>
                            </ul>
                        </c:if>
                        
                        <ul class="right">
                            <script>
                                /**
                                * Fetch token balance
                                */
                                async function getBalance(contributorAddress) {
                                    console.info('getBalance');

                                    // Connect to the web3 provider.
                                    const provider = await connect()
                                    window.web3 = new Web3(provider);
                                    console.info('window.web3: ' + window.web3);

                                    var contractAbi = [{"inputs":[{"internalType":"address","name":"account","type":"address"}],"name":"balanceOf","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"}];
                                    var contractAddress = '0xe29797910d413281d2821d5d9a989262c8121cc2';
                                    var contract = new window.web3.eth.Contract(contractAbi, contractAddress);
                                    var balance = await contract.methods.balanceOf(contributorAddress).call();

                                    return balance;
                                }
                            </script>
                            <c:choose>
                                <c:when test="${empty contributor.providerIdWeb3}">
                                    <a class="btn tokenButton" href="<spring:url value='/sign-on/web3' />">
                                        <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
                                            <g>
                                                <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                                                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                                                <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                                                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                                                <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                                                <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
                                            </g>
                                        </svg>&nbsp;Connect wallet
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="etherscanUrl" value="https://etherscan.io" />
                                    <c:if test="${applicationScope.configProperties['env'] != 'PROD'}">
                                        <c:set var="etherscanUrl" value="https://rinkeby.etherscan.io" />
                                    </c:if>
                                    <a class="btn tokenButton" href="${etherscanUrl}/token/0xe29797910d413281d2821d5d9a989262c8121cc2?a=${contributor.providerIdWeb3}" target="_blank">
                                        <code><span id="tokenBalance">0</span> $ELIMU</code>
                                    </a>
                                    <script>
                                        $(function() {
                                            var contributorAddress = '${contributor.providerIdWeb3}';
                                            getBalance(contributorAddress).then(function(result) {
                                                console.info('result: ' + result);

                                                var tokenBalance = result / 1000000000000000000;
                                                console.info('tokenBalance: ' + tokenBalance);

                                                var tokenBalanceFormatted = Intl.NumberFormat().format(Math.round(tokenBalance));
                                                console.info('tokenBalanceFormatted ' + tokenBalanceFormatted);

                                                $('#tokenBalance').html(tokenBalanceFormatted);
                                            });
                                        });
                                    </script>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                        
                        <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
                    </div>
                </nav>

                <c:if test="${hasBanner}">
                    <div class="section no-pad-bot" id="index-banner">
                        <div class="container">
                            <content:getbanner />
                        </div>
                    </div>
                </c:if>

                <main id="${cssId}" class="container <c:if test="${cssClass != null}">${cssClass}</c:if>">
                    <div class="section row">
                        <c:choose>
                            <c:when test="${!hasAside}">
                                <div class="col s12">
                                    <content:getsection />
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col s12 m8">
                                    <content:getsection />
                                </div>
                                <div class="col s12 m4">
                                    <content:getaside />
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </main>

                <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/')}">
                    <footer class="page-footer deep-purple lighten-1">
                      <div class="container">
                        <div class="row">
                          <div class="col l6 s12">
                            <h5 class="white-text">About elimu.ai</h5>
                            <p class="grey-text text-lighten-2">Our mission is to build innovative learning software that empowers out-of-school children to teach themselves basic reading📖, writing✍🏽 and math🔢 <b>within 6 months</b>.</p>
                            <p class="grey-text text-lighten-2">Read more about the project in the <a class="white-text" href="https://github.com/elimu-ai/wiki" target="_blank">Wiki</a>.</p>
                          </div>
                          <div class="col l3 offset-l1 s12 ">
                            <h5 class="white-text">Chat 💬</h5>
                            <p class="grey-text text-lighten-2">
                                Send us an <a class="white-text" href="mailto:info@elimu.ai" style="text-transform: lowercase;">E-mail</a> 
                                or chat with us directly in Discord:
                            </p>
                            <a class="btn waves-effect waves-light deep-purple lighten-2" target="_blank" href="https://discord.gg/9rz4XYJJDE">
                                Chat<i class="material-icons right">forum</i>
                            </a>
                          </div>
                          <div class="col l2 s12 right-align">
                            <h5 class="white-text">Socials</h5>
                            <ul>
                                <li><a class="white-text" href="https://twitter.com/elimu_ai" target="_blank">Twitter</a></li>
                                <li><a class="white-text" href="https://www.facebook.com/elimuai" target="_blank">Facebook</a></li>
                                <li><a class="white-text" href="https://www.instagram.com/elimu.ai" target="_blank">Instagram</a></li>
                                <li><a class="white-text" href="https://www.linkedin.com/company/elimuai" target="_blank">LinkedIn</a></li>
                                <li><a class="white-text" href="https://medium.com/elimu-ai" target="_blank">Blog</a></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    <div class="footer-copyright">
                      <div class="container">
                          <div class="row">
                            <div class="col s12 m6">
                                <a class="white-text" href="https://opensource.org/licenses/MIT">MIT License</a> &nbsp; | &nbsp; See our&nbsp;<a class="white-text" href="https://github.com/elimu-ai">code repository</a><br />
                                <a class="white-text" href="https://creativecommons.org/licenses/by/4.0/">CC BY 4.0</a> &nbsp; | &nbsp; See our&nbsp;<a class="white-text" href="<spring:url value='/content' />">content repository</a><br />
                            </div>
                            <div class="col s12 m6 right-align">
                                Languages: 
                                <c:choose>
                                    <c:when test="${applicationScope.configProperties['env'] == 'DEV'}">
                                        <c:forEach var="supportedLanguage" items="${applicationScope.configProperties['supported.languages']}" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                • 
                                            </c:if>
                                            <a class="white-text" href="<spring:url value='/?lang=${supportedLanguage.isoCode}' />" title="${supportedLanguage.nativeName} (${supportedLanguage.englishName})">${supportedLanguage.isoCode}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="supportedLanguage" items="${applicationScope.configProperties['supported.languages']}" varStatus="status">
                                            <c:if test="${status.index > 0}">
                                                • 
                                            </c:if>
                                            <a class="white-text" href="https://${supportedLanguage.isoCode}.elimu.ai" title="${supportedLanguage.nativeName} (${supportedLanguage.englishName})">${supportedLanguage.isoCode}</a>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose><br />
                                Webapp version: <a class="white-text" href="https://github.com/elimu-ai/webapp/releases"><code>${applicationScope.configProperties['pom.version']}</code></a>
                            </div>
                          </div>
                      </div>
                    </div>
                  </footer>
                </c:if>
            </body>
        </html>
    </c:otherwise>
</c:choose>
