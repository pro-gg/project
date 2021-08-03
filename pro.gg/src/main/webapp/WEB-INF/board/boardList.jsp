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
 <script>
     function checkPostDate(postDate, postTime, count){

            let today = new Date();

            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let date = today.getDate();

            var currentDate = year + "." + month +"." + date;
            if(currentDate === postDate){
                document.getElementById(count).innerHTML = postTime;
            }else{
                document.getElementById(count).innerHTML = postDate;
            }
        }
 </script>
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
                <th>작성일시</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="boardList" items="${boardList}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${boardList.nickname}</td>
                    <td><a href="${pageContext.request.contextPath}/postdetail.do?postNumber=${boardList.postNumber}">${boardList.postTitle}</a></td>
                    <script>
                        var postDate = '${boardList.postDate}';
                        var postTime = '${boardList.postTime}';
                        checkPostDate(postDate, postTime, '${status.count}');
                    </script>
                    <td id="${status.count}"></td>
                    <td>${boardList.lookupCount}</td>
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
</body>
</html>