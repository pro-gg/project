<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script src="/resources/js/elements.js" charset="utf-8"></script>
    <title></title>
    <script>
        $(function(){
            $('#recommendButton').click(function(){
                if('${sessionScope.member}'.length === 0){
                    alert("게시글 추천 기능은 로그인 후 이용하실 수 있습니다.");
                    window.location.href='${pageContext.request.contextPath}/move/login.do';
                }
                var memberNickname = '${sessionScope.member.nickname}';
                var not_recommendpost = '${sessionScope.member.not_recommendpost}';
                var postNumber = parseInt('${post.postNumber}');
                var jsonObj = undefined;

                if(not_recommendpost !== '[]' && not_recommendpost !== ''){
                    jsonObj = JSON.parse(not_recommendpost);
                    for(var i = 0; i < jsonObj.length; i++){
                        if(jsonObj[i] === postNumber){
                            alert("추천/비추천은 한번씩만 누를 수 있습니다.");
                            return;
                        }
                    }
                }

                if(memberNickname.length !== 0){
                    if(memberNickname === '${post.nickname}'){
                        alert("글 작성자는 추천, 또는 비추천을 할 수 없습니다.");
                    }
                    else{
                        // 글 작성자가 아닌 타인이 추천 버튼을 클릭했을 경우
                        $.ajax({
                            type:'get',
                            url:'${pageContext.request.contextPath}/clickRecommend.do?postNumber='+'${post.postNumber}'+'&nickname='+memberNickname,
                            data:'',
                            dataType:'',
                            success:function(data){
                                window.location.reload();
                            }
                        })

                    }
                }
            })

            $('#not_recommendButton').click(function(){
                if('${sessionScope.member}'.length === 0){
                    alert("게시글 비추천 기능은 로그인 후 이용하실 수 있습니다.");
                    window.location.href='${pageContext.request.contextPath}/move/login.do';
                }

                var memberNickname = '${sessionScope.member.nickname}';
                var recommendpost = '${sessionScope.member.recommendpost}';
                var postNumber = parseInt('${post.postNumber}');
                var jsonObj = undefined;

                if(recommendpost !== '[]' && recommendpost !== ''){
                    jsonObj = JSON.parse(recommendpost);
                    for(var i = 0; i < jsonObj.length; i++){
                        if(jsonObj[i] === postNumber){
                            alert("추천/비추천은 한번씩만 누를 수 있습니다.");
                            return;
                        }
                    }
                }

                if(memberNickname.length !== 0){
                    if(memberNickname === '${post.nickname}'){
                        alert("글 작성자는 추천, 또는 비추천을 할 수 없습니다.");
                    }
                    else{
                        // 글 작성자가 아닌 타인이 비추천 버튼을 클릭했을 경우
                        $.ajax({
                            type:'get',
                            url:'${pageContext.request.contextPath}/clickNotRecommend.do?postNumber='+'${post.postNumber}'+'&nickname='+memberNickname,
                            data:'',
                            dataType:'',
                            success:function(data){
                                window.location.reload();
                            }

                        })
                    }
                }
            })

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/callReplyList.do?postNumber='+'${post.postNumber}',
                data:'',
                dataType:'',
                success:function(data){
                    $("#replyList").html(data);
                }
            })
        })

        function replyRegister(postNumber){

            let today = new Date();

            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let date = today.getDate();
            let time = today.getTime();

            let hour = today.getHours();
            let minutes = today.getMinutes();
            let seconds = today.getSeconds();

            let replyDate = year + "." + month +"." + date;
            let replyTime = hour + ":" + minutes + ":" + seconds;

            let replyContent = document.getElementById("replyArea").value;

            let reply = {
                replyDate : replyDate,
                replyTime : replyTime,
                replyContent : replyContent,
                postNumber : postNumber
            }

            $.ajax({

                type:'get',
                url:'${pageContext.request.contextPath}/replyregister.do?reply='+encodeURI(JSON.stringify(reply)),
                data:'',
                dataType:'',
                success:function(data){
                    window.location.reload();
                }
            })
        }
    </script>
    <style>
        #recommendButton{
            margin-left: 500px;
        }
        #postContent{
            margin-left: 200px;
        }
        img{
            height: auto;
            width: 600px;
        }
        #replyArea, #registerReply{
            margin-left: 20px;
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
                        <div class="card-header">
                            <table class="table">
                            	<thead>
	                            	<tr class="table-active">
	                            		<th scope="col" style="width: 60%">제목: ${post.postTitle }<br>작성자: ${post.nickname }<br>작성일자: ${post.postDate } ${post.postTime }</th>
	                            	</tr>
	                           	</thead>
	                           	<tbody>
	                           		<tr>
	                           			<td><pre id="postContent">${post.postContent }</pre></td>
	                           		</tr>
	                           	</tbody>
                            </table>
                            <br>
                            <button id="recommendButton">
                                좋아요!
                                <hr>
                                <b>${post.postRecommendCount}</b>
                            </button>
                            &nbsp;&nbsp;
                            <button id="not_recommendButton">
                                싫어요!
                                <hr>
                                <b>${post.postNotRecommendCount}</b>
                            </button>
                            <br>
                            <c:if test = "${sessionScope.member.nickname == post.nickname}">
                            	<a href="${pageContext.request.contextPath}/postModify.do?postNumber=${post.postNumber}">수정</a>
                            </c:if>&nbsp;
                            <c:if test = "${sessionScope.member.nickname == post.nickname}">
                            	<a href="${pageContext.request.contextPath}/postDelete.do?postNumber=${post.postNumber}&nickname=${post.nickname}">삭제</a>
                            </c:if>
                            <hr>
                            <c:if test="${sessionScope.member == null}">
                                <p>로그인 후 댓글을 작성할 수 있습니다.</p>
                            </c:if>
                            <c:if test="${sessionScope.member != null}">
                                <table>
                                    <thead>
                                        <th><b id="nickname">${sessionScope.member.nickname}</b></th>
                                        <th><textarea name="" id="replyArea" cols="70" rows="2"></textarea></th>
                                        <th><button id="registerReply" onclick="replyRegister('${post.postNumber}')">등록하기</button></th>
                                    </thead>
                                </table>
                            </c:if>
                            <div id="replyList"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>