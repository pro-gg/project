<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
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
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/29.0.0/classic/ckeditor.js"></script> <!--ckeditor 경로-->
    <title>글 작성</title>
    <script>
        ClassicEditor
        .create(document.querySelector('#postContent'))
        .catch(error => {
            console.error(error);
        });
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
                            <c:if test="${boardNumber == 1}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="자유게시판" id="boardName" disabled>
                            </c:if>
                            <c:if test="${boardNumber == 2}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="팀원 모집 게시판" id="boardName" disabled>
                            </c:if>
                            <c:if test="${boardNumber == 3}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="팁 게시판" id="boardName" disabled>
                            </c:if>
                            <br>

                            <label for="postTitle">제목</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" placeholder="제목을 입력하세요" size=70 id="postTitle">
                            <br>
                            <hr>
                            <label for="postContent">내용</label><br>
                            <textarea id="postContent" cols="150" rows="10"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>