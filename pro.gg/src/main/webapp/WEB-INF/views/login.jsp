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
    <script>
    </script>
    <style>
        input[type="submit"]{
            width: 170px;
        }
    </style>

</head>
<body>
    <form action="${pageContext.request.contextPath}/trylogin.do">
        <input type="text" name="id" id="id" placeholder="아이디"> <br>
        <input type="password" name="passwd", id="passwd" placeholder="비밀번호"> <br>
        <input type="submit" value="로그인">
    </form>
</body>
</html>