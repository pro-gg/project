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
    <script src="/js/bootstrap.min.js" charset="utf-8"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function callPostContent(postTitle, nickname){
            console.log(postTitle);
            console.log(nickname);
        }
    </script>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="wrapper">
	    	<div class="content-wrapper">
		    	<div class="col-sm-12">
			    	<div class="card">
                        <div class="card-block">
			    			<div class="row">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>게시판</th>
                                            <th>번호</th>
                                            <th>닉네임</th>
                                            <th>제목</th>
                                            <th>게시일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchPostList" items="${searchPostList}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <c:if test="${sesearchPostList.boardNumber == 1}">
                                                        자유게시판
                                                    </c:if>
                                                </td>
                                                <td><c:out value="${searchPostList.postNumber}"></c:out></td>
                                                <td>${searchPostList.nickname}</td>
                                                <td>
                                                    <a href="#" onclick="callPostContent('${searchPostList.postTitle}', '${searchPostList.nickname}')">
                                                        ${searchPostList.postTitle}
                                                    </a>
                                                </td>
                                                <td>${searchPostList.postTime}</td>
                                                <script>
                                                    var postTime = '${searchPostList.postTime}';
                                                    console.log(postTime);
                                                </script>
                                            </tr>                  
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>