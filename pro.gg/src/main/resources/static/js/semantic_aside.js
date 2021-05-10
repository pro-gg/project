$(function () {
    $.ajax({
        type: 'get',
        url: '/aside.do',
        data: '',
        dataType: '',
        success: function (data) {
            $("aside").html(data);
        }
    })
})