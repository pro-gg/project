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
        function myPage(){
            $(function(){
                var member = "${member}";

                $.ajax({
                    type:'get',
                    url: '${pageContext.request.contextPath}/mypage.do',
                    data: member,
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
    <aside>
        <ul>
            <li>
                <h3>INFO</h3>
                <c:if test = "${member == null}">
                    <input type="button" value="LOGIN" name="login" id="login" 
                    onclick="location.href='${pageContext.request.contextPath}/move/login.do'">
                </c:if>
                <c:if test = "${member != null}">
                    <p>${member.nickname}</p>
                    <p>소환사 명 : ${member.summoner_name}</p>
                    <a href="#" onclick="myPage()">마이페이지</a>
                    <a href="${pageContext.request.contextPath}/logout.do">로그아웃</a>
                </c:if>
            </li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </aside>
</body>
</html>