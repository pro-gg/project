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
        function check_pw(){

            if(document.getElementById('passwd').value === '') {
                document.getElementById('check').innerHTML='';
                document.getElementById('input_passwd').innerHTML='비밀번호를 입력하십시오.';
                document.getElementById('input_passwd').style.color='red';
            }
            else if(document.getElementById('passwdcheck').value == ''){
                document.getElementById('input_passwd').innerHTML='';
                document.getElementById('check').innerHTML='비밀번호를 한번 더 입력해주세요.';
                document.getElementById('check').style.color='blue';
            } else{
                if( document.getElementById('passwd').value === document.getElementById('passwdcheck').value) {
                    document.getElementById('check').innerHTML='비밀번호가 일치합니다.';
                    document.getElementById('check').style.color='blue';
                } else{
                    document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
                    document.getElementById('check').style.color='red';
                }
            }
        }

    </script>
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
            margin-top: 100px; margin-bottom: auto;
        }
        input[type="text"], input[type="password"]{
            width: 300px;
        }

        #btn{
            width: 150px;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="${pageContext.request.contextPath}/tryregister.do" method="POST">
            <h2>회원가입</h2>
            <input type="text" placeholder="이름" name="name" id="name"> <br><br>
            <input type="text" placeholder="닉네임" name="nickname" id="nickname"> <br><br>
            <input type="text" placeholder="아이디" name="id" id="id"> <br><br>
            <input type="password" placeholder="비밀번호" name="passwd" id="passwd" onchange="check_pw()"><span id="input_passwd"></span><br><br>
            <input type="password" placeholder="비밀번호 확인" name="passwdcheck" id="passwdcheck" onchange="check_pw()"><span id="check"></span><br><br>
            <input type="email" placeholder="이메일" name="email" id="email"> <br><br>
            <input type="button" value="취소" id="btn" onclick="location.href='${pageContext.request.contextPath}'">
            <input type="submit" value="가입하기" id="btn">
        </form>
    </article>
    
</body>
</html>