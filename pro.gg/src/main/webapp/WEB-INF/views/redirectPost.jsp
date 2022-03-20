<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <title>Document</title>
    <script>
        function requestCrewSecession(teamName, target){
            let form = document.crewSecession;

            form.teamName.value = encodeURI(teamName);
            form.target.value = target;

            form.action = "${pageContext.request.contextPath}/team/crewsecession.do";
            form.method = "post";
            form.submit();
        }

        function requestRejectApply(nickname, target){
            let form = document.rejectApply;

            form.nickname.value = encodeURI(nickname);
            form.target.value = target;

            form.action = "${pageContext.request.contextPath}/team/rejectapply.do";
            form.method = "post";
            form.submit();
        }

        function requestSummonerRegister(target){
            let form = document.summonerRegister;

            form.target.value = target;

            form.action = "${pageContext.request.contextPath}/team/updateSummonerData.do";
            form.action = "post";
            form.submit();
        }
    </script>
</head>
<body>
    <form name="crewSecession">
        <input type="hidden" name="teamName">
        <input type="hidden" name="target">
    </form>
    <form name="rejectApply">
        <input type="hidden" name="nickname">
        <input type="hidden" name="target">
    </form>
    <form name="summonerRegister">
        <input type="hidden" name="target">
    </form>

    <c:if test="${target == 'updateSummonerName'}">
        <c:if test="${teamExist != null}">
            requestCrewSecession("'${member.teamName}'", "'${target}'")
        </c:if>
        <c:if test="${teamApply != null}">
            requestRejectApply("'${member.nickname}'", "'${target}'")
        </c:if>
    </c:if>
    <c:if test="${target == 'summoner_name_register'}">
        requestSummonerRegister("'${target}'");
    </c:if>
</body>
</html>