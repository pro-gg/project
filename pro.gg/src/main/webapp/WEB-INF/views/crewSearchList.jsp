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
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function findCrewData(nickname){

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/member/findmemberdata.do?nickname='+nickname,
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
    <c:if test="${notexistCrew != null}">
        <script>
            alert("조건에 해당하는 회원이 존재하지 않습니다.");
        </script>
    </c:if>
    <c:if test="${notexistCrew == null}">
        <table class="table">
            <thead>
                <tr>
                    <th>No</th>
                    <th>닉네임</th>
                    <th>소환사 명</th>
                    <th>티어</th>
                    <th>승률</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="memberDTOList" items="${memberDTOList}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>
                            <a href="#" onclick="findCrewData('${memberDTOList.nickname}')">
                                ${memberDTOList.nickname}
                            </a>
                        </td>
                        <td>${memberDTOList.summoner_name}</td> 
                        <td>${memberDTOList.tier}</td>
                        <td>${memberDTOList.rate}%</td>
                    </tr>                  
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>