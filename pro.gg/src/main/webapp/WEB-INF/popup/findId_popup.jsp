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
        function findId(){
            $(function(){
                var findId = {
                    'name' : document.getElementById("name").value,
                    'email' : document.getElementById("email").value
                }
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/findId.do?findId='+encodeURI(JSON.stringify(findId)),
                    data :'',
                    dataType:'',
                    success:function(data){
                       $("body").html(data);
                    }
                })
            })
        }
    </script>
    <style>
        article, div{
            position:absolute;
            left: 0; right: 0;
            margin-left: 60px; margin-right:0;
            top: 50px; bottom: auto;
            margin-top: auto; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <article>
        <div>
            <h3>아이디 찾기</h3>
            이름 : <input type="text" name="name" id="name"> <br>
            이메일 : <input type="email" name="email" id="email"> <br>
            <input type="button" value="찾기" onclick="findId()">
        </div>
    </article>
    
</body>
</html>