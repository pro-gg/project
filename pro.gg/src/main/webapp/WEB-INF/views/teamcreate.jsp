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
            
        }
    </script>
    <style>
        article, form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: 100px; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <h3>팀 생성</h3>
        <form action="">
            <label for="teamName">팀 이름</label>
            <p><input type="text" id="teamName" name="teamName"></p>
        
            <label for="playtime">플레이 가능 시간</label>
            <p>
                <select>
                    <option value="weekend">주말</option>
                    <option value="weekday">평일</option>
                </select>
                <input type="time" id="startTime">&nbsp;~&nbsp;<input type="time" id="endTime"> 
            </p>

            <label for="tier_limit"> 티어 제한</label>
            <p>
                <select>
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
    </article>
</body>
</html>