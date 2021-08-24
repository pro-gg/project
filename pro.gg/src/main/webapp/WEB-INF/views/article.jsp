<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/mystyle.css"/>
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function findMemberData(){
            var nickname = document.getElementById("searchNickname").value;

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/findmemberdata.do?nickname='+nickname,
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
	<div class="content-wrapper">
		<div class="back-img text-center">
			<img src="/pro.gg/resources/images/progg2.png" id="main-img" alt="로고">
			<div class="searchbar">
				<input type="text" autocomplete="off" name="searchNickName" id="searchNickname" placeholder="검색어를 입력하세요">
				<button type="submit" value="검색" onclick="findMemberData()">
					<img src="/pro.gg/resources/images/icon_search.png">
				</button>
			</div>
        <!-- 닉네임을 검색하면, 해당 회원에게 등록되어 있는 소환사 정보와 최근 전적을 조회해 볼 수 있다.(삽입이 아닌 조회)-->
        <!-- 마이페이지 에서 자신의 최근 전적 갱신 버튼을 누르면 최근 전적 데이터를 긁어와서 데이터베이스에 삽입-->
        <!-- 다른 사용자의 전적을 보려고 할 경우 데이터베이스 삽입이 아닌, 데이터베이스 조회를 하는 방식으로 기능을 구현한다.-->
		</div>        
    </div>
</body>
</html>