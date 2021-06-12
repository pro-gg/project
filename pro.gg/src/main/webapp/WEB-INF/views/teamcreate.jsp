<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        function team_create(){
            var week_input = document.getElementById('week_input');
            var tier_limit = document.getElementById('tier_limit');
            var captinName = "${sessionScope.member.nickname}";
            var userid = "{sessionScope.member.userid}";

            var teamData = {
                'teamName':document.getElementById('teamName').value,
                'week_input':week_input.options[week_input.selectedIndex].text,
                'startTime':document.getElementById('startTime').value,
                'endTime':document.getElementById('endTime').value,
                'tier_limit':tier_limit.options[tier_limit.selectedIndex].text,
                'team_description' : document.getElementById('team_description').value,

                'captinName' : captinName,
                'userid' : userid
            }

            $(function(){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/createTeam.do?teamData='+encodeURI(JSON.stringify(teamData)),
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
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
    	<div class="team-wrapper">
	        <form class="create-team">
	        	<h3>팀 생성</h3>
	            <label for="teamName">팀 이름</label>
	            <p><input type="text" id="teamName" name="teamName" maxlength="12"></p>
	        
	            <label for="playtime">플레이 가능 시간</label>
	            <p>
	                <select name="week_input" id="week_input">
	                    <option value="weekend">주말</option>
	                    <option value="weekday">평일</option>
	                </select>
	                <input type="time" id="startTime">&nbsp;~&nbsp;<input type="time" id="endTime"> 
	            </p>
	
	            <label for="tier_limit"> 티어 제한</label>
	            <p>
	                <select name="tier_limit" id="tier_limit">
	                    <option value="iron">IRON</option>
	                    <option value="bronze">BRONZE</option>
	                    <option value="silver">SILVER</option>
	                    <option value="gold">GOLD</option>
	                    <option value="PLATINUM">PLATINUM</option>
	                    <option value="diamond">DIAMOND</option>
	                    <option value="master">MASTER</option>
	                    <option value="grand_master">GRANDMASTER</option>
	                    <option value="challenger">CHALLENGER</option>
	                </select>
	            </p>
	            
	            <label for="team_description">팀 설명</label>
	            <p>
	                <textarea name="team_description" id="team_description" cols="40" rows="5"></textarea>
	            </p>
	
	            <p>
	                <input type="button" value="생성하기" id="teamCreate" onclick="team_create()">
	            </p>
	        </form>
		</div>
    </article>
</body>
</html>