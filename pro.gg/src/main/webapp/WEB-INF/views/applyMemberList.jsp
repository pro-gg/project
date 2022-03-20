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
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script>
        function approve(applyLine, nickname){
            if(confirm("수락 하시겠습니까?") === true){
                // 수락하고자 하는 회원의 지원 라인에 현재 지정되어 있는 팀원이 있는지 검증
                if(applyLine === 'top' && "${team.top}".length !== 0){
                    alert("이미 지정된 회원이 있어 수락할 수 없습니다.");
                }
                else if(applyLine === 'middle' && "${team.middle}".length !== 0){
                    console.log("${team.middle}");
                    alert("이미 지정된 회원이 있어 수락할 수 없습니다.");
                }
                else if(applyLine === 'jungle' && "${team.jungle}".length !== 0){
                    alert("이미 지정된 회원이 있어 수락할 수 없습니다.");
                }
                else if(applyLine === 'bottom' && "${team.bottom}".length !== 0){
                    alert("이미 지정된 회원이 있어 수락할 수 없습니다.");
                }
                else if(applyLine === 'suppoter' && "${team.suppoter}".length !== 0){
                    alert("이미 지정된 회원이 있어 수락할 수 없습니다.");
                }
                else{
                    $.ajax({
                        type:'post',
                        url:'${pageContext.request.contextPath}/team/teamapprove.do?nickname='+nickname,
                        data:'',
                        dataType:'',
                        success:function(data){
                            $("body").html(data);
                            applyStatusView();
                        }
                    })
                }
            }
        }

        function reject(nickname, teamName){
            if(confirm("신청을 거절 하시겠습니까?") === true){
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/team/rejectapply.do?nickname='+nickname+"&teamName="+teamName+"&target=reject",
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                        applyStatusView();
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
                        <button onclick="approve('${applyMemberList.line}', '${applyMemberList.nickname}')">수락</button>&nbsp;&nbsp;
                        <button onclick="reject('${applyMemberList.nickname}', '${applyMemberList.teamName}')">거절</button>
                    </td>
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
</body>
</html>