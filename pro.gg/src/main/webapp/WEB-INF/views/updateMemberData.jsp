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
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        $(function(){
            if(document.getElementById('nickname_update').value !== '' && document.getElementById("nam_update").value !== '' && document.getElementById("email_update").value !== ''){
                var btn = document.getElementById('btn');
                btn.disabled=false;
            }
        })

        function check_email(){
        	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        	var email = document.getElementById('email_update').value;
        	
        	if(email==''){
        		$('#input_email').text("이메일을 입력해주세요");
				$('#input_email').css("color","red");
				$('#btn').attr('disabled',true);
        	}else{
        		if(emailPattern.test(email)==true){
        			$('#input_email').text("");
        			$('#btn').attr('disabled',false);
        		}else{
        			$('#input_email').text("잘못된 형식입니다.");
        			$('#input_email').css("color","red");
        			$('#btn').attr('disabled',true);
        		}
        	}
        }

        function updateMemberData(){
            var updateMember = {
                'nickname':document.getElementById('nickname_update').value,
                'name':document.getElementById('name_update').value,
                'email':document.getElementById('email_update').value
            }

            $.ajax({
                type:'put',
                url:'${pageContext.request.contextPath}/member/updateMemberData.do?updateMember='+encodeURI(JSON.stringify(updateMember)),
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data)
                }
            })
        }

    </script>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
    	<div class="mypage-wrapper">
	    	<form class="mypage">
	            <h3>회원 정보 수정</h3>
	            <p>닉네임 : <input type="text" name="nickname" id="nickname_update"></p>
	            <p>이름 : <input type="text" name="name" id="name_update"></p>
	            <p>이메일 : <input type="email" name="email" id="email_update" onchange="check_email()"></p>
	            <p><input type="button" id="btn" value="수정하기" onclick="updateMemberData()" disabled="disabled"></p>
	        </form>
    	</div>
    </article>
</body>
</html>