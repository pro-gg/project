<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script>
		    function commentUpdate(commentNumber, nickname, commentContent, postNumber){
		        var target = "update";
		
		        $.ajax({
		            type:'get',
		            url:'${pageContext.request.contextPath}/board/commentUpdate.do?commentNumber=' + commentNumber + '&commentContent=' + commentContent + '&nickname=' + nickname + '&postNumber=' + postNumber + '&target=' + target,
		            data:'',
		            dataType:'',
		            success:function(data){
		                $("#"+commentNumber+"update").html(data);
		            }
		        })
		    }
		
		    function commentDelete(commentNumber, nickname, commentContent, postNumber){
		        var target = "delete";
		
		        if(confirm("댓글을 삭제 하시겠습니까?")){
		            $.ajax({
		                type:'get',
		                url:'${pageContext.request.contextPath}/board/commentUpdate.do?commentNumber=' + commentNumber + '&commentContent=' + commentContent + '&nickname=' + nickname + '&postNumber=' + postNumber + '&target=' + target,
		                data:'',
		                dataType:'',
		                success:function(data){
		                    window.location.reload();
		                }
		            })
		        }  
		    }
    </script>
</head>
<body>
	<c:forEach var="commentList" items="${commentList}" varStatus="status">
		<ul id="${commentList.commentNumber}update">
			<script>
                 var commentDate = '${commentList.commentDate}';
                 var commentTime = '${commentList.commentTime}';
                 checkPostDate(commentDate, commentTime, '${commentList.commentNumber}');
            </script>
            <li>
                <b>${commentList.nickname}</b>
                &nbsp;
                <small id="${commentList.commentNumber }"></small>
                <div id="updateORdelete" style="float: right;">
                    <c:if test='${sessionScope.member.nickname == commentList.nickname}'>
                        <a href="#" onclick="commentUpdate('${commentList.commentNumber}', '${commentList.nickname}', '${commentList.commentContent}', '${postNumber }')">수정</a>
                        &nbsp;
                        <a href="#" onclick="commentDelete('${commentList.commentNumber}', '${commentList.nickname}', '${commentList.commentContent}', '${postNumber }')">삭제</a>
                    </c:if>
                </div>
            </li>
            <li id="commentContent">${commentList.commentContent}</li>  
            <hr>
        </ul>
    </c:forEach>
			<li>
             	<c:if test="${sessionScope.member != null}">
                     <table>
                         <thead>
                             <th><b id="nickname">${sessionScope.member.nickname}</b></th>
                             <th><textarea name="" id="commentArea${replyNumber }" cols="70" rows="2"></textarea></th>
                             <th><button onclick="addReplyComment('${replyNumber }', '${postNumber }')">등록하기</button></th>
                         </thead>
                     </table>
                 </c:if>
             </li>
		
</body>
</html>