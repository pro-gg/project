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
                    <th><button id="replyRecommend">추천! ${replyDTOList.replyRecommendCount}</button>&nbsp;<button id="replyNotRecommend">비추천! ${replyDTOList.replyNotRecommendCount}</button></th>
                </thead>                         
            </c:forEach>
       
    </table>
</body>
</html>