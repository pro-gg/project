<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <title></title>
</head>
<body>
    <table class="table">
        <tbody>
            <c:forEach var="teamList" items="${teamList}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/teamdetail.do?teamName=${teamList.teamName}">
                            ${teamList.teamName}
                        </a>
                    </td>
                    <td>
                        ${teamList.captinName}
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>