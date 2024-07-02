<content:title>
    <fmt:message key="sign.on" />
</content:title>

<content:section cssId="signOnPage">
    <div class="center">
        <img src="<spring:url value='/static/img/logo-text-256x78.png' />" alt="elimu.ai" />
    </div>
    
    <h2 class="center"><fmt:message key="join.the.community" /></h2>
    
    <div class="section">
        <div class="row section">
            <p class="center"><fmt:message key="sign.on.via.an.existing.account" />:</p>
            
            <div class="col s12 l8 offset-l2" style="margin-bottom: 1em;">
                <a href="<spring:url value='/sign-on/google' />" class="col s12 btn-large waves-effect waves-light white grey-text">
                    <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 533.5 544.3" xmlns="http://www.w3.org/2000/svg"><path d="M533.5 278.4c0-18.5-1.5-37.1-4.7-55.3H272.1v104.8h147c-6.1 33.8-25.7 63.7-54.4 82.7v68h87.7c51.5-47.4 81.1-117.4 81.1-200.2z" fill="#4285f4"/><path d="M272.1 544.3c73.4 0 135.3-24.1 180.4-65.7l-87.7-68c-24.4 16.6-55.9 26-92.6 26-71 0-131.2-47.9-152.8-112.3H28.9v70.1c46.2 91.9 140.3 149.9 243.2 149.9z" fill="#34a853"/><path d="M119.3 324.3c-11.4-33.8-11.4-70.4 0-104.2V150H28.9c-38.6 76.9-38.6 167.5 0 244.4l90.4-70.1z" fill="#fbbc04"/><path d="M272.1 107.7c38.8-.6 76.3 14 104.4 40.8l77.7-77.7C405 24.6 339.7-.8 272.1 0 169.2 0 75.1 58 28.9 150l90.4 70.1c21.5-64.5 81.8-112.4 152.8-112.4z" fill="#ea4335"/></svg>
                    &nbsp;Sign on with Google
                </a>
            </div>
            <div class="col s12 l8 offset-l2" style="margin-bottom: 1em;">
                <a href="<spring:url value='/sign-on/web3' />" class="col s12 btn-large waves-effect waves-light white grey-text">
                    <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
                        <g>
                            <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                            <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                            <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                            <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                            <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                            <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
                        </g>
                    </svg>
                    &nbsp;Sign on with Web3
                </a>
            </div>
            <div class="col s12 l8 offset-l2" style="margin-bottom: 1em;">
                <a href="<spring:url value='/sign-on/discord' />" class="col s12 btn-large waves-effect waves-light white grey-text">
                    <svg  style="width: 24px; height: 24px; top: 8px; position: relative; right: 5px;" viewBox="0 0 71 55" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <g clip-path="url(#clip0)">
                        <path d="M60.1045 4.8978C55.5792 2.8214 50.7265 1.2916 45.6527 0.41542C45.5603 0.39851 45.468 0.440769 45.4204 0.525289C44.7963 1.6353 44.105 3.0834 43.6209 4.2216C38.1637 3.4046 32.7345 3.4046 27.3892 4.2216C26.905 3.0581 26.1886 1.6353 25.5617 0.525289C25.5141 0.443589 25.4218 0.40133 25.3294 0.41542C20.2584 1.2888 15.4057 2.8186 10.8776 4.8978C10.8384 4.9147 10.8048 4.9429 10.7825 4.9795C1.57795 18.7309 -0.943561 32.1443 0.293408 45.3914C0.299005 45.4562 0.335386 45.5182 0.385761 45.5576C6.45866 50.0174 12.3413 52.7249 18.1147 54.5195C18.2071 54.5477 18.305 54.5139 18.3638 54.4378C19.7295 52.5728 20.9469 50.6063 21.9907 48.5383C22.0523 48.4172 21.9935 48.2735 21.8676 48.2256C19.9366 47.4931 18.0979 46.6 16.3292 45.5858C16.1893 45.5041 16.1781 45.304 16.3068 45.2082C16.679 44.9293 17.0513 44.6391 17.4067 44.3461C17.471 44.2926 17.5606 44.2813 17.6362 44.3151C29.2558 49.6202 41.8354 49.6202 53.3179 44.3151C53.3935 44.2785 53.4831 44.2898 53.5502 44.3433C53.9057 44.6363 54.2779 44.9293 54.6529 45.2082C54.7816 45.304 54.7732 45.5041 54.6333 45.5858C52.8646 46.6197 51.0259 47.4931 49.0921 48.2228C48.9662 48.2707 48.9102 48.4172 48.9718 48.5383C50.038 50.6034 51.2554 52.5699 52.5959 54.435C52.6519 54.5139 52.7526 54.5477 52.845 54.5195C58.6464 52.7249 64.529 50.0174 70.6019 45.5576C70.6551 45.5182 70.6887 45.459 70.6943 45.3942C72.1747 30.0791 68.2147 16.7757 60.1968 4.9823C60.1772 4.9429 60.1437 4.9147 60.1045 4.8978ZM23.7259 37.3253C20.2276 37.3253 17.3451 34.1136 17.3451 30.1693C17.3451 26.225 20.1717 23.0133 23.7259 23.0133C27.308 23.0133 30.1626 26.2532 30.1066 30.1693C30.1066 34.1136 27.28 37.3253 23.7259 37.3253ZM47.3178 37.3253C43.8196 37.3253 40.9371 34.1136 40.9371 30.1693C40.9371 26.225 43.7636 23.0133 47.3178 23.0133C50.9 23.0133 53.7545 26.2532 53.6986 30.1693C53.6986 34.1136 50.9 37.3253 47.3178 37.3253Z" fill="#5865F2"/>
                        </g>
                        <defs>
                        <clipPath id="clip0">
                        <rect width="71" height="55" fill="white"/>
                        </clipPath>
                        </defs>
                        </svg>
                    &nbsp;Sign on with Discord
                </a>
            </div>
        </div>
        
        <div class="divider"></div>
                
        <div class="row section">
            <p class="center"><fmt:message key="are.you.a.developer" /></p>
            
            <div class="col s12 l8 offset-l2">
                <a href="<spring:url value='/sign-on/github' />" class="col s12 btn-large waves-effect waves-light grey darken-1">
                    <svg style="width: 32px; height: 32px; top: 6px; position: relative; right: 5px;" viewBox="0 0 50 50">
                        <path fill="#ffffff" d="M32,16c-8.8,0-16,7.2-16,16c0,7.1,4.6,13.1,10.9,15.2 c0.8,0.1,1.1-0.3,1.1-0.8c0-0.4,0-1.4,0-2.7c-4.5,1-5.4-2.1-5.4-2.1c-0.7-1.8-1.8-2.3-1.8-2.3c-1.5-1,0.1-1,0.1-1 c1.6,0.1,2.5,1.6,2.5,1.6c1.4,2.4,3.7,1.7,4.7,1.3c0.1-1,0.6-1.7,1-2.1c-3.6-0.4-7.3-1.8-7.3-7.9c0-1.7,0.6-3.2,1.6-4.3 c-0.2-0.4-0.7-2,0.2-4.2c0,0,1.3-0.4,4.4,1.6c1.3-0.4,2.6-0.5,4-0.5c1.4,0,2.7,0.2,4,0.5c3.1-2.1,4.4-1.6,4.4-1.6 c0.9,2.2,0.3,3.8,0.2,4.2c1,1.1,1.6,2.5,1.6,4.3c0,6.1-3.7,7.5-7.3,7.9c0.6,0.5,1.1,1.5,1.1,3c0,2.1,0,3.9,0,4.4 c0,0.4,0.3,0.9,1.1,0.8C43.4,45.1,48,39.1,48,32C48,23.2,40.8,16,32,16z" />
                    </svg>
                    &nbsp;Sign on with GitHub
                </a>
            </div>
        </div>
    </div>    
</content:section>

<content:aside>
    <h5 class="center">Token Holders 💎</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            Active contributors get rewarded with 
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:when>
                <c:otherwise>
                    <a href="https://etherscan.io/token/0xe29797910d413281d2821d5d9a989262c8121cc2" target="_blank"><code>$ELIMU</code></a>
                </c:otherwise>
            </c:choose> 
            tokens.
        </p>
        <p>
            All token holders can participate in the community's <a href="<spring:url value='/contributions/aragon-dao' />">decision making</a>.
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
    </div>
</content:aside>
