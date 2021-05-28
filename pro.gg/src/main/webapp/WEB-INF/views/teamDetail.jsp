<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/js/semantic_aside.js" charset="utf-8"></script>
<script src="/js/semantic_header.js" charset="utf-8"></script>
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
			    			<h5 class="card-header-text">${team.teamName}</h5>
			    			<p>${team.team_description}</p>
			    		</div>
			    		<div class="card-block">
			    			<div class="row">
			    				<div class="col-sm-12 table-responsive">
			    					<table class="table">
			    						<thead>
			    							<tr>
												<th>포지션</th>
			    								<th>닉네임</th>
			    								<th>티어</th>
			    								<th>승률</th>
												<th>비고</th>
			    							</tr>
			    						</thead>
			    						<tbody>
			    							<tr>
												<th>
													<select name="position" id="position">
														<option value="top">탑</option>
														<option value="middle">미드</option>
														<option value="jungle">정글</option>
														<option value="bottom">바텀</option>
														<option value="support">서포터</option>
													</select>
												</th>
			    								<td>${team.captinName}</td>
			    								<td></td>
			    								<td>56.6%</td>
												<td></td>
			    							</tr>
			    						</tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div>
			    		<button>신청</button> &nbsp;&nbsp;
			    		<button>이전</button>
			    	</div>
			    </div>
		    </div>
	    </div>
    </article>
</body>
</html>