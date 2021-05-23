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
    <script>
        $(function(){
            if(document.getElementById('nickname').value !== '' && document.getElementById("name").value !== '' && document.getElementById("email").value !== ''){
                var btn = document.getElementById('btn');
                btn.disabled=false;
            }
        })

        function updateMemberData(){
            
        }

    </script>
    <style>
        form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: 50px; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="">
            <p>닉네임 : <input type="text" name="nickname" id="nickname"></p>
            <p>이름 : <input type="text" name="name" id="name"></p>
            <p>이메일 : <input type="email" name="email" id="email"></p>
            <p><input type="button" id="btn" value="수정하기" onclick="updateMemberData()" disabled="disabled"></p>
        </form>
    </article>
</body>
</html>