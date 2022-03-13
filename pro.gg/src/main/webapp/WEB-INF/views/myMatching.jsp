<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
<script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
<script src="/resources/js/semantic_header.js" charset="utf-8"></script>
<script>
	function callTeamList(){
		$(function(){
			$.ajax({
				type:'get',
				url:'${pageContext.request.contextPath}/matchList.do',
				data:'',
				dataType:'',
				success:function(data){
					$("#printTeamList").html(data);
				}
			})
		})
	}
</script>
</head>
<body>
	<header></header>
    <aside></aside>
    <article>
    <div class="wrapper">
	    	<div class="content-wrapper">
		    	<div class="col-sm-12">
			    	<div class="card">
			    		<div class="card-header">
			    			<h5 class="card-header-text">매칭 목록</h5>
			    		</div>
			    		<div class="card-block">
			    			<div class="row">
								<c:if test="${sessionScope.member != null || sessionScope.member == null}">
									<script>callTeamList()</script>
								</c:if>
			    				<div class="col-sm-12 table-responsive" id="printTeamList">
			    				</div>
			    			</div>
			    		</div>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>