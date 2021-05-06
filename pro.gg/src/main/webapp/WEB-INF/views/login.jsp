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
        function registerForm(){
            $(function(){
            $("article").empty();

            $.ajax({
                type: 'get',
                url: '${pageContext.request.contextPath}/move/register.do',
                data: '',
                dataType:'',
                success:function(data){
                    $("article").html(data)
                }
            })
        })
        }
    </script>
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
    <article>
        <form action="${pageContext.request.contextPath}/trylogin.do" method="GET">
            <input type="text" name="id" id="id" placeholder="아이디"> <br>
            <input type="password" name="passwd", id="passwd" placeholder="비밀번호"> <br>
            <input type="submit" value="로그인"> <br>
        </form>
        <p>처음이세요? <a href="#" onclick="registerForm()">회원가입하기</a></p>
    </article>
    
</body>
</html>