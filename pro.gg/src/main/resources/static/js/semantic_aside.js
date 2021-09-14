$(function () {
    $.ajax({
        type: 'get',
        url: '/pro.gg/aside.do',
        data: '',
        dataType: '',
        success: function (data) {
            $("aside").html(data);
        }
    })
})