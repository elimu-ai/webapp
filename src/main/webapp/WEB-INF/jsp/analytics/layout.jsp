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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins" />
        <link rel="stylesheet" href="<spring:url value='/static/css/styles.css' />" />
        <link rel="stylesheet" href="<spring:url value='/static/css/analytics/styles.css' />" />
        
        <%-- JavaScripts --%>
        <script src="<spring:url value='/static/js/jquery-3.6.0.min.js' />"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="<spring:url value='/static/js/init.js' />"></script>
        <script src="<spring:url value='/static/js/difflib-0.2.4.min.js' />"></script>
        <script src="https://cdn.jsdelivr.net/npm/web3@1.3.6/dist/web3.min.js"></script>
        <script type="text/javascript" src="https://unpkg.com/web3modal@1.9.0/dist/index.js"></script>
        <script type="text/javascript" src="https://unpkg.com/@walletconnect/web3-provider@1.2.1/dist/umd/index.min.js"></script>
        <script type="text/javascript" src="https://unpkg.com/fortmatic@2.0.6/dist/fortmatic.js"></script>
        <script src="<spring:url value='/static/js/web3provider.js' />"></script>
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
            <div class="nav-wrapper container">
                <a id="logo-container" href="<spring:url value='/' />" class="brand-logo">
                    <img src="<spring:url value='/static/img/logo-text-256x77.png' />" alt="elimu.ai" />
                </a>
                <sec:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_CONTRIBUTOR')">
                    <ul class="right hide-on-med-and-down">
                        <li><a href="<spring:url value='/sign-on' />"><fmt:message key="sign.on" /></a></li>
                    </ul>
                    <ul id="nav-mobile" class="side-nav">
                        <li><a href="<spring:url value='/sign-on' />"><fmt:message key="sign.on" /></a></li>
                    </ul>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_CONTRIBUTOR')">
                    <ul class="right">
                        <a href="<spring:url value='/content' />">
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
                                        <c:out value="${contributor.firstName}" />&nbsp;<c:out value="${contributor.lastName}" />
                                    </c:when>
                                    <c:when test="${not empty contributor.providerIdWeb3}">
                                        ${fn:substring(contributor.providerIdWeb3, 0, 6)}...${fn:substring(contributor.providerIdWeb3, 38, 42)}
                                    </c:when>
                                </c:choose>
                            </div>
                        </a>
                    </ul>
                </sec:authorize>
                
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

        <div id="${cssId}" class="container <c:if test="${cssClass != null}">${cssClass}</c:if>">
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
        </div>
    </body>
</html>
