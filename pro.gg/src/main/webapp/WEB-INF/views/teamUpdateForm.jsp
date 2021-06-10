<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
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
												<th>소환사 명</th>
			    								<th>티어</th>
			    								<th>승률</th>
												<th>비고</th>
			    							</tr>
			    						</thead>
			    						<tbody>
			    							<tr>
												<th>
													<select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select>	
												</th> 
			    								<td id="top">${team.top}</td>
												<td>
													<c:if test="${team_top != null}">${team_top}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_top != null}">${soloData_top.tier} ${soloData_top.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_top.rate != 0}">${soloData_top.rate}%</c:if>
												</td>
												<td id="top_captin"></td>
			    							</tr>
											<tr>
												<th>
													<select name="position" id="position">
														<option value="middle">미드</option>
                                                        <option value="top">탑</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select>
												</th>
			    								<td id="middle">${team.middle}</td>
												<td>
													<c:if test="${team_middle != null}">${team_middle}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_middle != null}">${soloData_middle.tier} ${soloData_middle.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_middle.rate != 0}">${soloData_middle.rate}%</c:if>
												</td>
												<td id="middle_captin"></td>
											</tr>
											<tr>
												<th>
													<select name="position" id="position">
                                                        <option value="jungle">정글</option>
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select>
												</th>
			    								<td id="jungle">${team.jungle}</td>
												<td>
													<c:if test="${team_jungle != null}">${team_jungle}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_jungle != null}">${soloData_jungle.tier} ${soloData_jungle.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_jungle.rate != 0}">${soloData_jungle.rate}%</c:if>
												</td>
												<td id="jungle_captin"></td>
											</tr>
											<tr>
												<th>
													<select name="position" id="position">
                                                        <option value="bottom">바텀</option>
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="support">서포터</option>
													</select>
												</th>
			    								<td id="bottom">${team.bottom}</td>
												<td>
													<c:if test="${team_bottom != null}">${team_bottom}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_bottom != null}">${soloData_bottom.tier} ${soloData_bottom.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_bottom.rate != 0}">${soloData_bottom.rate}%</c:if>
												</td>
												<td id="bottom_captin"></td>
											</tr>
											<tr>
												<th>
													<select name="position" id="position">
                                                        <option value="support">서포터</option>
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
													</select>
												</th>
			    								<td id="suppoter">${team.suppoter}</td>
												<td>
													<c:if test="${team_suppoter != null}">${team_suppoter}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_suppoter != null}">${soloData_suppoter.tier} ${soloData_suppoter.tier_rank}</c:if>
												</td>
			    								<td>
													<c:if test="${soloData_suppoter.rate != 0}">${soloData_suppoter.rate}%</c:if>
												</td>
												<td id="suppoter_captin"></td>
											</tr>
			    						</tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
			    		<button>이전</button> &nbsp;&nbsp;
						<c:if test="${sessionScope.member.nickname == team.captinName}">
							<button onclick="teamUpdate()">수정</button> &nbsp;&nbsp;
						</c:if>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</html>