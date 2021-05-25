<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        function existTeamName(){
            alert("이미 존재하는 팀 이름 입니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/move/teamCreate.do',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }
    </script>
</head>
<body>
    <c:if test="${teamname_exist != null}">
        <script>existTeamName()</script>
    </c:if>
</body>
</html>