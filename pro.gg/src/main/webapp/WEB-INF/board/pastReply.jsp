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
        function callPostContent(postNumber){
            var num_postNumber = Number(postNumber);
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/board/postdetail.do?&postNumber=' + postNumber,
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function checkPostDate(replyDate, replyTime, count){

            let today = new Date();

            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let date = today.getDate();

            var currentDate = year + "." + month +"." + date;
            if(currentDate === replyDate){
                document.getElementById(count).innerHTML = replyTime;
            }else{
                document.getElementById(count).innerHTML = replyDate;
            }
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
                                            <th>댓글 번호</th>
                                            <th>게시글 번호</th>
                                            <th>닉네임</th>
                                            <th>댓글 내용</th>
                                            <th>작성일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchReplyList" items="${searchReplyList}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <c:out value="${searchReplyList.replyNumber}"></c:out>
                                                </td>
                                                <td><c:out value="${searchReplyList.postNumber}"></c:out></td>
                                                <td>${searchReplyList.nickname}</td>
                                                <td>
                                                    <a href="#" onclick="callPostContent('${searchReplyList.postNumber}')">
                                                        ${searchReplyList.replyContent}
                                                    </a>
                                                </td>
                                                <script>
                                                    var replyDate = '${searchReplyList.replyDate}';
                                                    var replyTime = '${searchReplyList.replyTime}';
                                                    checkPostDate(replyDate, replyTime, '${status.count}');
                                                </script>
                                                <td id="${status.count}"></td>
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