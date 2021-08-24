<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
<script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
</head>
<body class="back-gray">
	<header></header>
    <aside></aside>
    <article>
	    <div class="content-wrapper champList">
	    	<h3 class="white-font champ-header">챔피언 정보</h3>
	    	<br><hr>
	    	<c:forEach var="row" items="${champList }">
				<a href="${pageContext.request.contextPath }/champInfo.do?champ=${row}"><img alt="챔피언" src="${imgPath }${row }.png" style="width:60px; height:60px; padding: 5px;"></a>
			</c:forEach>
	    </div>
	</article>
</body>
</html>