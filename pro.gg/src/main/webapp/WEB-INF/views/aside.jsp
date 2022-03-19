<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
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
                    url:'${pageContext.request.contextPath}/summoner/printSummonerData_aside.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("#printSummonerData_aside").html(data);
                    }
                })
            })
        }
    </script>

    <style>
        #nickname_aside, #summoner_name, #printSummonerData_aside{
            color: white;
        }
    </style>
</head>
<body>
    <aside class="main-sidebar" style="height:3252px;">
    	<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto;">
    		<section class="sidebar" id="sidebar-scroll" style="height: 699px; overflow: inherit; width: 100%;">
    			<ul class="sidebar-menu" style="overflow: inherit;">
		            <li>
		                <h3>INFO</h3>
		                <c:if test = "${sessionScope.member != null}">
		                    <p id="nickname_aside">닉네임 : ${sessionScope.member.nickname}</p>
		                    <p id="summoner_name">소환사 명 : ${sessionScope.member.summoner_name}</p>
		                    <script>callSummonerData()</script>
		                    <div id="printSummonerData_aside"></div>
		                </c:if>
		            </li>
		        </ul>
    		</section>
    	</div>
    </aside>
    <aside class="main-rightbar" style="height:3252px;">
    </aside>
</body>
</html>