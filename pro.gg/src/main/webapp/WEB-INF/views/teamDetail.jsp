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

	$(function(){
		if("${team.captinName}" === "${team.top}"){
            $("#top").css("color", "blue");
            $("#top_captin").text("팀장").css("color", "blue");
        }else if("${team.top}".length !== 0){
            $("#top_captin").text("팀원");
        }

        if("${team.captinName}" === "${team.middle}"){
            $("#middle").css("color", "blue");
            $("#middle_captin").text("팀장").css("color", "blue");
        }else if("${team.middle}".length !== 0){
            $("#middle_captin").text("팀원");
        }

        if("${team.captinName}" === "${team.jungle}"){
            $("#jungle").css("color", "blue");
            $("#jungle_captin").text("팀장").css("color", "blue");
        }else if("${team.jungle}".length !== 0){
            $("#jungle_captin").text("팀원");
        }

        if("${team.captinName}" === "${team.bottom}"){
            $("#bottom").css("color", "blue");
            $("#bottom_captin").text("팀장").css("color", "blue");
        }else if("${team.bottom}".length !== 0){
            $("#bottom_captin").text("팀원");
        }

        if("${team.captinName}" === "${team.suppoter}"){
            $("#suppoter").css("color", "blue");
            $("#suppoter_captin").text("팀장").css("color", "blue");
        }else if("${team.suppoter}".length !== 0){
            $("#suppoter_captin").text("팀원");
        }

		if("${overlap}" !== ""){
			alert("라인이 중복 지정 되었습니다. 다시 수정 해주세요");
		}
	})
	function teamApply(){
		$(function(){
			var teamName = "${team.teamName}";
			var tier_limit = "${team.tier_limit}";
			var sessionCheck = "${sessionScope.member}";
			var belongTeamName = "";
			if(sessionCheck == null){
			    alert("로그인이 필요한 서비스 입니다.")
			}
			else{
			    belongTeamName = "${sessionScope.member.teamName}";

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
			}
		})
	}

	function teamUpdate(){
		$(function(){
			if(confirm("수정 하시겠습니까?") === true){
				var teamName = "${team.teamName}";

				$.ajax({
					type:'get',
					url:'${pageContext.request.contextPath}/team/teamdetail.do?teamName='+teamName+'&target=update',
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data);
					}
				})
			}
		})
	}

	function teamSecession(){
		var member = "${sessionScope.member.nickname}";
		var captinName = "${team.captinName}";
		var teamName = "${team.teamName}";

		if(confirm("팀에서 탈퇴 하시겠습니까?") === true){
			if(member === captinName && confirm("팀장이 탈퇴하면 팀이 완전히 해체됩니다. 그래도 탈퇴 하시겠습니까?") === true){
				// 팀장이 탈퇴하는 경우 소속되어 있는 팀장을 포함한 모든 팀원들이 탈퇴된 후 팀이 삭제되는 방식으로 로직이 수행된다.
				// 원활한 팀 삭제 작업을 위해 연관관계로 매핑되어 있는 데이터들을 null 값으로 세팅하여 연관관계를 제거해준다.
				// 팀 삭제 로직은 팀장의 탈퇴 요청시 수행되는 것으로 구현한다.
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/team/captinsecession.do?teamName='+teamName,
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data);
					}
				})
			}
			else{
				// 일반 팀원이 탈퇴하는 경우 팀의 해제 없이 해당 팀원만 연관관계 매핑 해제 후 탈퇴 되는 것으로 구현한다.
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/team/crewsecession.do?teamName='+teamName+'&target=secession',
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data);
					}
				})
			}
		}
	}

	function applyStatusView(){
		var teamName = "${team.teamName}";

		$.ajax({
			type:'get',
			url:'${pageContext.request.contextPath}/team/applyStatusView.do?teamName='+teamName,
			data:'',
			dataType:'',
			success:function(data){
				$("#applyInMember").html(data);
			}
		})
	}

	function backPage(){
	    location.replace("${pageContext.request.contextPath}/move/searchTeamName.do")
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
			    			<h5 class="card-header-text">${team.teamName}(${tier })</h5>
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
												<th>소환사 명</th>
			    								<th>티어</th>
			    								<th>승률</th>
												<th>비고</th>
			    							</tr>
			    						</thead>
			    						<tbody>
			    							<tr>
												<th>
													탑
												</th> 
			    								<td id="top">${team.top}</td>
												<td>
													<c:if test="${team_top != null}">${team_top}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_top != null}">${soloData_top.tier} ${soloData_top.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_top != null}">${soloData_top.rate}%</c:if>
												</td>
												<td id="top_captin"></td>
			    							</tr>
											<tr>
												<th>
													미드
												</th>
			    								<td id="middle">${team.middle}</td>
												<td>
													<c:if test="${team_middle != null}">${team_middle}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_middle != null}">${soloData_middle.tier} ${soloData_middle.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_middle != null}">${soloData_middle.rate}%</c:if>
												</td>
												<td id="middle_captin"></td>
											</tr>
											<tr>
												<th>
													정글
												</th>
			    								<td id="jungle">${team.jungle}</td>
												<td>
													<c:if test="${team_jungle != null}">${team_jungle}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_jungle != null}">${soloData_jungle.tier} ${soloData_jungle.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_jungle != null}">${soloData_jungle.rate}%</c:if>
												</td>
												<td id="jungle_captin"></td>
											</tr>
											<tr>
												<th>
													바텀
												</th>
			    								<td id="bottom">${team.bottom}</td>
												<td>
													<c:if test="${team_bottom != null}">${team_bottom}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_bottom != null}">${soloData_bottom.tier} ${soloData_bottom.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_bottom != null}">${soloData_bottom.rate}%</c:if>
												</td>
												<td id="bottom_captin"></td>
											</tr>
											<tr>
												<th>
													서포터
												</th>
			    								<td id="suppoter">${team.suppoter}</td>
												<td>
													<c:if test="${team_suppoter != null}">${team_suppoter}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_suppoter != null}">${soloData_suppoter.tier} ${soloData_suppoter.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_suppoter != null}">${soloData_suppoter.rate}%</c:if>
												</td>
												<td id="suppoter_captin"></td>
											</tr>
			    						</tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
			    		<c:if test="${sessionScope.member != null}">
			    		    <c:if test="${sessionScope.member.nickname != team.top && sessionScope.member.nickname != team.middle && sessionScope.member.nickname != team.jungle && sessionScope.member.nickname != team.bottom && sessionScope.member.nickname != team.suppoter}">
                            	<button onclick="teamApply()">신청</button> &nbsp;&nbsp;
                            </c:if>
                            <c:if test="${sessionScope.member.nickname == team.captinName}">
                                <button onclick="teamUpdate()">수정</button> &nbsp;&nbsp;
                            </c:if>
                            <c:if test="${sessionScope.member.nickname == team.top || sessionScope.member.nickname == team.middle || sessionScope.member.nickname == team.jungle || sessionScope.member.nickname == team.bottom || sessionScope.member.nickname == team.suppoter}">
                                <button onclick="teamSecession()">탈퇴</button>&nbsp;&nbsp;
                            </c:if>
                            <c:if test="${sessionScope.member.nickname == team.captinName}">
                            	<button onclick="applyStatusView()">신청 현황</button>&nbsp;&nbsp;
                            </c:if>
			    		</c:if>
			    		<button onclick="backPage()">이전</button> &nbsp;&nbsp;
						<div id="choose_line"></div>
						<div id="applyInMember"></div>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>