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
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script>
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

        function recommendReply(replyNumber, nickname){
            if('${sessionScope.member}'.length === 0){
                alert("댓글 추천 기능은 로그인 이후에 이용하실 수 있습니다.");
                window.location.href='${pageContext.request.contextPath}/move/login.do';
            }

            var memberNickname = '${sessionScope.member.nickname}';
            var not_recommendReply = '${sessionScope.member.not_recommendreply}';
            var replyNumber = parseInt(replyNumber);
            var jsonObj = undefined;

            if(not_recommendReply !== '[]' && not_recommendReply !== ''){
                jsonObj = JSON.parse(not_recommendReply);
                for(var i = 0; i < jsonObj.length; i++){
                    if(jsonObj[i] === replyNumber){
                        alert("추천/비추천은 한번씩만 누를 수 있습니다.");
                        return;
                    }
                }
            }

            if(memberNickname.length !== 0){
                if(memberNickname === nickname){
                    alert("본인이 작성한 댓글은 추천, 또는 비추천을 할 수 없습니다.");
                }
                else{

                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/replyRecommendClick.do?replyNumber='+ replyNumber + '&nickname='+ memberNickname,
                        data:'',
                        dataType:'',
                        success:function(data){
                            window.location.reload();
                        }
                    })
                }
            }
        }

        function not_recommendReply(replyNumber, nickname){
            if('${sessionScope.member}'.length === 0){
                alert("댓글 추천 기능은 로그인 이후에 이용하실 수 있습니다.");
                window.location.href='${pageContext.request.contextPath}/move/login.do';
            }

            var memberNickname = '${sessionScope.member.nickname}';
            var recommendReply = '${sessionScope.member.recommendreply}';
            var replyNumber = parseInt(replyNumber);
            var jsonObj = undefined;

            if(recommendReply !== '[]' && recommendReply !== ''){
                jsonObj = JSON.parse(recommendReply);
                for(var i = 0; i < jsonObj.length; i++){
                    if(jsonObj[i] === replyNumber){
                        alert("추천/비추천은 한번씩만 누를 수 있습니다.");
                        return;
                    }
                }
            }

            if(memberNickname.length !== 0){
                if(memberNickname === nickname){
                    alert("본인이 작성한 댓글은 추천, 또는 비추천을 할 수 없습니다.");
                }
                else{

                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/replyNotRecommendClick.do?replyNumber='+ replyNumber + '&nickname='+ memberNickname,
                        data:'',
                        dataType:'',
                        success:function(data){
                            window.location.reload();
                        }
                    })
                }
            }
        }

        function replyUpdate(replyNumber, nickname, replyContent, count){
            var target = "contentUpdate";

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/replyUpdate.do?replyNumber=' + replyNumber + '&replyContent=' + replyContent + '&nickname=' + nickname + '&target=' + target,
                data:'',
                dataType:'',
                success:function(data){
                    $("#"+count+"update").html(data);
                }
            })
        }

        function replyDelete(replyNumber, nickname, replyContent){
            var target = "replydelete";

            if(confirm("댓글을 삭제 하시겠습니까?")){
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
        }
        
        function addComment(replyNumber, postNumber){
        	loadCommentList(replyNumber, postNumber);
        	if(document.getElementById('replyCommentList'+replyNumber).style.display ==='block'){
        		document.getElementById('replyCommentList'+replyNumber).style.display = 'none';
        		document.getElementById('comment-toggle'+replyNumber).textContent = '답글';
        	} else {
        		document.getElementById('replyCommentList'+replyNumber).style.display ='block';
        		document.getElementById('comment-toggle'+replyNumber).textContent = '숨기기';
        	}
        }
        
        function addReplyComment(replyNumber, postNumber){

            let today = new Date();

            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let date = today.getDate();
            let time = today.getTime();

            var hour = today.getHours();
            var minutes = today.getMinutes();
            var seconds = today.getSeconds();

            var commentDate = year + "." + month +"." + date;
            var commentTime = hour + ":" + minutes + ":" + seconds;

            var commentContent = document.getElementById("commentArea"+replyNumber).value;

            var comment = {
                commentDate : commentDate,
                commentTime : commentTime,
                commentContent : commentContent,
                replyNumber : replyNumber,
                postNumber : postNumber
            }

            $.ajax({

                type:'get',
                url:'${pageContext.request.contextPath}/addReplyComment.do?comment='+encodeURI(JSON.stringify(comment)),
                data:'',
                dataType:'',
                success:function(data){
                    window.location.reload();
                }
            })
        }
        
        function loadCommentList(replyNumber, postNumber){
    		$.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/callReplyCommentList.do?replyNumber='+replyNumber+'&postNumber='+postNumber,
                data:'',
                dataType:'',
                success:function(data){
                    $("#replyCommentList"+replyNumber).html(data);
                }
            })
    	}
    </script>
    <style>
        ul{
            list-style: none;
        }
        #replyContent{
            /* padding-left: 25px; */

            width: 600px;
        }
        #updateORdelete{
            float: right;
        }
    </style>
</head>
<body>
        <p>총 ${replyListSize} 개 댓글</p>
        <hr>
            <c:forEach var="replyDTOList" items="${replyDTOList}" varStatus="status">
                <ul id="${status.count}update">
                    <script>
                        var replyDate = '${replyDTOList.replyDate}';
                        var replyTime = '${replyDTOList.replyTime}';
                        checkPostDate(replyDate, replyTime, '${status.count}');
                    </script>
                    <li>
                        <b>${replyDTOList.nickname}</b>
                        &nbsp;
                        <small id="${status.count}"></small>
                        <div id="updateORdelete">
							<a href="#" id='comment-toggle${replyDTOList.replyNumber }' onclick="addComment('${replyDTOList.replyNumber}','${replyDTOList.postNumber }')">답글</a>
                            <c:if test='${sessionScope.member.nickname == replyDTOList.nickname}'>
                                <a href="#" onclick="replyUpdate('${replyDTOList.replyNumber}', '${replyDTOList.nickname}', '${replyDTOList.replyContent}', '${status.count}')">수정</a>
                                &nbsp;
                                <a href="#" onclick="replyDelete('${replyDTOList.replyNumber}', '${replyDTOList.nickname}', '${replyDTOList.replyContent}')">삭제</a>
                            </c:if>
                        </div>
                    </li>
                    <li id="replyContent">${replyDTOList.replyContent}</li>  
                    <li>
                        <button id="replyRecommend" onclick="recommendReply('${replyDTOList.replyNumber}', '${replyDTOList.nickname}')">추천! ${replyDTOList.replyRecommendCount}</button>
                        &nbsp;
                        <button id="replyNotRecommend" onclick="not_recommendReply('${replyDTOList.replyNumber}', '${replyDTOList.nickname}')">비추천! ${replyDTOList.replyNotRecommendCount}</button>
                    </li>
                    <br>
                    <div id="replyCommentList${replyDTOList.replyNumber }"></div>
                </ul>                         
            </c:forEach>
</body>
</html>