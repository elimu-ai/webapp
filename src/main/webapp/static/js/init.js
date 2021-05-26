(function($){
  $(function(){
    var web3Ready = false;
    $('.button-collapse').sideNav();
    $('#navButton').sideNav();
    
    $('select').material_select();
    $('#web3').click(function() {
      if (!web3Ready){
        if (window.ethereum) {  
          window.web3 = new Web3(ethereum);  
          window.ethereum.enable()
        } else if (window.web3) {
          window.web3 = new Web3(web3.currentProvider)
        } else {
          console.log('No Metamask (or other Web3 Provider) installed')
          return
        }
        web3Ready = true;
        return
      }
      ethAddress = web3.currentProvider.selectedAddress;
      console.log(ethAddress);
      web3.eth.sign(ethAddress, 'sign-in',function(err, signature) {
            console.log(signature);
      });
    })

    
//    var $toastContent = $('#errorPanel').html();
//    Materialize.toast($toastContent, 5000);

  }); // end of document ready
})(jQuery); // end of jQuery name space
