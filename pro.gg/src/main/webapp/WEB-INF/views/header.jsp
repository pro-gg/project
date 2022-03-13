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
    <script src="/resources/js/bootstrap.min.js" charset="utf-8"></script>
    <script>
        function moveAdmin(){
            $(function(){

                var admin = {
                    'adminId' : prompt("관리자 아이디"),
                    'adminPasswd' : prompt("비밀번호") 
                }
     
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/confirmAdmin.do?admin='+encodeURI(JSON.stringify(admin)),
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }
        
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
    </script>
    <style>
        #admin{
            float: right;
        }
    </style>
</head>
<body>
    <header class="main-header-top">
        <a href="${pageContext.request.contextPath}/" class="logo">
        	<img class="img-fluid able-logo" src="/resources/images/progg.png" alt="logo"/>
        </a>
        <nav class="navbar navbar-static-top">
        	<a href="#!" data-toggle="offcanvas" class="sidebar-toggle">
        	</a>
        	<div class="navbar-custom-menu">
        		<ul class="top-nav lft-nav">
        			<li>
        				<a href ="${pageContext.request.contextPath }/champion.do">챔피언 정보</a>
        				<!-- <a href="#" id="admin" onclick="moveAdmin()">
       						<img src="/resources/images/person.png" id="imgPerson" alt="관리자 페이지 이동">
       					</a> -->
        			</li>
                    <li>
                        <a href="${pageContext.request.contextPath}/move/searchTeamName.do">팀 검색</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/move/searchCrew.do">조건 별 회원검색</a>
                    </li>
                    <li>
                    	<a href="${pageContext.request.contextPath}/move/myMatching.do">상대팀 찾기</a>
                    </li>
                    <li>
                    	<a href="${pageContext.request.contextPath}/move/board.do">게시판</a>
                    </li>
        		</ul>
        		<div class="navbar-custom-menu f-right">
        			<ul class="top-nav profile">
        				<li class="dropdown">
	        				<c:if test = "${sessionScope.member == null && sessionScope.admin == null}">
		       					<a href="#" id="login" name="login" onclick="location.href='${pageContext.request.contextPath}/move/login.do'">
		   							<img src="/resources/images/person.png" id="imgPerson" alt="로그인">
		   							<span>로그인</span>
		   						</a>
		                	</c:if>
		                	<c:if test="${sessionScope.member != null || sessionScope.admin != null || sessionScope.name}">
			                	<a href="#" data-toggle="dropdown" class="dropdown-toggle drop icon-circle drop-image">
	        						<span>
	        							<img class="img-circle" src="/resources/images/person.png" id="imgPerson" style="width:40px;">
	        						</span>
	        						<span>
	        							${sessionScope.member.name }
	        							<i class="icofont icofont-simple"></i>
	        						</span>
	        					</a>
	        					<ul class="dropdown-menu settings-menu">
	        						<li>
	        							<a href="#" onclick="myPage()">
	        								마이페이지
	        							</a>
	        						</li>
	        						<li>
	        							<a href="${pageContext.request.contextPath}/logout.do">
	        								로그아웃
	        							</a>
	        						</li>
	        					</ul>
	        				</c:if>
	                	</li>
        			</ul>
        		</div>
        	</div>
        </nav>
    </header>
</body>
</html>