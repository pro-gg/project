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
    <style>
        article, p{
            position:absolute;
            left: 0; right: 0;
            margin-left: 80px; margin-right:0;
            top: 70px; bottom: auto;
            margin-top: auto; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <article>
        <p>아이디 : ${memberId}</p>
    </article>
    
</body>
</html>