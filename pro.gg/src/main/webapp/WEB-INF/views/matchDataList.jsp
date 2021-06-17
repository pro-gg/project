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
    <style>
        #lose{
            background-color:coral;
        }
        #win{
            background-color: deepskyblue;
        }
    </style>
</head>
<body>
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
</body>
</html>