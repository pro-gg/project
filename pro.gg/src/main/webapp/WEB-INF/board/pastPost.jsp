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
    <script src="/resources/js/bootstrap.min.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script>
        function callPostContent(postNumber){
            var num_postNumber = Number(postNumber);
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/board/postdetail.do?&postNumber=' + postNumber,
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

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
    </script>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="wrapper">
	    	<div class="content-wrapper">
		    	<div class="col-sm-12">
			    	<div class="card">
                        <div class="card-block">
			    			<div class="row">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>?????????</th>
                                            <th>??????</th>
                                            <th>?????????</th>
                                            <th>??????</th>
                                            <th>?????????</th>
                                            <th>?????????</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchPostList" items="${searchPostList}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <c:if test='${searchPostList.boardNumber == 1}'>
                                                        ???????????????
                                                    </c:if>
                                                    <c:if test='${searchPostList.boardNumber == 2}'>
                                                        ?????? ?????? ?????????
                                                    </c:if>
                                                    <c:if test='${searchPostList.boardNumber == 3}'>
                                                        ??? ?????????
                                                    </c:if>
                                                </td>
                                                <td><c:out value="${searchPostList.postNumber}"></c:out></td>
                                                <td>${searchPostList.nickname}</td>
                                                <td>
                                                    <a href="#" onclick="callPostContent('${searchPostList.postNumber}')">
                                                        ${searchPostList.postTitle}
                                                    </a> 
                                                </td>
                                                <script>
                                                    var postDate = '${searchPostList.postDate}';
                                                    var postTime = '${searchPostList.postTime}';
                                                    checkPostDate(postDate, postTime, '${status.count}');
                                                </script>
                                                <td id="${status.count}"></td>
                                                <td>${searchPostList.lookupCount}</td>
                                            </tr>                  
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>