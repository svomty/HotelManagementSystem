$('.button').click(function () {
    var params = window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                var a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );

    var arrival_date = params['arrival_date_filter'];
    var departure_date = params['departure_date_filter'];
    var typeId = $(this).attr('name');
    $("#arrival_date").val(arrival_date);
    $("#departure_date").val(departure_date);
    $("#apartment_id").val(typeId);

    var buttonId = $(this).attr('id');

    $('#modal-container').removeAttr('class').addClass(buttonId);
    $('body').addClass('modal-active');
})

$('.close').click(function () {
    $('#modal-container').addClass('out');
    $('body').removeClass('modal-active');
});

