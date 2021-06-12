<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/js/semantic_aside.js" charset="utf-8"></script>
<script src="/js/semantic_header.js" charset="utf-8"></script>
</head>
<body>
	<header></header>
    <aside></aside>
	<article>
	    <div class="content-wrapper champList">
	    	<h3 class="white-font champ-header">${champion}</h3>
	    	<br><hr>
	    	<img alt="${champion}" src="http://ddragon.leagueoflegends.com/cdn/img/champion/loading/${champion}_0.jpg" style=" height:600px">
			<img alt="${passive }" src="http://ddragon.leagueoflegends.com/cdn/11.11.1/img/passive/${passive }" style="width: 60px; height:60px">
			<c:forEach var="row" items="${spells }">
				<img alt="${spells}" src="http://ddragon.leagueoflegends.com/cdn/11.11.1/img/spell/${row }.png" style=" width: 60px; height:60px">
			</c:forEach>
	    </div>
	</article>
</body>
</html>