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
    <c:if test="${ranked_solo != null}">
        <p>솔로 랭크</p>
        <p>LP : ${ranked_solo.leaguePoints}</p>
        <p>티어 : ${ranked_solo.tier} ${ranked_solo.tier_rank}</p>
        <p>승 : ${ranked_solo.wins}, 패 : ${ranked_solo.losses}</p>
        <p>승률 : ${ranked_solo.rate}%</p>
    </c:if>
</body>
</html>