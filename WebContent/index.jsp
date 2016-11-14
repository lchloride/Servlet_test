<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>AnimeHelper</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="utf-8">
<link rel="icon" href="./image/page-icon.ico" type="image/x-ico" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link
	href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script
	src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
      <![endif]-->
<style type="text/css">
.queryItem {
	padding-left: 15%;
}

label.text {
	font-size: 1.1em;
}

.body {
	background: #2d3b36 url(./image/bg.jpg) no-repeat bottom right fixed;
	background-size: cover
}

.mainbody {
	margin-top: auto;
	margin-bottom: 50px;
	margin-right: 10%;
	margin-left: 10%;
	font-family: Tahoma, "Microsoft YaHei", sans-serif;
	background: #E0FFFF;
	padding-bottom: 50px;
}

.result {
	border-top-style: solid;
	border-top-width: thin;
	border-top-color: #9C9C9C;
	margin-right: 5%;
}

.querypart {
	border-left-style: solid;
	border-left-width: thin;
	border-left-color: #CFCFCF;
	padding-right: 5%;
}

.myjumbotron {
	position: relative;
	width: 800px;
	margin-left: auto;
	margin-right: auto;
	margin-top: 100px;
	background-color: #F0F8FF;
}
}
</style>
<script type="text/javascript">
	var collapse = false;
	function loadArrow() {
		if (collapse == true)
			return "查询条件<span class=\"glyphicon glyphicon-collapse-down\" style=\"float:right\"></span>";
		else
			return "查询条件<span class=\"glyphicon glyphicon-collapse-up\" style=\"float:right\"></span>";
	}
	function submitFun(name) {
		var f = document.getElementById(name);
		f.submit();
	}
