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

        function check_pw(){
            if(document.getElementById('newPasswd').value === '') {
                document.getElementById('check').innerHTML='';
                document.getElementById('input_passwd').innerHTML='비밀번호를 입력하십시오.';
                document.getElementById('input_passwd').style.color='red';
            }
            else if(document.getElementById('newPasswdCheck').value == ''){
                document.getElementById('input_passwd').innerHTML='';
                document.getElementById('check').innerHTML='비밀번호를 한번 더 입력해주세요.';
                document.getElementById('check').style.color='blue';
            } else{
                if( document.getElementById('newPasswd').value === document.getElementById('newPasswdCheck').value) {
                    document.getElementById('check').innerHTML='비밀번호가 일치합니다.';
                    document.getElementById('check').style.color='blue';
                    var btn = document.getElementById('btn');
                    btn.disabled=false;

                } else{
                    document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
                    document.getElementById('check').style.color='red';
                }
            }
        }

        function updatePasswd(){
            var updatePasswd = {
                'updatePasswd' : document.getElementById('newPasswd').value,
                'userid' : '${member_userid}'
            }
            $(function(){
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/updatePasswd.do?updatePasswd='+encodeURI(JSON.stringify(updatePasswd)),
                    data:'',
                    dataType:'',
                    success:function(data){
                        alert("비밀번호가 변경되었습니다, 다시 한번 로그인 해주세요");
                        opener.location.href='${pageContext.request.contextPath}/';
                        window.close();
                    }
                })
            })
        }
    </script>
</head>
<body>
    <article>
        <div>
            <c:if test="${member_userid != null}">
                <h3>비밀번호 변경</h3>
                새 비밀번호 입력 : <input type="password" name="newPasswd" id="newPasswd" onchange="check_pw()"> <span id="input_passwd"></span><br>
                새 비밀번호 입력 확인 : <input type="password" name="newPasswdCheck" id="newPasswdCheck" onchange="check_pw()"> <span id="check"></span><br>
                <input type="button" value="확인" name="btn" id="btn" disabled="disabled" onclick="updatePasswd()">
            </c:if>
        </div>
    </article>
</body>
</html>