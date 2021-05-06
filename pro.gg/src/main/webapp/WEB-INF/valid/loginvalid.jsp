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
        $(function(){
            var result = "${result}";

            if(result === "NotExistId") {
                alert("존재하지 않는 아이디 입니다.");
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/move/login.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("article").html(data);
                    }
                })
            }
        })
    </script>
</head>
<body>
    <article></article>
</body>
</html>