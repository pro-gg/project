<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
    <title>자유게시판</title>
    <script>
        function pageReload(){
            window.location.reload();
        }

        function callfreeboard(){
            $(function(){
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/freeboardList.do',
                    data:'',
                    dataType:'',
                    success:function(data){
                        $("#freeBoardList").html(data);
                    }
                })
            })
        }
    </script>
    <style>
        li{
            float: left;
            margin-right: 30px;
        }
    </style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="wrapper">
            <div class="content-wrapper">
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-header">
                            <ul class="top-nav lft-nav">
                                <li>
                                    <a href="#" onclick="pageReload()">자유 게시판 </a>
                                </li>
                                <li>
                                    <a href="#">팀원 모집 게시판 </a>
                                </li>
                                <li>
                                    <a href="#">팁 게시판 </a>
                                </li>
                            </ul>
                        </div>
                        <div class="card-block">
                            <div class="row">
                                <script>callfreeboard()</script>
                                <div class="col-sm-12 table-responsive" id="freeBoardList">
			    				</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </article>
</body>
</html>