<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
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
        #lose{
            background-color:coral;
        }
        #win{
            background-color: deepskyblue;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="mypage-wrapper">
            <form class="mypage">
                <h2>"${summoner.summoner_name}" 검색 결과</h2> <br><br>
                
                <div id="matchList">
                    <% 
                    int imgcount = 0; 
                    int spellcount = 0;
                    int championcount = 0;
                %>
                <c:forEach var="matchDataList" items="${matchDataList}" varStatus="status">
                    <c:if test="${matchDataList.win == false}">
                        <div id="lose">
                            <%
                                String championImage = "championImage"+championcount;
                                String championImagePath = (String)request.getAttribute(championImage);
            
                                String championName = "championName"+championcount;
                                championName = (String)request.getAttribute(championName);
                                championcount++;
                            %>
                            <p>챔피언 : 
                                <img src="<%= championImagePath %>" alt=""><br>
                                <b>&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;<%= championName %></b>
                            </p>
                            
                            <p>K/D/A : <c:out value="${matchDataList.kills}/${matchDataList.deaths}/${matchDataList.assists}"></c:out></p>
                            <p>승패 여부 : <c:out value="패배.."></c:out></p>
                            <p>획득한 골드 : <c:out value="${matchDataList.goldEarned}"></c:out><img src="https://ddragon.leagueoflegends.com/cdn/5.5.1/img/ui/gold.png"></p>
                            <p>소모한 골드 : <c:out value="${matchDataList.goldSpent}"></c:out><img src="https://ddragon.leagueoflegends.com/cdn/5.5.1/img/ui/gold.png"></p>
                            <% 
                                String itemList = "itemlist_List"+imgcount;
                                List<String> itemList_string = (ArrayList)request.getAttribute(itemList);
                                imgcount++;
                            %>
                            <p>최종 아이템 : 
                                <img src="<%= itemList_string.get(0) %>" alt="">
                                <img src="<%= itemList_string.get(1) %> "alt="">
                                <img src="<%= itemList_string.get(2) %>" alt="">
                                <img src="<%= itemList_string.get(3) %>" alt="">
                                <img src="<%= itemList_string.get(4) %>" alt="">
                                <img src="<%= itemList_string.get(5) %>" alt="">
                                <img src="<%= itemList_string.get(6) %>" alt="">
                            </p>
                            <% 
                                String spellList = "spellList_List"+spellcount;
                                List<String> spellList_string = (ArrayList)request.getAttribute(spellList);
                                spellcount++;
                            %>
                            <p>소환사 마법 : 
                                <img src="<%= spellList_string.get(0) %>" alt="">
                                <img src="<%= spellList_string.get(1) %>" alt="">
                            </p>
                            <hr>
                        </div>
                    </c:if>
                    <c:if test="${matchDataList.win == true}">
                        <div id="win">
                            <%
                                String championImage = "championImage"+championcount;
                                String championImagePath = (String)request.getAttribute(championImage);
            
                                String championName = "championName"+championcount;
                                championName = (String)request.getAttribute(championName);
                                championcount++;
                            %>
                            <p>챔피언 : 
                                <img src="<%= championImagePath %>" alt=""><br>
                                <b>&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;<%= championName %></b>
                            </p>
                            <p>K/D/A : <c:out value="${matchDataList.kills}/${matchDataList.deaths}/${matchDataList.assists}"></c:out></p>
                            <p>승패 여부 : <c:out value="승리!"></c:out></p>
                            <p>획득한 골드 : <c:out value="${matchDataList.goldEarned}"></c:out><img src="https://ddragon.leagueoflegends.com/cdn/5.5.1/img/ui/gold.png"></p>
                            <p>소모한 골드 : <c:out value="${matchDataList.goldSpent}"></c:out><img src="https://ddragon.leagueoflegends.com/cdn/5.5.1/img/ui/gold.png"></p>
                            <% 
                                String itemList = "itemlist_List"+imgcount;
                                List<String> itemList_string = (ArrayList)request.getAttribute(itemList);
                                imgcount++;
                            %>
                            <p>최종 아이템 : 
                                <img src="<%= itemList_string.get(0) %>" alt="">
                                <img src="<%= itemList_string.get(1) %> "alt="">
                                <img src="<%= itemList_string.get(2) %>" alt="">
                                <img src="<%= itemList_string.get(3) %>" alt="">
                                <img src="<%= itemList_string.get(4) %>" alt="">
                                <img src="<%= itemList_string.get(5) %>" alt="">
                                <img src="<%= itemList_string.get(6) %>" alt="">
                            </p>
                            <% 
                                String spellList = "spellList_List"+spellcount;
                                List<String> spellList_string = (ArrayList)request.getAttribute(spellList);
                                spellcount++;
                            %>
                            <p>소환사 마법 : 
                                <img src="<%= spellList_string.get(0) %>" alt="">
                                <img src="<%= spellList_string.get(1) %>" alt="">
                            </p>
                            <hr>
                        </div>
                    </c:if>
                        
                </c:forEach>
                </div>

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
            </form>
        </div>
    </article>
</body>
</html>