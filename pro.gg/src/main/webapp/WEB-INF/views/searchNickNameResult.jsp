<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function solo_rank_nickname(){
            var ranked_solo = "${ranked_solo}";
            var tier = "${ranked_solo.tier}";

            if(tier !== ''){
                document.getElementById("rankedSoloNickname").innerHTML="<dt><b>솔로 랭크</b></dt>" +
                	"<dd><img src='/pro.gg/resources/images/Emblem_${ranked_solo.tier}.png' style='width:100px; height:100px;'/></dd>" +
                    "<dd>LP : ${ranked_solo.leaguePoints}p</dd>" +
                    "<dd>티어 : ${ranked_solo.tier} ${ranked_solo.tier_rank}</dd>" +
                    "<dd>${ranked_solo.wins}승 ${ranked_solo.losses}패</dd>" +
                    "<dd>승률 : ${ranked_solo.rate}%</dd>";
            }else {
            	document.getElementById("rankedSoloNickname").innerHTML="<dt><b>솔로 랭크</b></dt>" +
        		"<dd><img src='/pro.gg/resources/images/provisional.png' style='width:100px; height:100px;'/></dd>" +
        		"<dd>Unranked</dd>";
        	}
        }

        function flex_rank_nickname(){
            $("#ranked_aside").empty();
            var ranked_flex = "${ranked_flex}";
            var tier = "${ranked_flex.tier}";

            if(tier !== ''){
                document.getElementById("rankedFlexNickname").innerHTML="<dt><b>자유 랭크</b></dt>" +
                	"<dd><img src='/pro.gg/resources/images/Emblem_${ranked_flex.tier}.png' style='width:100px; height:100px;'/></dd>" +
                    "<dd>LP : ${ranked_flex.leaguePoints}p</dd>" +
                    "<dd>티어 : ${ranked_flex.tier} ${ranked_flex.tier_rank}</dd>" +
                    "<dd>${ranked_flex.wins}승 ${ranked_flex.losses}패</dd>" +
                    "<dd>승률 : ${ranked_flex.rate}%</dd>";
            }else {
            	document.getElementById("rankedFlexNickname").innerHTML="<dt><b>자유 랭크</b></dt>" +
            		"<dd><img src='/pro.gg/resources/images/provisional.png' style='width:100px; height:100px;'/></dd>" +
            		"<dd>Unranked</dd>";
            }
        }

        function updateSummonerData(){
            $(function(){
                var updateSummoner = "updateSummonerData";
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/updateSummonerData.do?target='+updateSummoner,
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("body").html(data);
                    }
                })
            })
        }

        function callMatchList(){
            var nickname = "${searchNickName.nickname}";
            var summoner_name = "${summoner.summoner_name}";

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/searchMatchList.do?nickname='+nickname+"&summoner_name="+summoner_name,
                data:'',
                dataType:'',
                success:function(data){
                    $("#matchList").html(data);
                }
            })
        }
    </script>
    <style>
        dl{
            background-color: antiquewhite;
            width: 300px;
        }
        #matchList{
            float: right;
            margin-right: 200px;
            width: 600px;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="mypage-wrapper">
            <form class="mypage">
                <h2>"${searchNickName.nickname}" 검색 결과</h2> <br><br>
                <label for="nickname">닉네임 : </label>
                <em name="nickname" id="nickname">${searchNickName.nickname}</em> <br>
                
                <script>callMatchList()</script>
                <div id="matchList"></div>

                <label for="summonerName">소환사 명 : </label>
                <em name="summonerName" id="summonerName">${summoner.summoner_name}</em> <br><br>
                <dl>
                    <script>solo_rank_nickname()</script>
                    <div id="rankedSoloNickname"></div>
                </dl>
                <dl>
                    <script>flex_rank_nickname()</script>
                    <div id="rankedFlexNickname"></div>
                </dl>
                <button onclick="updateSummonerData()">랭크 정보갱신</button>
            </form>
        </div>
    </article>
</body>
</html>