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
        function notexistSummoner(){
            $(function(){
                var summoner_name = "${summoner_name}";
                alert("소환사 명 : " + summoner_name + " 은(는) 존재하지 않습니다.");

                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/mypage.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }

        function alreadyExistSummoner(){
            $(function(){
                var summoner_name_exist = "${summoner_name_exist}";
                alert("이미 등록되어 있는 소환사 명 입니다.");

                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/mypage.do',
                    data:'',
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
    <c:if test="${summoner_name != null}">
        <script>notexistSummoner()</script>
    </c:if>
    <c:if test="${summoner_name_exist != null}">
        <script>alreadyExistSummoner()</script>
    </c:if>
</body>
</html>