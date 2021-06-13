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
    <script src="/js/bootstrap.min.js" charset="utf-8"></script>
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
                if(confirm("소환사 명을 변경하시겠습니까? 기존에 있던 데이터는 삭제됩니다. ") == true){
                    if(confirm("소속된 팀이 있다면 자동으로 추방되고, 특정 팀에 지원중인 경우 또한 지원이 취소됩니다. 그래도 변경하시겠습니까?") === true){
                        $.ajax({
                            type:'get',
                            url:'${pageContext.request.contextPath}/updateSummonerName.do',
                            data:'',
                            dataType:'',
                            success:function(data){
                                $("body").html(data);
                            }
                        })
                    }
                    else{
                        return false;
                    }
                } else {
                    return false;
                }
            })
        }
        function callMatchHistory(){
            $(function(){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/matchHistory.do?summoner_name=${sessionScope.member.summoner_name}',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("#printMatchHistory").html(data);
                    }
                })
            })
        }

        function updateSummonerData(){
            $(function(){
                var updateSummoner = "updateSummonerData";
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/updateSummonerData.do?target='+updateSummoner,
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }
        function callSummonerData(){
            $(function(){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/printSummonerData_mypage.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("#printSummonerData_mypage").html(data);
                    }
                })
            })
        }

        function updateMember(){
            $(function(){
                if(confirm("회원 정보를 변경 하시겠습니까?") == true){
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/move/updateMember.do',
                        data:'',
                        dataType:'',
                        success:function(data){
                            $("body").html(data);
                        }
                    })
                }
            })
        }

        function updatePasswd(){
            if(confirm("비밀번호를 변경 하시겠습니까?") == true){
                window.open("${pageContext.request.contextPath}/move/currentPasswd_popup.do?target=change", "findId", "width=550, height=450, left=100, top=50");
            }
        }

        function secessionMember(){
            if(confirm("회원 탈퇴 하시겠습니까? 탈퇴시 모든 데이터는 삭제됩니다.") == true){
                window.open("${pageContext.request.contextPath}/move/currentPasswd_popup.do?target=secession", "findId", "width=550, height=450, left=100, top=50");
            }
        }
    </script>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
    	<div class="mypage-wrapper">
	        <form class="mypage">
	            <h1>MyPage</h1>
	            <p>이름 : ${sessionScope.member.name}<a href="#" id="memberUpdate" onclick="updateMember()">회원 정보 수정하기</a></p> 
	            <p>닉네임 : ${sessionScope.member.nickname}<a href="#" id="memberSecession" onclick="secessionMember()"> 회원 탈퇴</a></p>
	            <p>이메일 : ${sessionScope.member.email}</p>
	            <p>비밀번호 : <input type="password" id="passwd_input"value="${sessionScope.member.passwd}" disabled>&nbsp;<a href="#" id="memberSecession" onclick="updatePasswd()"> 비밀번호 변경</a></p>
	            <c:if test = "${sessionScope.member.summoner_name == null}">
	                <hr>
	                <p>소환사 명 등록하기 : <input type="text" name="summonerName" id="summonerName" placeholder="소환사 명"></p>
	                <input type="button" name="registerSummoner" id="registerSummoner" value="등록하기" onclick="summonerRegister()">
	            </c:if>
	            <c:if test = "${sessionScope.member.summoner_name != null}">
	                <hr>
	                <p>소환사 명 : ${sessionScope.member.summoner_name} <input type="button" value="변경하기" name="updateSummonerName" id="updateSummonerName"
	                    onclick="summonerNameUpdate()"></p>
	                    <script>callSummonerData()</script>
	                    <div id="printSummonerData_mypage"></div>
	                <p> <input type="button" value="최근 전적" name="matchHistory" id="matchHistory" onclick="callMatchHistory()">
	                    <input type="button" value="정보 갱신" name="summonerData" id="summonerData" onclick="updateSummonerData()">
	                </p>
	                
	                <hr>
	                <div id="printMatchHistory"></div>
	            </c:if>
	        </form>
        </div>
    </article>
</body>
</html>