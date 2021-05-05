<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pro.gg</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    function moveLoginPage(){
        $(function(){
            $("article").empty();

            $.ajax({
                type: 'get',
                url: '${pageContext.request.contextPath}/move/login.do',
                data: '',
                dataType:'',
                success:function(data){
                    $("article").html(data)
                }
            })
        })
    }
</script>
<style>
    aside{
        float: left;
        line-height: 30px;
    }
    article{
        text-align: center;
    }
</style>
</head>
<body>
    <header><h1>분리 실험</h1></header>
    <aside>
        <ul>
            <li>
                <h3>INFO</h3>
                <input type="button" value="LOGIN" name="login" id="login" onclick="moveLoginPage();">
            </li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </aside>
    <article>
        <h3>main article</h3>
        <h3>main article</h3>
        <h3>main article</h3>
        <h3>main article</h3>
    </article>
</body>
</html>
