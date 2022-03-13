<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
<script src="/resources/js/semantic_header.js" charset="utf-8"></script>
</head>
<body class="back-gray">
	<header></header>
    <aside></aside>
	<article>
	    <div class="content-wrapper champList">
	    	<h3 class="white-font champ-header">${name} : ${title }</h3>
	    	<br><hr>
	    	<div class="info">
		    	<img alt="${champion}" src="http://ddragon.leagueoflegends.com/cdn/img/champion/loading/${champion}_0.jpg" class="champ-img" style=" height:500px">
		    	<div class="right-info">
		    		<span class="info-font">체력 : </span><span>${stats.hp }(+${stats.hpperlevel})</span><br>
		    		<span class="info-font">마나 : </span><span>${stats.mp }(+${stats.mpperlevel})</span><br>
		    		<span class="info-font">이동속도 : </span><span>${stats.movespeed }</span><br>
		    		<span class="info-font">방어력 : </span><span>${stats.armor }(+${stats.armorperlevel})</span><br>
		    		<span class="info-font">마법저항력 : </span><span>${stats.spellblock }(+${stats.spellblockperlevel})</span><br>
		    		<span class="info-font">사거리 : </span><span>${stats.attackrange }</span><br>
		    		<span class="info-font">체력회복 : </span><span>${stats.hpregen }(+${stats.hpregenperlevel})</span><br>
		    		<span class="info-font">마나회복 : </span><span>${stats.mpregen }(+${stats.mpregenperlevel})</span><br>
		    		<span class="info-font">치명타 : </span><span>${stats.crit }(+${stats.critperlevel})</span><br>
		    		<span class="info-font">공격력 : </span><span>${stats.attackdamage }(+${stats.attackdamageperlevel})</span><br>
		    		<span class="info-font">공격속도 : </span><span>${stats.attackspeed }(+${stats.attackspeedperlevel})</span><br>
		    		<div class="champ_skill">
					<div class="skill">
						<img alt="${passive_name }" src="http://ddragon.leagueoflegends.com/cdn/11.11.1/img/passive/${passive }" style="width: 60px; height:60px">
						<p>${passive_des }</p>
					</div>
				<c:forEach var="row" items="${champSpells }">
					&nbsp;&nbsp;&nbsp;
					<div class="skill">
						<img alt="${row.name}" src="http://ddragon.leagueoflegends.com/cdn/11.11.1/img/spell/${row.id }.png" style=" width: 60px; height:60px">
						<p>${row.tooltip }</p>
					</div>
				</c:forEach>
					</div>
		    	</div>
			</div>
			<br><hr>
			<div>
				<h3 class="white-font">사용 팁</h3>
				<c:forEach var="ally" items="${allytips }">
					<p>- ${ally }</p>
				</c:forEach>
			</div>
			<div>
				<h3 class="white-font">상대 팁</h3>
				<c:forEach var="enemy" items="${enemytips }">
					<p>- ${enemy }</p>
				</c:forEach>
			</div>
			<br><br><br>
	    </div>
	</article>
</body>
</html>