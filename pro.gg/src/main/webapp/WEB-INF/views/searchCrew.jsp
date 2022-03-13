<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
    <script src="/resources/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/resources/js/semantic_header.js" charset="utf-8"></script>
    <script src="/resources/js/bootstrap.min.js" charset="utf-8"></script>
    <script>
        function crewSearch(){

            var tier = document.getElementById("tier");
            var rate = document.getElementById("rate");

            var searchData = {
                'tier' : tier.options[tier.selectedIndex].value,
                'rate' : rate.options[rate.selectedIndex].value
            };

            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/searchCrew.do?searchData='+encodeURI(JSON.stringify(searchData)),
                data:'',
                dataType:'',
                success:function(data){
                    $("#printSearchCrew").html(data);
                }
            })
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
			    		<div class="card-header">
			    			<h5 class="card-header-text">회원 목록</h5>
								<select name="tier" id="tier">
									<option value="">티어</option>
									<option value="IRON IV">IRON IV</option>
									<option value="IRON III">IRON III</option>
									<option value="IRON II">IRON II</option>
									<option value="IRON I">IRON I</option>
									<option value="BRONZE IV">BRONZE IV</option>
									<option value="BRONZE III">BRONZE III</option>
									<option value="BRONZE II">BRONZE II</option>
									<option value="BRONZE I">BRONZE I</option>
									<option value="SILVER IV">SILVER IV</option>
									<option value="SILVER III">SILVER III</option>
									<option value="SILVER II">SILVER II</option>
									<option value="SILVER I">SILVER I</option>
									<option value="GOLD IV">GOLD IV</option>
									<option value="GOLD III">GOLD III</option>
									<option value="GOLD II">GOLD II</option>
									<option value="GOLD I">GOLD I</option>
									<option value="PLATINUM IV">PLATINUM IV</option>
									<option value="PLATINUM III">PLATINUM III</option>
									<option value="PLATINUM II">PLATINUM II</option>
									<option value="PLATINUM I">PLATINUM I</option>
									<option value="DIAMOND IV">DIAMOND IV</option>
									<option value="DIAMOND III">DIAMOND III</option>
									<option value="DIAMOND II">DIAMOND II</option>
									<option value="DIAMOND I">DIAMOND I</option>
									<option value="MASTER I">MASTER I</option>
									<option value="GRANDMASTER I">GRANDMASTER I</option>
									<option value="CHALLENGER I">CHALLENGER I</option>
								</select>
                                <select name="rate" id="rate">
                                    <option value="">승률</option>
                                    <option value="Erank">0%~20%</option>
                                    <option value="Drank">20%~40%</option>
                                    <option value="Crank">40%~60%</option>
                                    <option value="Brank">60%~80%</option>
                                    <option value="Arank">80%~100%</option>
                                </select>
								<input type="button" value="검색" name="searchCrew" id="searchCrew" onclick="crewSearch()">
							</div>
			    		</div>
			    		<div class="card-block">
			    			<div class="row">
								<div class="col-sm-12 table-responsive" id="searchResultOfTeamName">
								</div>
                                <div id="printSearchCrew"></div>
			    			</div>
			    		</div>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>