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
            }else if("${team.top}".length !== 0){
                $("#top_captin").append("<button onclick=\"crewExile('${team.top}')\">추방</button>");
            }

            if("${team.captinName}" === "${team.middle}"){
                $("#middle").css("color", "blue");
                $("#middle_captin").text("팀장").css("color", "blue");
            }else if("${team.middle}".length !== 0){
                $("#middle_captin").append("<button onclick=\"crewExile('${team.middle}')\">추방</button>");
            }

            if("${team.captinName}" === "${team.jungle}"){
                $("#jungle").css("color", "blue");
                $("#jungle_captin").text("팀장").css("color", "blue");
            }else if("${team.jungle}".length !== 0){
                $("#jungle_captin").append("<button onclick=\"crewExile('${team.jungle}')\">추방</button>");
            }

            if("${team.captinName}" === "${team.bottom}"){
                $("#bottom").css("color", "blue");
                $("#bottom_captin").text("팀장").css("color", "blue");
            }else if("${team.bottom}".length !== 0){
                $("#bottom_captin").append("<button onclick=\"crewExile('${team.bottom}')\">추방</button>");
            }

            if("${team.captinName}" === "${team.suppoter}"){
                $("#suppoter").css("color", "blue");
                $("#suppoter_captin").text("팀장").css("color", "blue");
            }else if("${team.suppoter}".length !== 0){
                $("#suppoter_captin").append("<button onclick=\"crewExile('${team.suppoter}')\">추방</button>");
            }
	    })

        function crewExile(nickname){
            // 팀원 추방을 위한 정보를 컨트롤러에 넘겨줘야 한다.
			// 추방 시키고자 하는 팀원의 닉네임 과 팀 이름을 넘겨준다.
			if(confirm("팀원을 정말 추방 하시겠습니까?") === true){
				var teamName = "${team.teamName}";
				var exile = {
					teamName:teamName,
					nickname:nickname
				}

				$.ajax({
					type:'get',
					url:'${pageContext.request.contextPath}/crewexile.do?exile='+encodeURI(JSON.stringify(exile)),
					data:'',
					dataType:'',
					success:function(data){
						$("body").html(data);
						alert("정상적으로 추방 되었습니다.");
					}
				})
			}
        }

		function teamLineUpdate(){

			var teamName = "${team.teamName}";

			var positionTop = document.getElementById("positionTop");
			var positionMiddle = document.getElementById("positionMiddle");
			var positionJungle = document.getElementById("positionJungle");
			var positionBottom = document.getElementById("positionBottom");
			var positionSuppoter = document.getElementById("positionSuppoter");

			var positionArray = [];
			if("${team.top}".length !== 0){
				positionArray.push(positionTop.options[positionTop.selectedIndex].value);
			}
			if("${team.middle}".length !== 0){
				positionArray.push(positionMiddle.options[positionMiddle.selectedIndex].value);
			}
			if("${team.jungle}".length !== 0){
				positionArray.push(positionJungle.options[positionJungle.selectedIndex].value);
			}
			if("${team.bottom}".length !== 0){
				positionArray.push(positionBottom.options[positionBottom.selectedIndex].value);
			}
			if("${team.suppoter}".length !== 0){
				positionArray.push(positionSuppoter.options[positionSuppoter.selectedIndex].value);
			}
			encodeURI()
			$.ajax({
				type:'get',
				url:'${pageContext.request.contextPath}/teamLineUpdate.do?positionArray='+positionArray+'&teamName='+teamName,
				data:'',
				dataType:'',
				success:function(data){
					$("body").html(data);
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
												<th>소환사 명</th>
			    								<th>티어</th>
			    								<th>승률</th>
												<th>비고</th>
			    							</tr>
			    						</thead>
			    						<tbody>
			    							<tr>
												<c:if test="${team.top != null}">
													<th>
														<select name="position" id="positionTop">
															<option value="top">탑</option>
															<option value="middle">미드</option>
															<option value="jungle">정글</option>
															<option value="bottom">바텀</option>
															<option value="suppoter">서포터</option>
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
												</c:if>
			    							</tr>
											<tr>
												<c:if test="${team.middle != null}">
													<th>
														<select name="position" id="positionMiddle">
															<option value="middle">미드</option>
															<option value="top">탑</option>
															<option value="jungle">정글</option>
															<option value="bottom">바텀</option>
															<option value="suppoter">서포터</option>
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
												</c:if>
											</tr>
											<tr>
												<c:if test="${team.jungle != null}">
													<th>
														<select name="position" id="positionJungle">
															<option value="jungle">정글</option>
															<option value="top">탑</option>
															<option value="middle">미드</option>
															<option value="bottom">바텀</option>
															<option value="suppoter">서포터</option>
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
												</c:if>	
											</tr>
											<tr>
												<c:if test="${team.bottom != null}">
													<th>
														<select name="position" id="positionBottom">
															<option value="bottom">바텀</option>
															<option value="top">탑</option>
															<option value="middle">미드</option>
															<option value="jungle">정글</option>
															<option value="suppoter">서포터</option>
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
												</c:if>
											</tr>
											<tr>
												<c:if test="${team.suppoter != null}">
													<th>
														<select name="position" id="positionSuppoter">
															<option value="suppoter">서포터</option>
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
												</c:if>	
											</tr>
			    						</tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
			    		<button>이전</button> &nbsp;&nbsp;
						<c:if test="${sessionScope.member.nickname == team.captinName}">
							<button onclick="teamLineUpdate()">수정 완료</button> &nbsp;&nbsp;
						</c:if>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</html>