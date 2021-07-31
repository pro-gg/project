<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
    <title></title>
</head>
<body>
	<header></header>
    <aside></aside>
    <article>
    	<div class="wrapper">
            <div class="content-wrapper">
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-header">
                            <table class="table">
                            	<thead>
	                            	<tr class="table-active">
	                            		<th scope="col" style="width: 60%">제목: ${post.postTitle }<br>작성자: ${post.nickname }<br>작성일자: ${post.postDate } ${post.postTime }</th>
	                            	</tr>
	                           	</thead>
	                           	<tbody>
	                           		<tr>
	                           			<td><pre>${post.postContent }</pre></td>
	                           		</tr>
	                           	</tbody>
                            </table>
                            <br>
                            <c:if test = "${sessionScope.member.nickname == post.nickname}">
                            	<a href="${pageContext.request.contextPath}/postModify.do?postNumber=${post.postNumber}">수정</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>