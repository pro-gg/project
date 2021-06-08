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
        function approve(){

        }

        function reject(nickname, teamName){
            if(confirm("신청을 거절 하시겠습니까?") === true){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/rejectapply.do?nickname='+nickname+"&teamName="+teamName,
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                        window.location.reload();
                    }
                })
            }
        }
    </script>
</head>
<body>
    <table class="table">
        <thead>
            <tr>
                <th>No</th>
                <th>닉네임</th>
                <th>소환사 명</th>
                <th>라인</th>
                <th>티어</th>
                <th>승률</th>
                <th>수락 여부</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="applyMemberList" items="${applyMemberList}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${applyMemberList.nickname}</td>
                    <td>${applyMemberList.summoner_name}</td> 
                    <td>
                        <c:choose>
                            <c:when test="${applyMemberList.line eq 'top'}">
                                탑
                            </c:when>
                            <c:when test="${applyMemberList.line eq 'middle'}">
                                미드
                            </c:when>
                            <c:when test="${applyMemberList.line eq 'jungle'}">
                                정글
                            </c:when>
                            <c:when test="${applyMemberList.line eq 'bottom'}">
                                바텀
                            </c:when>
                            <c:when test="${applyMemberList.line eq 'suppoter'}">
                                서포터
                            </c:when>
                        </c:choose>
                    </td>
                    <td>${applyMemberList.tier} ${applyMemberList.tier_rank}</td>
                    <td>${applyMemberList.rate}%</td>
                    <td>
                        <button onclick="approve()">수락</button>&nbsp;&nbsp;<button onclick="reject('${applyMemberList.nickname}', '${applyMemberList.teamName}')">거절</button>
                    </td>
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
</body>
</html>