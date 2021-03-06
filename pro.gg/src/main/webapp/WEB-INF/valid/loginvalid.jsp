<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function loginSession(){
            $(function(){
                let result = "${result}";

                if(result === "Success"){
                    location.replace("${pageContext.request.contextPath}/");
                }
            })
        }

        function loginfailSession(){
            $(function(){
                let result = "${result}";

                if(result === "NotExistId") {
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/move/login.do',
                        data:'',
                        dataType:'',
                        success:function(data){
                            alert("존재하지 않는 아이디 입니다.");
                            $("body").html(data);
                        }
                    })
                }
                else if(result === "failPasswd"){
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/move/login.do',
                        data:'',
                        dataType:'',
                        success:function(data){
                            alert("비밀번호가 잘못 되었습니다.");
                            $("body").html(data);
                        }
                    })

                } 
            })
        }
        
    </script>
</head>
<body>
    <c:if test = "${sessionScope.member != null}">
        <script>loginSession()</script>
    </c:if>
    <c:if test = "${sessionScope.member == null}">
        <script>loginfailSession()</script>
    </c:if>
</body>
</html>