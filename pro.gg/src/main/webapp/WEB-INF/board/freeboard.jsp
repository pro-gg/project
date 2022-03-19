<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
    <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script src="/resources/js/elements.js" charset="utf-8"></script>
    <title></title>
    <script>
	    var boardNumber = 1;
	
	    function callfreeboard(x){
	    	boardNumber = x;
	    	
	        $(function(){
	            $.ajax({
	                type:'get',
	                url:'${pageContext.request.contextPath}/board/boardList.do?nowPage=1&cntPerPage=10&boardNumber='+ boardNumber,
	                data:'',
	                dataType:'',
	                success:function(data){
	                    $("#freeBoardList").html(data);
	                }
	            })
	        })
	    }
	    
        function boardPosting(){
        	
            var member = '${sessionScope.member}';
            if(member.length !== 0){
                // 게시판 글 작성 페이지로 이동
                // 어떤 게시판에서 작성되는 글인지 구분하기 위해 게시판 분류 번호를 데이터로 넘겨준다.
                // (1 : 자유 게시판, 2 : 팀원 모집 게시판, 3 : 팁 게시판)
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/move/boardpost.do?boardNumber='+boardNumber,
                    data:'',
                    dataType:'',
                    processData:false,
                    contentType:false,
                    success:function(data){
                        $("body").html(data);
                    }
                })
            }else{
                alert('로그인 해야만 이용 가능한 기능입니다.');
            }
        }
        
        function postSearch(){
            // 닉네임 검색, 게시글 제목 검색 옵션 지정해줄것
            var searchKeyword = document.getElementById("searchPost").value;
            var searchCondition = document.getElementById("searchCondition");
            // 조건문 이용해서 컨트롤러에서 어떤 값을 받았는지 구별하게끔 한다.
            // target 변수에 어떤 값인지를 알려주는 데이터 저장
            var target = undefined;
            var checkCondition = searchCondition.options[searchCondition.selectedIndex].text
            if(checkCondition === "게시글 제목"){
                target = "title";
            }
            else if(checkCondition === "닉네임"){
                target = "nickname";
            }
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/board/postSearch.do?searchKeyword=' + encodeURI(searchKeyword) + '&target=' + target,
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }
    </script>
    <style>
        li{
            float: left;
            margin-right: 30px;
        }
        #searchArea, #writePost{
            float: right;
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
                                    <a href="#" onclick="callfreeboard(1)" id="freeboard">
                                        자유 게시판
                                    </a>
                                </li>
                                <li>
                                    <a href="#" onclick="callfreeboard(2)" id="crewboard">
                                        팀원 모집 게시판
                                    </a>
                                </li>
                                <li>
                                    <a href="#" onclick="callfreeboard(3)" id="tipboard">
                                        팁 게시판
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="card-block">
                            <div class="row">
                                <div id="searchArea">
                                    <select name="searchCondition" id="searchCondition">
                                        <option value="postTitle">게시글 제목</option>
                                        <option value="nickname">닉네임</option>
                                    </select>
                                    <input type="text" placeholder="게시글 검색" id="searchPost"><input type="button" value="검색" id="searchBottonClick" onclick="postSearch()" >
                                </div>
                                <script>callfreeboard(1)</script>
                                <div class="col-sm-12 table-responsive" id="freeBoardList">
			    				</div>
                            </div>
                        </div>
                        <input type="button" value="글 작성" id="writePost" onclick="boardPosting()">
                    </div>
                </div>
            </div>
        </div>
        
    </article>
</body>
</html>