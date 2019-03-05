(function () {
    $('#order-button').on('click', function(e) {
        e.preventDefault();
        $('.custom-modal--order').addClass('active');
        $('.modal-mask').slideDown();
    });
    $('.close-modal').on('click', function() {
        $('.custom-modal--order').removeClass('active');
        $('.modal-mask').slideUp();
    })
    $('.modal-mask').on('click', function() {
        $('.custom-modal--order').removeClass('active');
        $('.modal-mask').slideUp();
    })
    ScrollReveal().reveal('.thumb', {
             origin: 'left',
             delay: 400,
             distance: '200px',
         });

         ScrollReveal().reveal('.vance1', {
            origin: 'left',
            delay: 400,
            distance: '200px',
        });
})(jQuery)