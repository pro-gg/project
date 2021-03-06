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
        function findPasswdSuccess(){
            $(function(){

                var userid = {
                    'userid' : '${memberDTO.userid}'
                }
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/member/findPasswdSuccess.do?userid='+encodeURI(JSON.stringify(userid)),
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }

        function findPasswdFail(){
            $(function(){
                alert("입력 정보가 존재하지 않습니다.");
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/move/findPasswd.do',
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
    <c:if test="${memberDTO != null}">
        <script>findPasswdSuccess()</script>
    </c:if>
    <c:if test="${memberDTO == null}">
        <script>findPasswdFail()</script>
    </c:if>
</body>
</html>