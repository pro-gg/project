<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function findId(){
            $(function(){
                var findId = {
                    'name' : document.getElementById("name").value,
                    'email' : document.getElementById("email").value
                }
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/member/findId.do?findId='+encodeURI(JSON.stringify(findId)),
                    data :'',
                    dataType:'',
                    success:function(data){
                       $("body").html(data);
                    }
                })
            })
        }
    </script>
</head>
<body>
    <article class="find_id">
        <div class="find_form">
            <h3>아이디 찾기</h3>
            <div class="col-md-12">
	            <div class="md-input-wrapper">
	            	<input type="text" name="name" id="name" class="md-form-control" required="required" autocomplete='off'>
	            	<label>이름</label>
	            </div>
            </div>
            <div class="col-md-12">
	            <div class="md-input-wrapper">
	            	<input type="email" name="email" id="email" class="md-form-control" required="required" autocomplete='off'>
	            	<label>이메일</label>
	            </div>
	        </div>
	        <div class="col-xs-10 offset-xs-1">
            	<input type="button" class="btn btn-color btn-md btn-block waves-effect text-center m-b-20" value="찾기" onclick="findId()"/>
            </div>
        </div>
    </article>
    
</body>
</html>