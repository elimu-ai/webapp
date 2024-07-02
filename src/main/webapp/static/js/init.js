(function($){
  $(function(){

    $('.button-collapse').sideNav();
    $('#navButton').sideNav();
    
    $('select').material_select();
    
//    var $toastContent = $('#errorPanel').html();
//    Materialize.toast($toastContent, 5000);
    
    $('form').submit(function() {
        console.info('form submit');
        var $formLoadingOverlayHtml = $('#formLoadingOverlay');
        $('form').prepend($formLoadingOverlayHtml);
        $formLoadingOverlayHtml.show();
    });
  }); // end of document ready
})(jQuery); // end of jQuery name space
