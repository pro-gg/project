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
        function loginSession(){
            $(function(){
                var result = "${result}";

                if(result === "Success"){
                    $.ajax({
                        type:'post',
                        url:'${pageContext.request.contextPath}/move/adminpage.do',
                        data:'${admin}',
                        dataType:'',
                        success:function(data){
                            $("body").html(data);
                        }
                    })
                }
            })
        }

        function loginfailSession(){
            $(function(){
                var result = "${result}";

                if(result === "NotExistId") {
                    alert("존재하지 않는 아이디 입니다.");
                    location.reload(); // 새로고침
                }
                else if(result === "failPasswd"){
                    alert("비밀번호가 잘못 되었습니다.");
                    location.reload(); // 새로고침
                }
            })
        }

    </script>
</head>
<body>
    <c:if test = "${admin != null}">
        <script>loginSession()</script>
    </c:if>
    <c:if test = "${admin == null}">
        <script>loginfailSession()</script>
    </c:if>
</body>
</html>