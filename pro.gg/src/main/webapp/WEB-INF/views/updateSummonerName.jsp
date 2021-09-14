<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function summonerRegister(){
            $(function(){
                var summonerName = {
                    'summonerName' : document.getElementById('summonerName').value
                };
                    
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/SearchSummonerData.do?summonerName='+encodeURI(JSON.stringify(summonerName)),
                    data:'', 
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }
    </script> 
    <style>
        form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: auto; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="">
            <h1>소환사 명 변경</h1>
            <p>소환사 명 변경하기 : <input type="text" name="summonerName" id="summonerName" placeholder="소환사 명"></p>
            <input type="button" name="registerSummoner" id="registerSummoner" value="변경하기" onclick="summonerRegister()">
          
        </form>
        
    </article>
</body>
</html>