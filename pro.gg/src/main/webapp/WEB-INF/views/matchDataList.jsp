<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
</head>
<body>
    <c:forEach var="matchDataList" items="${matchDataList}">
        <p>챔피언 이름 : <c:out value="${matchDataList.championName}"></c:out></p>
        <p>K/D/A : <c:out value="${matchDataList.kills}/${matchDataList.deaths}/${matchDataList.assists}"></c:out></p>
        <p>승패 여부 : 
            <c:if test="${matchDataList.win == false }">
                <c:out value="패배.."></c:out>
            </c:if>
            <c:if test="${matchDataList.win == true}">
                <c:out value="승리!"></c:out>
            </c:if>
        </p>
        <p>획득한 골드 : <c:out value="${matchDataList.goldEarned}"></c:out></p>
        <p>소모한 골드 : <c:out value="${matchDataList.goldSpent}"></c:out></p>
        <p>최종 아이템 : <c:out value="${matchDataList.itemList}"></c:out></p>
        <p>소환사 마법 : <c:out value="${matchDataList.spellList}"></c:out></p>
        <hr>
    </c:forEach>
</body>
</html>