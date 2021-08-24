<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/pro.gg/resources/css/mystyle.css"/>
    <title></title>
    <script>
        function teamLineApply(){
            if(confirm("신청 하시겠습니까?") === true){
                if($(':radio[name="line"]:checked').length < 1){
                    alert('지원 할 라인을 선택 해주세요.');
                }
                else{
                    var teamName = "${team.teamName}";
                    var tier_limit = "${team.tier_limit}";
                    var line = $('input[name="line"]:checked').val();

                    var teamapply = {
                        teamName:teamName,
                        tier_limit:tier_limit,
                        line:line
                    }

                    $.ajax({
                        type:'post',
                        url:'${pageContext.request.contextPath}/teamapply.do?teamapply='+encodeURI(JSON.stringify(teamapply)),
                        data:'',
                        dataType:'',
                        success:function(data){
                            $("body").html(data);
                        }
                    })
                }
            }
        }
    </script>
    <style>
        input[type=radio]{
            margin-left: 160px;
        }
        #line-radio{
            background-color:antiquewhite;
        }
        #applyButton{
            float: right;
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="card-block">
            <div id="line-radio">
                <input type="radio" id="top" name="line" value="top">탑
                <input type="radio" id="middle" name="line" value="middle">미드
                <input type="radio" id="jungle" name="line" value="jungle">정글
                <input type="radio" id="bottom" name="line" value="bottom">바텀
                <input type="radio" id="suppoter" name="line" value="suppoter">서포터
            </div>
            <button onclick="teamLineApply()" id="applyButton">신청하기</button>
        </div>
    </div>
</body>
</html>