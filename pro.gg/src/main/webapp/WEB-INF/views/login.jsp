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

    <style>
        aside{
            height: 920px;
            width: 300px;
            float: left;
            line-height: 30px;
            background-color: aqua;
        }
        article,form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: auto; margin-bottom: auto;
        }
    </style>
    <style>
        input[type="submit"]{
            width: 300px;
        }
        #id, #passwd{
            width: 300px;
        }
    </style>

</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="${pageContext.request.contextPath}/trylogin.do" method="POST">
            <input type="text" name="id" id="id" placeholder="아이디"> <br>
            <input type="password" name="passwd", id="passwd" placeholder="비밀번호"><br>
            <input type="submit" value="로그인">
            <p>처음이세요? <a href="${pageContext.request.contextPath}/move/register.do">회원가입하기</a></p>
        </form>
    </article>
    
</body>
</html>