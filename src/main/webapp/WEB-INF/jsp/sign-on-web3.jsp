<content:title>
    Sign On With Web3
</content:title>

<content:section cssId="signOnWeb3Page">
    <h2><content:gettitle /></h2>
    
    <div class="card-panel">
        <p>Connect a Web3 wallet:</p>
        
        <div class="divider" style="margin-bottom: 1em;"></div>
        
        <div class="center">
            <a id="connectWeb3Wallet" class="btn-large waves-effect waves-light">
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
                Connect wallet
            </a>
            <script src="https://cdn.jsdelivr.net/npm/web3@1.3.6/dist/web3.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/web3modal@1.9.8/dist/index.min.js"></script>
            <script src="https://unpkg.com/@walletconnect/web3-provider@1.2.1/dist/umd/index.min.js"></script>
            <script src="https://unpkg.com/fortmatic@2.0.6/dist/fortmatic.js"></script>
            <script src="<spring:url value='/static/js/web3provider.js' />"></script>
            <script>
                $(function() {
                    // When the connect button is pressed, request to view providers
                    $('#connectWeb3Wallet').click(async function() {
                        console.info('#connectWeb3Wallet click');

                        try {
                            // Request permission to connect to Web3 provider
                            const provider = await connect();
                            window.web3 = new Web3(provider);

                            // Request the currently selected address
                            // Get list of accounts of the connected wallet
                            const accounts = await window.web3.eth.getAccounts();
                            // MetaMask does not give you all accounts, only the selected account
                            console.log("Got accounts", accounts);
                            let address = accounts[0];
                            console.log('address ' + address);
                            
                            // Request signature to verify ownership of the address
                            window.web3.eth.personal.sign('elimu.ai\'s mission is to build innovative learning software that empowers out-of-school children to teach themselves basic reading📖, writing✍🏽 and math🔢 **within 6 months**.', address)
                                .then(signature => {
                                    console.info('Signature provided. Signing on...');
                                    
                                    // Add ETH address and signature to the form to be submitted
                                    $('#web3SignOnForm [name="address"]').val(address);
                                    $('#web3SignOnForm [name="signature"]').val(signature);
                                    
                                    // Submit the information to the backend for verification
                                    $('#web3SignOnForm').submit();
                                    
                                    // Display loading overlay while the signature is being verified
                                    var $formLoadingOverlayHtml = $('#formLoadingOverlay');
                                    $('body').prepend($formLoadingOverlayHtml);
                                    $formLoadingOverlayHtml.show();
                                });
                        } catch(error) {
                            console.error('error: ' + error);
                            Materialize.toast('An error occurred', 4000, 'rounded');
                        }
                    });
                });
            </script>
            <form id="web3SignOnForm" action="<spring:url value='/sign-on/web3' />" method="POST">
                <input type="hidden" name="address" />
                <input type="hidden" name="signature" />   
            </form>
        </div>
    </div>
</content:section>

<content:aside>
    <h5 class="center">What is Web3? 🤔</h5>
    <div class="card-panel center deep-purple lighten-5">
        <svg style="width: 64px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
            <g>
                <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
            </g>
        </svg>
        <p>
            Web3 is a new kind of Internet where <i>you</i> are in control of your account.
        </p>
        <p>
            To get started, you will need to install an Ethereum wallet. We recommend <a href="https://metamask.io/download.html" target="_blank">MetaMask</a>.
        </p>
        <iframe style="border-radius: 8px;" width="100%" height="160" src="https://www.youtube.com/embed/YVgfHZMFFFQ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </div>
</content:aside>
