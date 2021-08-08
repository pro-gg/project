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
            if(currentDate === postDate){
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
    </script>
    <style>
        #replyContent{
            padding-left: 25px;

            width: 500px;
        }
        #replyRecommend{
            margin-left: 450px;
        }
    </style>
</head>
<body>
    <table>
        <p>총 ${replyListSize} 개 댓글</p>
        <hr>
            <c:forEach var="replyDTOList" items="${replyDTOList}" varStatus="status">
                <thead>
                    <th id="nickname">${replyDTOList.nickname}</th>
                    <th id="replyContent">${replyDTOList.replyContent}</th>
                    <script>
                        var replyDate = '${replyDTOList.replyDate}';
                        var replyTime = '${replyDTOList.replyTime}';
                        checkPostDate(replyDate, replyTime, '${status.count}');
                    </script>
                    <th id="${status.count}"></th>
                    <th>
                        <button id="replyRecommend" onclick="recommendReply('${replyDTOList.replyNumber}', '${replyDTOList.nickname}')">추천! ${replyDTOList.replyRecommendCount}</button>
                        &nbsp;
                        <button id="replyNotRecommend" onclick="not_recommendReply('${replyDTOList.replyNumber}', '${replyDTOList.nickname}')">비추천! ${replyDTOList.replyNotRecommendCount}</button></th>
                </thead>                         
            </c:forEach>
       
    </table>
</body>
</html>