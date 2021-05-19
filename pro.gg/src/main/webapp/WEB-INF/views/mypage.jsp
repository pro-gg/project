<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
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

        function summonerNameUpdate(){
            $(function(){
                if(confirm("소환사 명을 변경하시겠습니까? 기존에 있던 데이터는 삭제됩니다.") == true){
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/updateSummonerName.do',
                        data:'',
                        dataType:'',
                        success:function(data){
                            $("body").html(data);
                        }
                    })
                } else {
                    return false;
                }

            })
        }
    </script>
    <style>
        aside{
            height: 920px;
            width: 300px;
            float: left;
            line-height: 30px;
            background-color: aqua;
        }
        article, form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: 50px; margin-bottom: auto;
        }
        #passwd_input{
            border: none;
            border-top: 0px;
            border-bottom: 0px;
            border-left: 0px;
            border-right: 0px;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="">
            <h1>MyPage</h1>
            <p>이름 : ${sessionScope.member.name}</p>
            <p>닉네임 : ${sessionScope.member.nickname}</p>
            <p>이메일 : ${sessionScope.member.email}</p>
            <p>비밀번호 : <input type="password" id="passwd_input"value="${sessionScope.member.passwd}" disabled></p> 
            <c:if test = "${sessionScope.member.summoner_name == null}">
                <p>소환사 명 등록하기 : <input type="text" name="summonerName" id="summonerName" placeholder="소환사 명"></p>
                <input type="button" name="registerSummoner" id="registerSummoner" value="등록하기" onclick="summonerRegister()">
            </c:if>
            <c:if test = "${sessionScope.member.summoner_name != null}">
                소환사 명 : ${sessionScope.member.summoner_name} <input type="button" value="변경하기" name="updateSummonerName" id="updateSummonerName"
                    onclick="summonerNameUpdate()"> <br>
                <input type="button" value="최근 전적" name="matchHistory" id="matchHistory">
            </c:if>

        </form>
    </article>
</body>
</html>