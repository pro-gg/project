<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
	<script type="text/javascript">
		function popup(){
			window.name="parent";
			var url="${pageContext.request.contextPath}/move/terms.do";
			var name="약관 동의";
			var option="width=600, height=700, top=100, left=200, location=no";
			window.open(url,name,option);
		}
		
		function findId(){
            window.open("${pageContext.request.contextPath}/move/findId.do", "findId", "width=550, height=450, left=100, top=50");
        }
        function findPasswd(){
            window.open("${pageContext.request.contextPath}/move/findPasswd.do", "findId", "width=550, height=450, left=100, top=50");
        }
	</script>

	<!--페이스북 로그인-->
	<script>
		window.fbAsyncInit = function(){
			FB.init({
				appId : '202332558445458',
				autoLogAppEvents : true,
				xfbml : true,
				version : 'v11.0'
			});

			FB.AppEvents.logPageView();

			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
		};

		(function(d, s, id){
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) {return;}
			js = d.createElement(s); js.id = id;
			js.src = "https://connect.facebook.net/ko_KR/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));

		function checkLoginState() {
			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
		}

		function statusChangeCallback(response){
			console.log("Button click");
			if(response.status === 'connected'){
				getUserData();
			}
		}

		function getUserData(){
			console.log("Facebook Login test");
			FB.api(
				'/me',
				'GET',
				{"fields":"id,name,email"},
				function(response) {

					var facebookName = response.name;
					var facebookId = response.id;
					var facebookEmail = response.email;

					$.ajax({
						type:'post',
						url:'${pageContext.request.contextPath}/facebookLogin.do?facebookName='+encodeURI(facebookName)+'&facebookId='+facebookId+'&facebookEmail='+facebookEmail,
						data:'',
						dataType:'',
						success:function(data){
							$("body").html(data);
							window.location.replace('/');
						}
					})
					
				}
			);
		}
	</script>
	<script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js" nonce="86tvEXGE"></script>
</head>
<body class="back-gray">

	<div id="fb-root"></div>
	
    <header></header>
    <aside></aside>
    <article>
        <section class="login p-fixed d-flex text-center">
	    	<div class="content-wrapper">
		    	<div class="container-fluid">
		    		<div class="login-card card-block">
		    			<form class="md-float-material" action="${pageContext.request.contextPath}/trylogin.do" method="POST">
		    				<div class="text-center">
		    					<img src="/images/progg.png" alt="logo"/>
		    				</div>
		    				<div class="row">
		    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    							<input type="text" name="id" id="id" class="md-form-control" required="required" autocomplete='off'>
		    							<label>아이디</label>
		    						</div>
		    					</div>
		    					<div class="col-md-12">
		    						<div class="md-input-wrapper">
		    							<input type="password" name="passwd" id="passwd" class="md-form-control" required="required" autocomplete='off'>
		    							<label>비밀번호</label>
		    						</div>
		    					</div>
		    					<div class="col-sm-6 col-xs-12 text-right id-pw-find">
		    						<a href="#" onclick="findId()" class="text-right f-w-600">아이디 찾기</a>&nbsp;&nbsp;
				                    <a href="#" onclick="findPasswd()" class="text-right f-w-600">비밀번호 찾기</a>
		    					</div>
		    				</div>
		    				<div class="row">
		    					<div class="col-xs-10 offset-xs-1">
		    						<input type="submit" class="btn btn-color btn-md btn-block text-center m-b-20" value="로그인"></input>
									<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
									</fb:login-button>
		    					</div>
		    				</div>
		    				<div class="col-sm-12 col-xs-12 text-center">
		    					<span class="text-muted">처음이세요?</span>
		    					<a href="javascript:popup()" class="f-w-600 p-1-5">회원가입</a>
		    				</div>
		    			</form>
		    		</div>
		    	</div>
	    	</div>
    	</section>
    </article>
    
</body>
</html>