<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	if(session.getAttribute("member")!= null){	//세션확인
		response.sendRedirect(request.getContextPath()+"/");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
	<meta name ="google-signin-client_id" content="260796672294-2ohafah614eufqbajaunso754sjuqjtq.apps.googleusercontent.com">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
	<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
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
				appId : '901123407102819',
				autoLogAppEvents : true,
				xfbml : true,
				version : 'v11.0'
			});

			FB.AppEvents.logPageView();

			FB.getLoginStatus(function(response){
				console.log(response.status);
			});
		};

		(function(d, s, id){
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) {return;}
			js = d.createElement(s); js.id = id;
			js.src = "https://connect.facebook.net/ko_KR/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));

		function checkLoginState(){
			FB.getLoginStatus(function(response){
				if(response.status === 'connected'){
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
				}else if(response.status === 'not_authorized'){
					alert('로그인 되어 있지않은 상태입니다.');
				}
			});
		};

	</script>
	<script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js" nonce="86tvEXGE"></script>

	<!-- 구글 로그인 -->
	<script>
		function init() {
			gapi.load('auth2', function() {
				gapi.auth2.init();
				options = new gapi.auth2.SigninOptionsBuilder();
				options.setPrompt('select_account');
				// 추가는 Oauth 승인 권한 추가 후 띄어쓰기 기준으로 추가
				options.setScope('email profile openid https://www.googleapis.com/auth/user.birthday.read');
				// 인스턴스의 함수 호출 - element에 로그인 기능 추가
				// GgCustomLogin은 li태그안에 있는 ID, 위에 설정한 options와 아래 성공,실패시 실행하는 함수들
				gapi.auth2.getAuthInstance().attachClickHandler('GgCustomLogin', options, onSignIn, onSignInFailure);
			})
		}

		function onSignIn(googleUser) {
			var access_token = googleUser.getAuthResponse().access_token
			$.ajax({
				// people api를 이용하여 프로필 및 생년월일에 대한 선택동의후 가져온다.
				url: 'https://people.googleapis.com/v1/people/me'
				// key에 자신의 API 키를 넣습니다.
				, data: {personFields:'birthdays', key:'AIzaSyCQYS8yQ3PC1Qn5Uq_E8I4imXo_gnI19No', 'access_token': access_token}
				, method:'GET'
			})
			.done(function(e){
				//프로필을 가져온다.
				var profile = googleUser.getBasicProfile();
				console.log(profile)
				//수집해온 데이터를 ajax 를 활용해 컨트롤러로 넘겨준다.
				$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/googleLogin.do?profile='+encodeURI(JSON.stringify(profile)),
				data:'',
				dataType:'',
				success:function(data){
					$("body").html(data);
					window.location.replace('/');
				}
			})
			})
			.fail(function(e){
				console.log(e);
			})
		}
		function onSignInFailure(t){
			console.log(t);
		}
	</script>
</head>
<body class="back-gray">

	<div id="fb-root"></div>
	<script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v11.0&appId=901123407102819&autoLogAppEvents=1" nonce="inuMS5S2"></script>
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
		    					</div>
		    				</div>
		    				<div class="row">
		    					<%
								    String clientId = "_GlAhgDzVIlPh0a5FTYm";//애플리케이션 클라이언트 아이디값";
								    String redirectURI = URLEncoder.encode("http://localhost:8120/naver.do", "UTF-8");
								    SecureRandom random = new SecureRandom();
								    String state = new BigInteger(130, random).toString();
								    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
								    apiURL += "&client_id=" + clientId;
								    apiURL += "&redirect_uri=" + redirectURI;
								    apiURL += "&state=" + state;
								    session.setAttribute("state", state);
								%>
		    					<a href="<%=apiURL %>"><img height="50" src="/images/btnG_icon_circle.png"/></a>
		    					&nbsp;&nbsp;
		    					<a href="https://kauth.kakao.com/oauth/authorize?client_id=1447fe0b65f3900660f98ce2af5f18cf&redirect_uri=http://localhost:8120/kakao.do&response_type=code"><img height="50" src="/images/kakaoLogin.png"/></a>
								<a href="#"><img height="50" src="/images/btn_google_signin_dark_pressed_web.png" onclick="init()" id="GgCustomLogin"></a>
								<div class="fb-login-button" data-auto-logout-link="false" data-use-continue-as="false" onlogin="checkLoginState()"></div>
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