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
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function commentUpdateSuccess(commentNumber, postNumber){
            var nickname = '${comment.nickname}';
            var commentContent = document.getElementById("commentContent").value;
            var target = "commentUpdate";

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/commentUpdate.do?commentNumber=' + commentNumber + '&commentContent=' + commentContent + '&nickname=' + nickname + '&postNumber=' + postNumber + '&target=' + target,
                data:'',
                dataType:'',
                success:function(data){
                    window.location.reload();
                } 
            })
        }

        function commentUpdateCancel(){
            window.location.reload();
        }
    </script>
    <style>
        #updateReplyForm{
            float: left;
        }
        #nickname{
            float: left;
        }
    </style>
</head>
<body>
        <li id="updateCommentForm"><b id="nickname">${comment.nickname}</b>&nbsp;&nbsp;&nbsp;</li>
        <li id="updateCommentForm"><textarea name="" id="commentContent" cols="70" rows="2">${comment.commentContent}</textarea>&nbsp;&nbsp;&nbsp;</li>
        <li id="updateCommentForm">
            <button id="updateReply" onclick="commentUpdateSuccess('${comment.commentNumber}', '${postNumber }')">수정</button>
            &nbsp;
            <button id="updateCancel" onclick="commentUpdateCancel()">취소</button>
        </li>
    <br><br>
        
</body>
</html>