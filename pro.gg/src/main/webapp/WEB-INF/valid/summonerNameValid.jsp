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
    <script>
        function registerSummonerName(){
            $(function(){
                 $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/',
                    data:'${member}',
                    dataType:'',
                    success:function(data){
                        alert("등록 되었습니다.");
                        $("body").html(data);
                    }
                })  
            })
        }
    </script>
</head>
<body>
    <aside></aside>
    <article></article>
    <c:if test = "${member != null}">
        <script>registerSummonerName()</script>
    </c:if>
</body>
</html>