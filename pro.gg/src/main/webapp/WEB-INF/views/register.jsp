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
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script>
	    $(function(){ 		
			$('#id').blur(function(){
				var form = document.formReg;
		 		var id = form.id.value;
		 		var idPattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/; //영문 시작, 영문과 숫자포함한 4~20자리
		 		
		 		if(id==''){
		 			$('#input_id').text("아이디를 입력해주세요");
					$('#input_id').css("color","red");
		 		}else if(idPattern.test(id) == true){
		 			var param = "id="+id;
	 	 			check_id(param);
		 		}else{
		 			$('#input_id').text("아이디형식이 잘못되었습니다");
					$('#input_id').css("color","red");
		 		}
	 	 	});
	 	});
		
	    function check_id(id){
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/check_id.do",
				data: id,
				dataType: "text",
				success: function(data){
					if(data == 'OK'){
						$('#input_id').text("사용 가능한 아이디입니다.");
						$('#input_id').css("color","blue");
						$('#btnRegister').attr('disabled',false);
					}else if(data=='Fail'){
						$('#input_id').text("중복된 아이디입니다.");
						$('#input_id').css("color","red");
						$('#btnRegister').attr('disabled',true);
					}
				},
				error: function(){
					console.log('에러');
				}
			})
		}
	    function check_pw(){
        	//숫자, 특문 각 1회 이상, 영문은 2개 이상 사용하여 8자리 이상 입력
        	var pwPattern = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
            if(document.getElementById('passwd').value === '') {
                document.getElementById('check').innerHTML='';
                document.getElementById('input_passwd').innerHTML='비밀번호를 입력하십시오.';
                document.getElementById('input_passwd').style.color='red';
                $('#btnRegister').attr('disabled',true);
            }else if(pwPattern.test(document.getElementById('passwd').value) == false){
            	document.getElementById('input_passwd').innerHTML='';
            	document.getElementById('input_passwd').innerHTML='8자리이상 영문,숫자,특수문자 조합해주세요';
                document.getElementById('input_passwd').style.color='red';
                $('#btnRegister').attr('disabled',true);
            }
            else if(pwPattern.test(document.getElementById('passwd').value) == true && document.getElementById('passwdcheck').value == ''){
                document.getElementById('input_passwd').innerHTML='';
                document.getElementById('check').innerHTML='비밀번호를 한번 더 입력해주세요.';
                document.getElementById('check').style.color='blue';
                $('#btnRegister').attr('disabled',true);
            } else{
                if( document.getElementById('passwd').value === document.getElementById('passwdcheck').value) {
                    document.getElementById('check').innerHTML='비밀번호가 일치합니다.';
                    document.getElementById('check').style.color='blue';
                    $('#btnRegister').attr('disabled',false);
                } else{
                    document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
                    document.getElementById('check').style.color='red';
                    $('#btnRegister').attr('disabled',true);
                }
            }
        }
        function check_email(){
        	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        	var email = document.getElementById('email').value;
        	
        	if(email==''){
        		$('#input_email').text("이메일을 입력해주세요");
				$('#input_email').css("color","red");
				$('#btnRegister').attr('disabled',true);
        	}else{
        		if(emailPattern.test(email)==true){
        			$('#input_email').text("");
        			$('#btnRegister').attr('disabled',false);
        		}else{
        			$('#input_email').text("잘못된 형식입니다.");
        			$('#input_email').css("color","red");
        			$('#btnRegister').attr('disabled',true);
        		}
        	}
        }

    </script>
    <style>
        aside{
            height: 920px;
            width: 300px;
            float: left;
            line-height: 30px;
            background-color: aqua;
        }
        article,form{
            position:absolute;
            width: 920px; height: 50px;
            left: 0; right: 0;
            margin-left: 300px; margin-right:auto;
            top: 0; bottom: 0;
            margin-top: 100px; margin-bottom: auto;
        }
        input[type="text"], input[type="password"]{
            width: 300px;
        }

        #btn{
            width: 150px;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
         <form name="formReg" action="${pageContext.request.contextPath}/tryregister.do" method="POST">
            <h2>회원가입</h2>
            <input type="text" placeholder="이름" name="name" id="name"> <br><br>
            <input type="text" placeholder="닉네임" name="nickname" id="nickname"> <br><br>
            <input type="text" placeholder="아이디" name="id" id="id"><span id="input_id"></span> <br><br>
            <input type="password" placeholder="비밀번호" name="passwd" id="passwd" onchange="check_pw()"><span id="input_passwd"></span><br><br>
            <input type="password" placeholder="비밀번호 확인" name="passwdcheck" id="passwdcheck" onchange="check_pw()"><span id="check"></span><br><br>
            <input type="text" placeholder="이메일" name="email" id="email" onchange="check_email()"><span id="input_email"></span> <br><br>
            <input type="button" value="취소" class="btn" onclick="location.href='${pageContext.request.contextPath}/'">
            <input type="submit" value="가입하기" class="btn" id="btnRegister">
        </form>
    </article>
    
</body>
</html>