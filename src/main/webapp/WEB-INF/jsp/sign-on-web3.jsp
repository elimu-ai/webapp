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
            <script>
                $(function() {
                    // When the connect button is pressed, request to view providers
                    $('#connectWeb3Wallet').click(async function() {
                        console.info('#connectWeb3Wallet click');

   
                        try {
                            // Request permission to connect to Web3 provider
                            const provider = await connect()
                            window.web3 = new Web3(provider);

                            // Request the currently selected address
                            // Get list of accounts of the connected wallet
                            const accounts = await window.web3.eth.getAccounts();
                            // MetaMask does not give you all accounts, only the selected account
                            console.log("Got accounts", accounts);
                            let address = accounts[0];
                            console.log('address ' + address);
                            
                            // Request signature to verify ownership of the address
                            window.web3.eth.personal.sign('I verify ownership of this account 👍', address)
                                .then(signature => {
                                    console.info('Signature provided. Logging in...');
                                    
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
