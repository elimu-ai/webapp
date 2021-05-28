(function($){
  $(function(){
    var web3Ready = false;
    $('.button-collapse').sideNav();
    $('#navButton').sideNav();
    
    $('select').material_select();
    $('#web3').click(async function() {
      if (!web3Ready){
        if (window.ethereum) {  
          window.web3 = new Web3(ethereum);  
          await window.ethereum.enable()
        } else if (window.web3) {
          window.web3 = new Web3(web3.currentProvider)
        } else {
          console.log('No Metamask (or other Web3 Provider) installed')
          return
        }
        web3Ready = true;
      }
      ethAddress = window.web3.currentProvider.selectedAddress;
      window.web3.eth.personal.sign('sign-in', ethAddress)
      .then(signature => {
            $('#web3-signature').val(signature);
            $('#web3-address').val(ethAddress);
            $('#web3-form').submit()
      });
    })

    
//    var $toastContent = $('#errorPanel').html();
//    Materialize.toast($toastContent, 5000);

  }); // end of document ready
})(jQuery); // end of jQuery name space
