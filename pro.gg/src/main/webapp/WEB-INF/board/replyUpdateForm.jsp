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
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function replyUpdateSuccess(replyNumber){
            var nickname = '${reply.nickname}';
            var replyContent = document.getElementById("replyContent").value;
            var target = "replyUpdate";

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/replyUpdate.do?replyNumber=' + replyNumber + '&replyContent=' + replyContent + '&nickname=' + nickname + '&target=' + target,
                data:'',
                dataType:'',
                success:function(data){
                    window.location.reload();
                } 
            })
        }

        function replyUpdateCancel(){
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
        <li id="updateReplyForm"><b id="nickname">${reply.nickname}</b>&nbsp;&nbsp;&nbsp;</li>
        <li id="updateReplyForm"><textarea name="" id="replyContent" rows="2">${reply.replyContent}</textarea>&nbsp;&nbsp;&nbsp;</li>
        <li id="updateReplyForm">
            <button id="updateReply" onclick="replyUpdateSuccess('${reply.replyNumber}')">수정</button>
            &nbsp;
            <button id="updateCancel" onclick="replyUpdateCancel()">취소</button>
        </li>
    <br><br>
        
</body>
</html>