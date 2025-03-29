<!DOCTYPE html>
<html lang="en" data-content-language="${fn:toLowerCase(applicationScope.configProperties['content.language'])}">
    <head>
        <title><content:gettitle /> | ${fn:toLowerCase(applicationScope.configProperties['content.language'])}.elimu.ai</title>

        <meta charset="UTF-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link rel="shortcut icon" href="<spring:url value='/static/img/favicon.ico' />" />
        
        <%-- CSS --%>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css" />
        <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
        <link rel="stylesheet" href="<spring:url value='/static/css/content/styles.css' />" />
        
        <%-- JavaScripts --%>
        <script src="<spring:url value='/static/js/jquery-3.6.0.min.js' />"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="<spring:url value='/static/js/init.js' />"></script>
        <script src="<spring:url value='/static/js/difflib-0.2.4.min.js' />"></script>
        <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
    </head>

    <body>
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
            <div class="row nav-wrapper">
                <div class="col s1">
                    <ul id="nav-mobile" class="side-nav">
                        <li>
                            <a href="<spring:url value='/content' />">
                                <img style="max-width: 100%; vertical-align: middle; max-height: 60%;" src="<spring:url value='/static/img/logo-text-256x78.png' />" alt="elimu.ai" />
                            </a>
                        </li>
                        
                        <li class="divider"></li>

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
                        
                        <li class="grey-text"><b>Community</b></li>
                        <li><a href="<spring:url value='/contributor/list' />"><i class="material-icons left">group</i>Contributors</a></li>
                        <li><a href="https://discord.gg/9rz4XYJJDE" target="_blank"><i class="material-icons left">forum</i>Discord</a></li>
                        
                        <li class="divider"></li>
                        
                        <li class="grey-text"><b>Text</b></li>
                        <li><a href="<spring:url value='/content/letter/list' />"><i class="material-icons left">text_fields</i>Letters</a></li>
                        <li><a href="<spring:url value='/content/sound/list' />"><i class="material-icons left">music_note</i>Sounds</a></li>
                        <li><a href="<spring:url value='/content/letter-sound/list' />"><i class="material-icons left">emoji_symbols</i>Letter-sounds</a></li>
                        <li><a href="<spring:url value='/content/syllable/list' />"><i class="material-icons left">queue_music</i>Syllables</a></li>
                        <li><a href="<spring:url value='/content/number/list' />"><i class="material-icons left">looks_one</i>Numbers</a></li>
                        <li><a href="<spring:url value='/content/word/list' />"><i class="material-icons left">sms</i>Words</a></li>
                        <li><a href="<spring:url value='/content/emoji/list' />"><i class="material-icons left">emoji_emotions</i>Emojis</a></li>
                        <li class="grey-text"><b>Multimedia</b></li>
                        <li><a href="<spring:url value='/content/multimedia/image/list' />"><i class="material-icons left">image</i>Images</a></li>
                        <li><a href="<spring:url value='/content/storybook/list' />"><i class="material-icons left">book</i>Storybooks</a></li>
                        <li><a href="<spring:url value='/content/multimedia/video/list' />"><i class="material-icons left">movie</i>Videos</a></li>
                    </ul>
                    <a id="navButton" href="<spring:url value='/content' />" data-activates="nav-mobile" class="waves-effect waves-light"><i class="material-icons">dehaze</i></a>
                </div>
                <div class="col s5">
                    <a href="<spring:url value='/content' />" class="breadcrumb">Educational content</a>
                    <c:if test="${!fn:contains(pageContext.request.requestURI, '/jsp/content/main.jsp')}">
                        <c:choose>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/contributor/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/contributor/list' />">Contributors</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/sound/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/sound/list' />">Sounds</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/number/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/number/list' />">Numbers</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/letter/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/letter/list' />">Letters</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/letter-sound/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/letter-sound/list' />">Letter-sound correspondences</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/word/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/word/list' />">Words</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/emoji/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/emoji/list' />">Emojis</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/image/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/multimedia/image/list' />">Images</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/storybook/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/storybook/list' />">Storybooks</a>
                            </c:when>
                            <c:when test="${fn:contains(pageContext.request.requestURI, '/content/multimedia/video/')
                                    && !fn:endsWith(pageContext.request.requestURI, '/list.jsp')}">
                                <a class="breadcrumb" href="<spring:url value='/content/multimedia/video/list' />">Videos</a>
                            </c:when>
                        </c:choose>
                        <a class="breadcrumb"><content:gettitle /></a>
                    </c:if>
                </div>
                <div class="col s6">
                    <ul class="right">
                        <a class="dropdown-button" data-activates="contributorDropdown" data-beloworigin="true" >
                            <div class="chip">
                                <c:choose>
                                    <c:when test="${not empty contributor.imageUrl}">
                                        <img src="${contributor.imageUrl}" />
                                    </c:when>
                                    <c:when test="${not empty contributor.providerIdWeb3}">
                                        <img src="https://effigy.im/a/<c:out value="${contributor.providerIdWeb3}" />.png" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<spring:url value='/static/img/placeholder.png' />" />
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty contributor.firstName}">
                                        <span class="contributor-name-wrapper" aria-label="${contributor.firstName}">
                                            <span><c:out value="${contributor.firstName}" /></span>
                                            <span><c:out value="${contributor.lastName}" /></span>
                                        </span>
                                    </c:when>
                                    <c:when test="${not empty contributor.providerIdWeb3}">
                                        ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                    </c:when>
                                </c:choose>
                            </div>
                        </a>
                        <ul id='contributorDropdown' class='dropdown-content'>
                            <li><a href="<spring:url value='/contributor/${contributor.id}' />"><i class="material-icons left">art_track</i>My contributions</a></li>
                            <li class="divider"></li>
                            <li><a href="<spring:url value='/content/contributor/edit-name' />"><i class="material-icons left">mode_edit</i>Edit name</a></li>
                            <%--<li class="divider"></li>
                            <li><a href="<spring:url value='/content/contributor/edit-email' />"><i class="material-icons left">mail</i>Edit e-mail</a></li>--%>
                            <c:if test="${fn:contains(contributor.roles, 'ADMIN')}">
                                <li class="divider"></li>
                                <li><a href="<spring:url value='/admin' />"><i class="material-icons left">build</i>Administration</a></li>
                            </c:if>
                            <c:if test="${fn:contains(contributor.roles, 'ANALYST')}">
                                <li class="divider"></li>
                                <li><a href="<spring:url value='/analytics' />"><i class="material-icons left">timeline</i>Analytics</a></li>
                            </c:if>
                            <li class="divider"></li>
                            <li><a id="logout" href="<spring:url value='/sign-out' />"><i class="material-icons left">power_settings_new</i>Sign out</a></li>
                        </ul>
                    </ul>
                    
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
                </div>
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
    </body>
</html>
