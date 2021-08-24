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
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>

        function alreadyBelongInTeam(){
            alert("이미 다른팀에 소속되어 있습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/move/teammatch.do',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function existTeamName(){
            alert("이미 존재하는 팀 이름 입니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/move/teamCreate.do',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function memberTierLimit(){
            alert("신청 제한 티어가 본인의 티어보다 높습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/move/teamCreate.do',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function notExistSoloRankData(){
            alert("솔로 랭크 데이터가 없으면 팀을 생성하실 수 없습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/move/teammatch.do',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }
    </script>
</head>
<body>

    <c:if test="${alreadyBelongTeam != null}">
        <script>alreadyBelongInTeam()</script>
    </c:if>

    <c:if test="${teamname_exist != null}">
        <script>existTeamName()</script>
    </c:if>

    <c:if test="${memberTier != null}">
        <script>memberTierLimit()</script>
    </c:if>

    <c:if test="${notExistSoloRank != null}">
        <script>notExistSoloRankData()</script>
    </c:if>
</body>
</html>