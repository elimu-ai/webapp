"use strict";

// Unpkg imports
const Web3Modal = window.Web3Modal.default;
const WalletConnectProvider = window.WalletConnectProvider.default;
const Fortmatic = window.Fortmatic;
const evmChains = window.evmChains;

// Web3modal instance
let web3Modal;

// Chosen wallet provider given by the dialog window
let provider;

/**
 * Setup the orchestra
 */
function init() {

  console.log("WalletConnectProvider is", WalletConnectProvider);
  console.log("Fortmatic is", Fortmatic);
  console.log("window.web3 is", window.web3, "window.ethereum is", window.ethereum);

  // Check that the web page is run in a secure context,
  // as otherwise MetaMask won't be available
  if (location.protocol !== 'https:') {
    // https://ethereum.stackexchange.com/a/62217/620
    // TODO:
  }

  // Tell Web3modal what providers we have available.
  // Built-in web browser provider (only one can exist as a time)
  // like MetaMask, Brave or Opera is added automatically by Web3modal
  const providerOptions = {
    walletconnect: {
      package: WalletConnectProvider,
      options: {
        infuraId: "37c4b40db8fe47fb90a1e2ac4ff152eb",
      }
    }
  };

  web3Modal = new Web3Modal({
    cacheProvider: true, // optional
    providerOptions, // required
    disableInjectedProvider: false, // optional. For MetaMask / Brave / Opera.
  });

  console.log("Web3Modal instance is", web3Modal);
}

/**
 * Connect to the wallet provider.
 */
async function connect() {
  if (web3Modal === undefined){
    init();
  }
  console.log("Opening a dialog", web3Modal);
  try {
    provider = await web3Modal.connect();
  } catch(e) {
    console.log("Could not get a wallet connection", e);
    return;
  }
  // Subscribe to accounts change
  provider.on("accountsChanged",async (accounts) => {
     // TODO:
  });

  // Subscribe to chainId change
  provider.on("chainChanged",async (chainId) => {
     // TODO:
  });

  // Subscribe to networkId change
  provider.on("networkChanged",async (networkId) => {
     // TODO:
  });

  return provider;
}

async function disconnect() {
  console.log("Killing the wallet connection", provider);

  // TODO: Which providers have close method?
  if (provider.close) {
    await provider.close();

    // If the cached provider is not cleared,
    // WalletConnect will default to the existing session
    // and does not allow to re-scan the QR code with a new wallet.
    // Depending on your use case you may want or want not his behavir.
    provider = null;
  }
  await web3Modal.clearCachedProvider();
}

/**
 * Main entry point.
 */
$( document ).ready(function() {

  $('#logout').on('click', async function(e) {
    e.preventDefault();
    await disconnect();
    window.location = $(this).attr('href');
  });
});