$(function(){
    $.ajax({
        type:'get',
        url:'/pro.gg/article.do',
        data:'',
        dataType:'',
        success:function(data){
            $("article").html(data)
        }
    });
});