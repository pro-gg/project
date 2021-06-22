<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 찾기</title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/js/semantic_aside.js" charset="utf-8"></script>
<script src="/js/semantic_header.js" charset="utf-8"></script>
<script>
	function callTeamList(){
		$(function(){
			$.ajax({
				type:'get',
				url:'${pageContext.request.contextPath}/teamList.do',
				data:'',
				dataType:'',
				success:function(data){
					$("#printTeamList").html(data);
				}
			})
		})
	}
	
	function TeamCreate(){
		var member = "${sessionScope.member}";
		var teamName = "${sessionScope.member.teamName}";

		if(member.length === 0){
			alert("로그인이 필요한 서비스 입니다.");
		}
		else if(teamName.length !== 0){
			alert("이미 다른 팀에 소속되어 있습니다.");
		}
		else{
			$(function(){
				$.ajax({
					type:'get',
					url:'${pageContext.request.contextPath}/move/teamCreate.do',
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data);
					}
				})
			})
		}
	}
</script>
<style>
	#createTeam{
		float: right;
	}
</style>
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
			    			<h5 class="card-header-text">팀 목록</h5>
							<input type="button" value="팀 생성" name="createTeam" id="createTeam" onclick="TeamCreate()">
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