<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pro.gg</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>

    $(function(){
        $.ajax({
            type:'get',
            url:'${pageContext.request.contextPath}/article.do',
            data:'',
            dataType:'',
            success:function(data){
                $("article").html(data)
            }
        });
    });
    
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
        height: 920px;
        width: 300px;
        float: left;
        line-height: 30px;
        background-color: aqua;
    }
    article{
        position:absolute;
        width: 920px; height: 50px;
        left: 0; right: 0;
        margin-left: 300px; margin-right:auto;
        top: 0; bottom: 0;
        margin-top: auto; margin-bottom: auto;
    }
</style>
</head>
<body>
    <header><h1>Pro.gg</h1></header>
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
    <article></article>
</body>
</html>
