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
    <script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function loginSession(){
            $(function(){
                var result = "${result}";

                if(result === "Success"){
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/',
                        data:'${sessionScope.member}',
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
    <header></header>
    <aside></aside>
    <article></article>
    <c:if test = "${sessionScope.member != null}">
        <script>loginSession()</script>
    </c:if>
    <c:if test = "${sessionScope.member == null}">
        <script>loginfailSession()</script>
    </c:if>
</body>
</html>