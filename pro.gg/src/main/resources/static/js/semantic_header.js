$(function(){
    $.ajax({
        type:'get',
        url:'/header.do',
        data:'',
        dataType:'',
        success:function(data){
            $("header").html(data)
        }
    });
});