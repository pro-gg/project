<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function solo_rank_aside(){
            $("#ranked_aside").empty();
            var ranked_solo = "${ranked_solo}";

            if(ranked_solo !== null){
                document.getElementById("ranked_aside").innerHTML="<p>솔로 랭크</p>" +
                    "<p>LP : ${ranked_solo.leaguePoints}</p>" +
                    "<p>티어 : ${ranked_solo.tier} ${ranked_solo.tier_rank}</p>" +
                    "<p>승 : ${ranked_solo.wins}, 패 : ${ranked_solo.losses}</p>" +
                    "<p>승률 : ${ranked_solo.rate}%</p>";
            }
        }

        function flex_rank_aside(){
            $("#ranked_aside").empty();
            var ranked_flex = "${ranked_flex}";

            if(ranked_flex !== null){
                document.getElementById("ranked_aside").innerHTML="<p>자유 랭크</p>" +
                    "<p>LP : ${ranked_flex.leaguePoints}</p>" +
                    "<p>티어 : ${ranked_flex.tier} ${ranked_flex.tier_rank}</p>" +
                    "<p>승 : ${ranked_flex.wins}, 패 : ${ranked_flex.losses}</p>" +
                    "<p>승률 : ${ranked_flex.rate}%</p>";
            }
        }
    </script>
</head>
<body>
    <a href="#" onclick="solo_rank_aside()">솔로 랭크</a> <a href="#" onclick="flex_rank_aside()">자유 랭크</a>
    <div id="ranked_aside"></div>
</body>
</html>