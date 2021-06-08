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
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
</head>
<body>
    <table class="table">
        <thead>
            <tr>
                <th>No</th>
                <th>팀이름</th>
                <th>팀장</th>
                <th>티어 제한</th>
                <th>플레이 가능 시간</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="teamList" items="${teamList}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/teamdetail.do?teamName=${teamList.teamName}">
                            ${teamList.teamName}
                        </a>
                    </td>
                    <td>${teamList.captinName}</td> 
                    <td>${teamList.tier_limit}</td>
                    <td>${teamList.week_input} ${teamList.startTime} ~ ${teamList.endTime}</td>
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
</body>
</html>



