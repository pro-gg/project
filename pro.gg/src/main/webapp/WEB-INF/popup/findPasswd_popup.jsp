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
        function findPasswd(){
            $(function(){
                var findPasswd = {
                    'userid' : document.getElementById("userid").value,
                    'name' : document.getElementById("name").value,
                    'email' : document.getElementById("email").value
                }
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/findPasswd.do?findPasswd='+encodeURI(JSON.stringify(findPasswd)),
                    data :'',
                    dataType:'',
                    success:function(data){
                       $("body").html(data);
                    }
                })
            })
        }
    </script>
</head>
<body>
    <article>
        <div>
            <h3>비밀번호 찾기</h3>
            아이디 : <input type="text" name="userid" id="userid" required="required"> <br>
            이름 : <input type="text" name="name" id="name" required="required"> <br>
            이메일 : <input type="email" name="email" id="email" required="required"> <br>
            <input type="button" value="찾기" onclick="findPasswd()">
        </div>
    </article>
</body>
</html>