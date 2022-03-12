<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function snsSubmit(){
            document.getElementById('frm').submit();
        }
    </script>
</head>
<body>
    <form action="${pageContext.request.contextPath}/trylogin.do" id="frm" method="post">
        <input type="hidden" name="id" value="${id}">
        <input type="hidden" name="passwd" value="${passwd}">
        <script>snsSubmit()</script>
    </form>
</body>
</html>