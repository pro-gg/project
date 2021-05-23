<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function myPage(){
            $(function(){
                var member = "${sessionScope.member}";

                $.ajax({
                    type:'get',
                    url: '${pageContext.request.contextPath}/move/mypage.do',
                    data: member,
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
                    url:'${pageContext.request.contextPath}/printSummonerData_aside.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("#printSummonerData_aside").html(data);
                    }
                })
            })
        }

        function findId(){
            window.open("${pageContext.request.contextPath}/move/findId.do", "findId", "width=550, height=450, left=100, top=50");
        }
        function findPasswd(){
            window.open("${pageContext.request.contextPath}/move/findPasswd.do", "findId", "width=550, height=450, left=100, top=50");
        }
    </script>
</head>
<body>
    <aside class="main-sidebar hidden-print" style="height:3252px;">
    	<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto;">
    		<section class="sidebar" id="sidebar-scroll" style="height: 699px; overflow: inherit; width: 100%;">
    			<ul class="sidebar-menu" style="overflow: inherit;">
		            <li>
		                <h3>INFO</h3>
		                <c:if test = "${sessionScope.member == null}">
		                    <input type="button" value="LOGIN" name="login" id="login" 
		                    onclick="location.href='${pageContext.request.contextPath}/move/login.do'"> <br>
		                    <a href="#" onclick="findId()">아이디 찾기</a> 
		                    <a href="#" onclick="findPasswd()">비밀번호 찾기</a>
		                </c:if>
		                <c:if test = "${sessionScope.member != null}">
		                    <p>${sessionScope.member.nickname}</p>
		                    <p>소환사 명 : ${sessionScope.member.summoner_name}</p>
		                    <script>callSummonerData()</script>
		                    <div id="printSummonerData_aside"></div>
		                    <a href="#" onclick="myPage()">마이페이지</a>
		                    <a href="${pageContext.request.contextPath}/logout.do">로그아웃</a>
		                </c:if>
		            </li>
		            <li>
		            	<h3><a href="${pageContext.request.contextPath }/move/teammatch.do">팀 매칭</a></h3>
		            </li>
		            <li></li>
		            <li></li>
		        </ul>
    		</section>
    	</div>
    </aside>
</body>
</html>