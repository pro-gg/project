<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/js/semantic_aside.js" charset="utf-8"></script>
<script src="/js/semantic_header.js" charset="utf-8"></script>
<script>

	$(function(){
		if("${team.captinName}" === "${team.top}"){
			$("#top").css("color", "blue");
			$("#top_captin").text("팀장").css("color", "blue");
		}
		else if("${team.captinName}" === "${team.middle}"){
			$("#middle").css("color", "blue");
			$("#middle_captin").text("팀장").css("color", "blue");
		}
		else if("${team.captinName}" === "${team.jungle}"){
			$("#jungle").css("color", "blue");
			$("#jungle_captin").text("팀장").css("color", "blue");
		}
		else if("${team.captinName}" === "${team.bottom}"){
			$("#bottom").css("color", "blue");
			$("#bottom_captin").text("팀장").css("color", "blue");
		}
		else if("${team.captinName}" === "${team.suppoter}"){
			$("#suppoter").css("color", "blue");
			$("#suppoter_captin").text("팀장").css("color", "blue");
		}
	})
	function teamApply(){
		$(function(){
			var teamName = "${team.teamName}";
			var tier_limit = "${team.tier_limit}";
			var belongTeamName = "${sessionScope.member.teamName}";

			if(belongTeamName.length !== 0){
				alert("이미 소속되어 있는 팀이 있습니다.")
			} else{
				var team = {
					teamName:teamName,
					tier_limit:tier_limit
				};

				$.ajax({
					type:'get',
					url:'${pageContext.request.contextPath}/move/teamapplyForm.do?team='+encodeURI(JSON.stringify(team)),
					data:'',
					dataType:'',
					success:function(data){
						$("#choose_line").html(data);
					}
				})
			}			
		})
	}

	function teamUpdate(){
		$(function(){
			if(confirm("수정 하시겠습니까?") === true){
				$.ajax({
					type:'get',
					url:'${pageContext.request.contextPath}/teamUpdate.do',
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data)
					}
				})
			}
		})
	}

	function teamSecession(){
		if(confirm("탈퇴 하시겠습니까?") === true){
			
		}
	}

	function applyStatusView(){
		var teamName = "${team.teamName}";

		$.ajax({
			type:'get',
			url:'${pageContext.request.contextPath}/applyStatusView.do?teamName='+teamName,
			data:'',
			dataType:'',
			success:function(data){
				$("#applyInMember").html(data);
			}
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
			    			<h5 class="card-header-text">${team.teamName}</h5>
			    			<p>${team.team_description}</p>
			    		</div>
			    		<div class="card-block">
			    			<div class="row">
			    				<div class="col-sm-12 table-responsive">
			    					<table class="table">
			    						<thead>
			    							<tr>
												<th>포지션</th>
			    								<th>닉네임</th>
			    								<th>티어</th>
			    								<th>승률</th>
												<th>비고</th>
			    							</tr>
			    						</thead>
			    						<tbody>
			    							<tr>
												<th>
													<!-- <select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select> -->
													탑
												</th> 
			    								<td id="top">${team.top}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td id="top_captin"></td>
			    							</tr>
											<tr>
												<th>
													<!-- <select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select> -->
													미드
												</th>
			    								<td id="middle">${team.middle}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td id="middle_captin"></td>
											</tr>
											<tr>
												<th>
													<!-- <select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select> -->
													정글
												</th>
			    								<td id="jungle">${team.jungle}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td id="jungle_captin"></td>
											</tr>
											<tr>
												<th>
													<!-- <select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select> -->
													바텀
												</th>
			    								<td id="bottom">${team.bottom}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td id="bottom_captin"></td>
											</tr>
											<tr>
												<th>
													<!-- <select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select> -->
													서포터
												</th>
			    								<td id="suppoter">${team.suppoter}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td id="suppoter_captin"></td>
											</tr>
			    						</tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
						<c:if test="${sessionScope.member.nickname != team.top &&
							sessionScope.member.nickname != team.middle &&
							sessionScope.member.nickname != team.jungle &&
							sessionScope.member.nickname != team.bottom &&
							sessionScope.member.nickname != team.suppoter}">
							<button onclick="teamApply()">신청</button> &nbsp;&nbsp;
						</c:if>
			    		<button>이전</button> &nbsp;&nbsp;
						<c:if test="${sessionScope.member.nickname == team.captinName}">
							<button onclick="teamUpdate()">수정</button> &nbsp;&nbsp;
						</c:if>
						<c:if test="${sessionScope.member != null}">
							<c:if test="${sessionScope.member.nickname == team.top ||
								sessionScope.member.nickname == team.middle ||
								sessionScope.member.nickname == team.jungle ||
								sessionScope.member.nickname == team.bottom ||
								sessionScope.member.nickname == team.suppoter}">
								<button onclick="teamSecession()">탈퇴</button>&nbsp;&nbsp;
							</c:if>
						</c:if>
						<c:if test="${sessionScope.member.nickname == team.captinName}">
							<a href="#" onclick="applyStatusView()">신청 현황</a>&nbsp;&nbsp;
						</c:if>
						<div id="choose_line"></div>
						<div id="applyInMember"></div>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>