</script>
</head>
<body class="body">
	<div class="mainbody">
		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">AnimeHelper</a>
				</div>
				<div>
					<ul class="nav navbar-nav">
						<li class="active"><a href="index.jsp">主页</a></li>
						<li><a href="#">帮助</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> 参考链接 <b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="#">WebSite1</a></li>
								<li><a href="#">WebSite2</a></li>
								<li><a href="#">WebSite3</a></li>
								<li class="divider"></li>
								<li><a href="#">WebSite4</a></li>
							</ul></li>
					</ul>
					<ul class="nav navbar-nav navbar-right" style="padding-right: 5%">
						<li><a href="#"><span class="glyphicon glyphicon-user"></span>
								注册</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
								登录</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="row">
			<div class="col-lg-3 col-md-3 col-sm-3 hidden-xs">
				<ul id="myTab" class="nav nav-tabs nav-stacked">
					<li class="disabled"><a data-toggle="tab"
						style="font-size: 1.25em">查询信息</a></li>
					<li class="dropdown active"><a href="#" id="myTabDrop1"
						class="dropdown-toggle" data-toggle="dropdown"
						style="padding-left: 15%">动漫作品 <b class="caret"></b>
					</a>
						<ul class="dropdown-menu " role="menu"
							aria-labelledby="myTabDrop1" id="TabDropList">
							<li><a href="#all_work" tabindex="-1" data-toggle="tab"
								style="padding-left: 15%"> 全部作品</a></li>
							<li><a href="#airgoing" tabindex="-1" data-toggle="tab"
								style="padding-left: 15%"> 连载中作品</a></li>
						</ul></li>
					<li><a href="#character" data-toggle="tab"
						style="padding-left: 15%">动漫角色</a></li>
					<li><a href="#cast" data-toggle="tab"
						style="padding-left: 15%">配音声优</a></li>
					<li><a href="#writer" data-toggle="tab"
						style="padding-left: 15%">作者</a></li>
					<li class="divider"></li>
					<li class="active"><a data-toggle="tab"
						style="font-size: 1.25em">编辑信息</a></li>
					<li class="active"><a data-toggle="tab"
						style="font-size: 1.25em">意见反馈</a></li>
				</ul>
			</div>

			<div class=" col-lg-9 col-md-9 col-sm-9 querypart" style="">
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="all_work">
						<div class="page-header" style="">
							<h3>
								全部动漫信息检索页 <br /> <small>使用作品名、制作商、作者检索动漫作品的基本信息。通过基本信息可以查看详细信息。</small>
							</h3>
						</div>
						<div class="panel panel-primary" style="background: #E9EEEE">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne" id="query_condition"> 查询条件 <script>
											document
													.getElementById("query_condition").innerHTML = window
													.loadArrow();
										</script>
									</a>
								</h4>
							</div>

							<div id="collapseOne" class="panel-collapse collapse in">
								<div class="panel-body">
									<form role="form" action="Anime" method="POST">
										<div class="form-group">
											<label class="text" for="anime_name">作品名</label> <input
												type="text" class="form-control" id="anime_name"
												placeholder="请输入作品名称，不指定该项可不填">
										</div>
										<div class="form-group">
											<label class="text" for="product_name">制作公司</label> <input
												type="text" class="form-control" id="product_name"
												placeholder="请输入制作公司名称，不指定该项可不填">
										</div>
										<div class="form-group">
											<label class="text" for="product_name">作者</label> <input
												type="text" class="form-control" id="writer_name"
												placeholder="请输入作者名，不指定该项可不填">
										</div>
										<label class="text" for="anime_name">要显示的列</label>
										<div class="form-group">
											<label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_anime_name" value="option1">动漫名称
											</label> <label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_start_date" value="option2">上映日期
											</label> <label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_product_company"
												value="option3">制作公司
											</label><label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_episode_number" value="option3">集数
											</label><label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_writer" value="option3">原作者
											</label><label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_director" value="option3">导演
											</label><label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_scriptwriter" value="option3">编剧
											</label><label class="checkbox-inline"> <input
												type="checkbox" id="Checkbox_character" value="option3">剧中角色<span
												style="color: #808080">(检索结果可能很多，请慎重选择)</span>
											</label>
										</div>
										<p>
											<button type="submit" class="btn btn-primary">提交</button>
											<button type="reset" class="btn btn-default">重置</button>
										</p>
									</form>
								</div>
							</div>
						</div>

						<div class="panel panel-primary">
							<div class="panel-heading">
								<form action="Anime" method="GET" id="form_set"
									class="form-inline " role="form">
									<h4 class="panel-title">
										查询结果 <span style="float: right"> <label for="name">每页显示</label>
											<select class="form-control" name="page_content_number"
											style="max-height: 30px;" onchange="submitFun('form_set')">
												<option value="5" ${page_content_number=="5"?'selected':''}>5</option>
												<option value="10"
													${page_content_number=="10"?'selected':''}>10</option>
												<option value="25"
													${page_content_number=="25"?'selected':''}>25</option>
												<option value="50"
													${page_content_number=="50"?'selected':''}>50</option>
										</select>
										</span>
									</h4>
								</form>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-hover table-bordered">
										<tr class="info">
											<c:forEach var="col_name"
												items="${requestScope.QueryHeader }">
												<th>${col_name }</th>
											</c:forEach>
										</tr>
										<c:forEach var="item" items="${requestScope.QueryResult }">
											<tr>
												<c:forEach var="str" items="${item }">
													<td>${str }</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
								<form action="Anime" method="GET">
									<ul class="pagination">
										<li><a
											href="?page_idx=1&page_content_number=${page_content_number}">&laquo;</a></li>
										<c:forEach var="page_idx" begin="1"
											end="${requestScope.ResultPageCount }">
											<li ${page_idx==requestScope["page_idx"]?'class="active"':''}><a
												href="?page_idx=${page_idx }&page_content_number=${page_content_number}">${page_idx}</a></li>
										</c:forEach>
										<li><a
											href="?page_idx=${requestScope.ResultPageCount}&page_content_number=${page_content_number}">&raquo;</a></li>
									</ul>
								</form>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="airgoing">
						<p>iOS 是一个由苹果公司开发和发布的手机操作系统。最初是于 2007 年首次发布 iPhone、iPod Touch
							和 Apple TV。iOS 派生自 OS X，它们共享 Darwin 基础。OS X 操作系统是用在苹果电脑上，iOS
							是苹果的移动版本。</p>
					</div>
					<div class="tab-pane fade" id="character">
						<p>jMeter 是一款开源的测试软件。它是 100% 纯 Java 应用程序，用于负载和性能测试。</p>
					</div>
					<div class="tab-pane fade" id="cast">
						<p>Enterprise Java
							Beans（EJB）是一个创建高度可扩展性和强大企业级应用程序的开发架构，部署在兼容应用程序服务器（比如 JBOSS、Web
							Logic 等）的 J2EE 上。</p>
					</div>
					<div class="tab-pane fade" id="writer">
						<p>Enterprise Java
							Beans（EJB）是一个创建高度可扩展性和强大企业级应用程序的开发架构，部署在兼容应用程序服务器（比如 JBOSS、Web
							Logic 等）的 J2EE 上。</p>
					</div>
				</div>
				<script>
					$(function() {
						$('#TabDropList li:eq(0) a').tab('show');
					});
					$(function() {
						$('#collapseOne')
								.on(
										'show.bs.collapse',
										function() {
											collapse = false;
											document
													.getElementById("query_condition").innerHTML = window
													.loadArrow();
										})
					});
					$(function() {
						$('#collapseOne')
								.on(
										'hide.bs.collapse',
										function() {
											collapse = true;
											document
													.getElementById("query_condition").innerHTML = window
													.loadArrow();
										})
					});
				</script>
				<c:if test="${result == true }">
					<script>
						$(function() {
							$('#collapseOne').collapse('hide');
							collapse = true;
						});
					</script>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>