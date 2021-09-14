<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 찾기</title>
<link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/mystyle.css"/>
<script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
<script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
<script src="/pro.gg/resources/js/bootstrap.min.js" charset="utf-8"></script>
<script>

	$(function(){
		var notexistTeam = "${notexistTeam}";
		if(notexistTeam.length !== 0){
			alert("조건에 해당되는 팀이 존재하지 않습니다!");
		}
	})
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

	function teamSearch(){

		var teamName = document.getElementById("searchTeamName").value;
		var tier_limit = document.getElementById("tier_limit");
		var empty_line = document.getElementById("empty_line");

		var searchData = {
			'teamName' : teamName,
			'tier_limit' : tier_limit.options[tier_limit.selectedIndex].value,
			'empty_line' : empty_line.options[empty_line.selectedIndex].value
		};

		$.ajax({
			type:'get',
			url:'${pageContext.request.contextPath}/searchTeam.do?searchData='+encodeURI(JSON.stringify(searchData)),
			data:'',
			dataType:'',
			success:function(data){
				$("body").html(data);
			}
		})
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
								
								<select name="tier_limit" id="tier_limit">
									<option value="">티어 제한</option>
									<option value="IRON IV">IRON IV</option>
									<option value="IRON III">IRON III</option>
									<option value="IRON II">IRON II</option>
									<option value="IRON I">IRON I</option>
									<option value="BRONZE IV">BRONZE IV</option>
									<option value="BRONZE III">BRONZE III</option>
									<option value="BRONZE II">BRONZE II</option>
									<option value="BRONZE I">BRONZE I</option>
									<option value="SILVER IV">SILVER IV</option>
									<option value="SILVER III">SILVER III</option>
									<option value="SILVER II">SILVER II</option>
									<option value="SILVER I">SILVER I</option>
									<option value="GOLD IV">GOLD IV</option>
									<option value="GOLD III">GOLD III</option>
									<option value="GOLD II">GOLD II</option>
									<option value="GOLD I">GOLD I</option>
									<option value="PLATINUM IV">PLATINUM IV</option>
									<option value="PLATINUM III">PLATINUM III</option>
									<option value="PLATINUM II">PLATINUM II</option>
									<option value="PLATINUM I">PLATINUM I</option>
									<option value="DIAMOND IV">DIAMOND IV</option>
									<option value="DIAMOND III">DIAMOND III</option>
									<option value="DIAMOND II">DIAMOND II</option>
									<option value="DIAMOND I">DIAMOND I</option>
									<option value="MASTER I">MASTER I</option>
									<option value="GRANDMASTER I">GRANDMASTER I</option>
									<option value="CHALLENGER I">CHALLENGER I</option>
								</select>
								<select name="empty_line" id="empty_line">
									<option value="">지원가능 라인</option>
									<option value="top">탑</option>
									<option value="middle">미드</option>
									<option value="jungle">정글</option>
									<option value="bottom">바텀</option>
									<option value="suppoter">서포터</option>
								</select>
								<input type="text" name="searchTeamName" id="searchTeamName" placeholder="팀 이름 검색">
								<input type="button" value="검색" name="searchTeam" id="searchTeam" onclick="teamSearch()">
							</div>
			    		</div>
			    		<div class="card-block">
			    			<div class="row">
								<div class="col-sm-12 table-responsive" id="searchResultOfTeamName">
								</div>
								<c:if test="${sessionScope.member != null || sessionScope.member == null}">
									<script>
										callTeamList();

										var notexistTeam = "${notexistTeam}";
																				
										if(notexistTeam.length === 0 && "${teamDTOList}".length !== 0){
											document.getElementById("searchResultOfTeamName").innerHTML=
											'<h4>검색 결과</h4>'+
												'<table class=\"table\">'+
													'<thead>'+
														'<tr>'+
															'<th>No</th>'+
															'<th>팀이름</th>'+
															'<th>팀장</th>'+
															'<th>티어 제한</th>'+
															'<th>플레이 가능 시간</th>'+
														'</tr>'+
													'</thead>'+
													'<tbody>'+
														'<c:forEach var="teamDTOList" items="${teamDTOList}" varStatus="status">'+
															'<tr>'+
																'<td>${status.count}</td>'+
																'<td>'+
																	'<a href=\"${pageContext.request.contextPath}/teamdetail.do?teamName=${teamDTOList.teamName}&target=detail\">'+
																		'${teamDTOList.teamName}'+
																	'</a>'+
																'</td>'+
																'<td>${teamDTOList.captinName}</td>'+
																'<td>${teamDTOList.tier_limit}</td>'+
																'<td>${teamDTOList.week_input} ${teamDTOList.startTime} ~ ${teamDTOList.endTime}</td>'+
															'</tr>'+
														'</c:forEach>'+
													'</tbody>'+
												'</table><br><br>';
										}
									</script>
								</c:if>
			    				<div class="col-sm-12 table-responsive" id="printTeamList">
			    				</div>
			    			</div>
			    		</div>
						<input type="button" value="팀 생성" name="createTeam" id="createTeam" onclick="TeamCreate()">
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>