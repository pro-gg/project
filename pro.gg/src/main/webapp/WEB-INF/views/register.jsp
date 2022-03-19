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
				url: "${pageContext.request.contextPath}/member/check_id.do",
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

		function check_nickname(){
			var nickname = document.getElementById('nickname').value;
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/member/check_nickname.do?nickname="+nickname,
				data:'',
				dataType:'',
				success:function(data){
					if(data == 'OK'){
						$('#input_nickname').text("사용 가능한 닉네임 입니다.");
						$('#input_nickname').css("color","blue");
						$('#btnRegister').attr('disabled',false);
					} else if(data == 'Fail'){
						$('#input_nickname').text("중복된 닉네임 입니다.");
						$('#input_nickname').css("color","red");
						$('#btnRegister').attr('disabled',true);
					}
				},
				error:function(){
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
</head>
<body class="back-gray">
    <header></header>
    <aside></aside>
    <article>
    	<div class="content-wrapper">
		    <div class="container-fluid">
		    	<div class="reg-card card-block">
			         <form class="md-float-material" name="formReg" action="${pageContext.request.contextPath}/member/tryregister.do" method="POST">
			         	<div class="text-center">
			         		<h3>회원가입</h3>
			         	</div>
			            <div class="row">
			            	<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="text" name="name" id="name" class="md-form-control" required="required" autocomplete='off'>
		    						<label>이름</label>
	    						</div>
	    					</div>
	    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="text" name="nickname" id="nickname" class="md-form-control" required="required"  autocomplete='off' onchange="check_nickname()">
		    						<label>닉네임</label>
									<span id="input_nickname"></span>
	    						</div>
	    					</div>	
	    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="text" name="id" id="id" class="md-form-control" required="required" autocomplete='off'>
		    						<label>아이디</label>
		    						<span id="input_id"></span>
	    						</div>
	    					</div>	
	    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="password" name="passwd" id="passwd" class="md-form-control" required="required" autocomplete='off' onchange="check_pw()">
		    						<label>비밀번호</label>
		    						<span id="input_passwd"></span>
	    						</div>
	    					</div>	
	    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="password" name="passwdcheck" id="passwdcheck" class="md-form-control" required="required" autocomplete='off' onchange="check_pw()">
		    						<label>비밀번호 확인</label>
		    						<span id="check"></span>
	    						</div>
	    					</div>	
	    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    						<input type="text" name="email" id="email" class="md-form-control" required="required" autocomplete='off' onchange="check_email()">
		    						<label>이메일</label>
		    						<span id="input_email"></span>
	    						</div>
	    					</div>			
			            </div>
			            <br>
			            <div class="row">
	    					<div class="reg-btn-row">
	    						<div class="text-center">
			            			<input type="submit" value="가입하기" class="btn btn-color btn-md text-center m-b-20" id="btnRegister">
			            			<input type="button" value="취소" class="btn btn-color btn-md text-center m-b-20" onclick="location.href='${pageContext.request.contextPath}/'">
		            			</div>
		            		</div>
			            </div>
			        </form>
		        </div>
		   	</div>
	   	</div>
    </article>
    
</body>
</html>