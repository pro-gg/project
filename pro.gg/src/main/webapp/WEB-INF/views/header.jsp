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
    </script>
    <style>
        #admin{
            float: right;
        }
    </style>
</head>
<body>
    <header class="main-header-top hidden-print">
        <a href="${pageContext.request.contextPath}/" class="logo">
        	<img class="img-fluid able-logo" src="/images/progg.png" alt="logo"/>
        </a>
        <nav class="navbar navbar-static-top">
        	<a href="#!" data-toggle="offcanvas" class="sidebar-toggle">
        	</a>
        	<div class="navbar-custom-menu">
        		<ul class="top-nav">
        			<li>
        				<a href="#" id="admin" onclick="moveAdmin()">
       						<img src="/images/person.png" id="imgPerson" alt="관리자 페이지 이동">
       					</a>
        			</li>
        		</ul>
        	</div>
        </nav>
    </header>
</body>
</html>