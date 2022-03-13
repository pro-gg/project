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
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script src="/resources/js/elements.js" charset="utf-8"></script>
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
                                <br>
                                <h4>'${searchKeyword}' 검색 결과</h4>
                                <br>
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
                                        <c:forEach var="searchPostByCondition" items="${searchPostByCondition}" varStatus="status">
                                            <tr>
                                                <td>${searchPostByCondition.postNumber}</td>
                                                <td>${searchPostByCondition.nickname}</td>
                                                <td><a href="${pageContext.request.contextPath}/postdetail.do?postNumber=${searchPostByCondition.postNumber}">${searchPostByCondition.postTitle}</a></td>
                                                <script>
                                                    var postDate = '${searchPostByCondition.postDate}';
                                                    var postTime = '${searchPostByCondition.postTime}';
                                                    checkPostDate(postDate, postTime, '${status.count}');
                                                </script>
                                                <td id="${status.count}"></td>
                                                <td>${searchPostByCondition.lookupCount}</td>
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