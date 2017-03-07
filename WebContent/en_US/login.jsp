<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- This is login page in English -->
<html lang="zh_CN">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="UTF-8">
<title>Login -- AnimeHelper</title>

<link rel="icon" href="/AnimeHelper/image/page-icon.ico" type="image/x-ico" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="/AnimeHelper/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/AnimeHelper/bootstrap/js/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/AnimeHelper/bootstrap/js/bootstrap.min.js"></script>
<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<style type="text/css">
.body {
	background: #2d3b36 url(/AnimeHelper/image/bg.jpg) no-repeat center
		bottom fixed;
	background-size: cover
}

@media ( min-width : 768px) {
	.mainbody {
		margin-top: auto;
		margin-bottom: 50px;
		margin-right: 10%;
		margin-left: 10%;
		font-family: Tahoma, "Microsoft YaHei", sans-serif;
		background: #E0FFFF;
		padding-bottom: 50px;
	}
}

@media ( max-width : 767px) {
	.mainbody {
		margin-top: auto;
		margin-bottom: auto;
		margin-right: 5px;
		margin-left: 5px;
		font-family: Tahoma, "Microsoft YaHei", sans-serif;
		background: #E0FFFF;
		padding-bottom: 50px;
	}
}

.loginpart {
	padding-left: 5%;
	padding-right: 5%;
}
</style>
<script>
	function checkSubmit(id) {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;

		if (username == "") {
			alert("用户名不能为空");
			return false;
		}
		if (password == "") {
			alert("密码不能为空");
			return false;
		}
		document.getElementById("lastURL").value = document.referrer;
		document.getElementById(id).submit();
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
						<li><a href="index.jsp">HomePage</a></li>
						<li><a href="#">Help</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> Reference <b class="caret"></b>
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
								Register</a></li>
						<li class="active"><a href="login"><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="page-header loginpart" style="">
			<h3>
				Login Page <br /> <small>Use your username/ID and password
					to login</small>
			</h3>

			<br />

			<form role="form" action="/AnimeHelper/en_US/login" method="POST"
				id="form_login" onsubmit="return checkSubmit(form_login)">
				<c:if test="${not empty isLogin and isLogin == false }">
					<div class="text-danger">Username(ID) OR password is wrong!</div>
				</c:if>
				<input type="hidden" value="" id="lastURL" name="LastURL">
				<div class="form-group">
					<label class="text" for="username">Username/ID</label> <input
						type="text" class="form-control" id="username"
						name="Text_username" placeholder="Username or ID." value="">
				</div>

				<div class="form-group">
					<label class="text" for="password">Password</label> <input
						type="password" class="form-control" id="password"
						name="Text_password" placeholder="Password" value="">
				</div>


				<div class="form-group">
					<div class="row">
						<label class="text" for="verifycode" style="padding-left: 15px">Verify
							Code</label>
						<div id="verifycode_combination">
							<div class="col-md-10">
								<input type="text" class="form-control" id="verifycode"
									name="Text_verifycode"
									placeholder="Please input code in the picure, case ignored"
									value="">
							</div>
							<div class="col-md-2">
								<img src="/AnimeHelper/image/VerifyCode/verifycode1.png" />
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-md-2">
							<label class="checkbox-inline"> <input type="checkbox"
								id="Checkbox_remember_user" name="Checkbox_remember_user"
								${Checkbox_remember_user=='on'?'checked':''}>Remember Me
							</label>
						</div>
						<div class="col-md-8"></div>
						<div class="col-md-2">
							<a href="forgetpwd.jsp">Forget Password?</a>
						</div>
					</div>
				</div>

				<button type="submit" class="btn btn-primary">Login</button>
			</form>

		</div>
	</div>
</body>
</html>