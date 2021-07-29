<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
	<br>
	<h2>${postType }</h2>
	<br><br>
	<table class="table">
        <thead>
            <tr>
                <th>No</th>
                <th>닉네임</th>
                <th>제목</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="boardList" items="${boardList}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${boardList.nickname}</td>
                    <td><a href="${pageContext.request.contextPath}/postUpdate.do?postNumber=${boardList.postNumber}">${boardList.postTitle}</a></td> 
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
</body>
</html>