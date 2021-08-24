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
    <script>
        function updatePasswd(){
            $(function(){
                var currentPasswd = document.getElementById("currentPasswd").value;

                if(currentPasswd === "${sessionScope.member.passwd}"){
                    var userid = {
                        'userid' : '${sessionScope.member.userid}'
                    }

                    if("${change}" === "change"){     
                        $.ajax({
                            type:'post',
                            url:'${pageContext.request.contextPath}/findPasswdSuccess.do?userid='+encodeURI(JSON.stringify(userid)),
                            data:'',
                            dataType:'',
                            success:function(data){
                                $("body").html(data);
                            }
                        })
                    } else if("${secession}" === "secession"){
                        $.ajax({
                            type:'post',
                            url:'${pageContext.request.contextPath}/memberSecession.do',
                            data:'',
                            dataType:'',
                            success:function(data){
                                alert("정상적으로 탈퇴 되었습니다.");
                                opener.location.href='${pageContext.request.contextPath}/';
                                window.close();
                            }
                        })
                    }
                }  
                else{
                    alert("현재 비밀번호가 일치하지 않습니다.")
                }
            })
        }
    </script>
    <style>
        article, div{
            position:absolute;
            left: 0; right: 0;
            margin-left: 60px; margin-right:0;
            top: 50px; bottom: auto;
            margin-top: auto; margin-bottom: auto;
        }
    </style>
</head>
<body>
    <article>
        <div>
            <p>현재 비밀번호 입력 : <input type="password" name="currentPasswd" id="currentPasswd"></p>
            <input type="button" value="확인" onclick="updatePasswd()">
            
        </div>
    </article>
</body>
</html>