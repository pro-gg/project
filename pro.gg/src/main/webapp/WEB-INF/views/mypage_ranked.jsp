<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function solo_rank_mypage(){
            $("#ranked_mypage").empty();
            var ranked_solo = "${ranked_solo}";
            var tier = "${ranked_solo.tier}";

            if(tier !== ''){
                document.getElementById("ranked_mypage").innerHTML="<p>솔로 랭크</p>" +
                    "<img src='/resources/images/Emblem_${ranked_solo.tier}.png' style='width:100px; height:100px;'/>" +
                    "<p>LP : ${ranked_solo.leaguePoints}</p>" +
                    "<p>티어 : ${ranked_solo.tier} ${ranked_solo.tier_rank}</p>" +
                    "<p>승 : ${ranked_solo.wins}, 패 : ${ranked_solo.losses}</p>" +
                    "<p>승률 : ${ranked_solo.rate}%</p>";
            }else {
            	document.getElementById("ranked_mypage").innerHTML="<p>솔로 랭크</p>" +
        		"<img src='/resources/images/provisional.png' style='width:100px; height:100px;'/>" +
        		"<p>Unranked</p>";
        	}
        }

        function flex_rank_mypage(){
            $("#ranked_mypage").empty();
            var ranked_flex = "${ranked_flex}";
            var tier = "${ranked_flex.tier}";

            if(tier !== ''){
                document.getElementById("ranked_mypage").innerHTML="<p>자유 랭크</p>" +
                    "<img src='/resources/images/Emblem_${ranked_flex.tier}.png' style='width:100px; height:100px;'/>" +
                    "<p>LP : ${ranked_flex.leaguePoints}</p>" +
                    "<p>티어 : ${ranked_flex.tier} ${ranked_flex.tier_rank}</p>" +
                    "<p>승 : ${ranked_flex.wins}, 패 : ${ranked_flex.losses}</p>" +
                    "<p>승률 : ${ranked_flex.rate}%</p>";
            }else {
            	document.getElementById("ranked_mypage").innerHTML="<p>자유 랭크</p>" +
            		"<img src='/resources/images/provisional.png' style='width:100px; height:100px;'/>" +
            		"<p>Unranked</p>";
            }
        }
    </script>
</head>
<body>
    <a href="#" onclick="solo_rank_mypage()">솔로 랭크</a> <a href="#" onclick="flex_rank_mypage()">자유 랭크</a>
    <div id="ranked_mypage"></div>
</body>
</html>