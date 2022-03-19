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
    <script src="/resources/js/bootstrap.min.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function postDelete(postNumber, nickname){
            if(confirm("정말로 글을 삭제 하시겠습니까?")){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/board/postDelete.do?postNumber='+postNumber+'&nickname='+nickname,
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            }      
        }
    </script>
    <style>
        #nickname{
            font-size: 8px;
            color: grey;
        }
        #updateORdelete{
            font-size: 15px;
            float: right;
            margin-right: 20px;
        }
        #postContent{
            margin-left: 200px;
        }
        img{
            height: auto;
            width: 600px;
        }
    </style>
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
                                        <td>${selectPostContent.postTitle}</td>
                                        <td id="nickname">
                                            ${selectPostContent.nickname} <br><br>
                                        </td>
                                        <p id="updateORdelete"><a href="#">수정</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="postDelete('${selectPostContent.postNumber}', '${selectPostContent.nickname}')">삭제</a></p>
                                    </thead>
                                </table>
                                <hr>
                                <table class="table">
                                    <tbody>
                                        <div id="postContent">${selectPostContent.postContent}</div>
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