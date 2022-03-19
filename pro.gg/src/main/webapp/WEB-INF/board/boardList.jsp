<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <title></title>
 <script src="/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
 <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="/resources/css/mystyle.css"/>
 <script>
     function checkPostDate(postDate, postTime, count){

            let today = new Date();

            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let date = today.getDate();

            var currentDate = year + "." + month +"." + date;
            if(currentDate === postDate){
                document.getElementById(count).innerHTML = postTime;
            }else{
                document.getElementById(count).innerHTML = postDate;
            }
    }
     
     function selChange(){
    	 	var sel = document.getElementById('cntPerPage').value;
	        $(function(){
	            $.ajax({
	                type:'get',
	                url:'${pageContext.request.contextPath}/board/boardList.do?nowPage=${paging.nowPage}&cntPerPage='+sel+'&boardNumber=${paging.boardNumber}',
	                data:'',
	                dataType:'',
	                success:function(data){
	                    $("#freeBoardList").html(data);
	                }
	            })
	        })
	    }
     
     function pageChange(x, y){
    	 $(function(){
	            $.ajax({
	                type:'get',
	                url:'${pageContext.request.contextPath}/board/boardList.do?boardNumber=${paging.boardNumber }&nowPage='+x+'&cntPerPage='+y,
	                data:'',
	                dataType:'',
	                success:function(data){
	                    $("#freeBoardList").html(data);
	                }
	            })
	        })
     }
 </script>
</head>
<body>
	<br>
	<h2>${postType }</h2>
	<div style="float: right;">
		<select id="cntPerPage" name="sel" onchange="selChange()">
			<option value="5"
				<c:if test="${paging.cntPerPage == 5}">selected</c:if>>5줄 보기</option>
			<option value="10"
				<c:if test="${paging.cntPerPage == 10}">selected</c:if>>10줄 보기</option>
			<option value="20"
				<c:if test="${paging.cntPerPage == 20}">selected</c:if>>20줄 보기</option>
		</select>
	</div>
	<br><br>
	<table class="table">
        <thead>
            <tr>
                <th>No</th>
                <th>닉네임</th>
                <th>제목</th>
                <th>작성일시</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="boardList" items="${boardList}" varStatus="status">
                <tr>
                    <td>${boardList.postNumber}</td>
                    <td>${boardList.nickname}</td>
                    <td><a href="${pageContext.request.contextPath}/board/postdetail.do?postNumber=${boardList.postNumber}">${boardList.postTitle}</a></td>
                    <script>
                        var postDate = '${boardList.postDate}';
                        var postTime = '${boardList.postTime}';
                        checkPostDate(postDate, postTime, '${status.count}');
                    </script>
                    <td id="${status.count}"></td>
                    <td>${boardList.lookupCount}</td>
                </tr>                  
            </c:forEach>
        </tbody>
    </table>
    <div style="display: block; text-align: center;">		
		<c:if test="${paging.startPage != 1 }">
			<a href="#" onclick="pageChange(${paging.startPage - 1 },${paging.cntPerPage});">&lt;</a>
		</c:if>
		<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
			<c:choose>
				<c:when test="${p == paging.nowPage }">
					<b>${p }</b>
				</c:when>
				<c:when test="${p != paging.nowPage }">
					<a href="#" onclick="pageChange(${p},${paging.cntPerPage });">${p }</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${paging.endPage != paging.lastPage}">
			<a href="#" onclick="pageChange(${paging.endPage+1 },${paging.cntPerPage})">&gt;</a>
		</c:if>
	</div>
</body>
</html>