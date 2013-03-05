<!doctype html>
<html>
	<head>
		<jsp:include page="mobile/mobile-manifest.jsp" />		
	</head>
<body>
		<div data-role="page">
			<div data-role="header">
				<h1>登陆 - 论坛</h1>
			</div>
			<div data-role="content">
				<img src="/resources/images/mobile/logo.png" alt="欢迎登陆雨无声社区论坛！" />
				<form action="/m/do_login.yws" data-ajax="false" method="post">
					<div data-role="fieldcontainer">
							<label for="username">用户名:</label>
							<input type="text" id="username" name="username" required />
					</div>
					<div data-role="fieldcontainer">
							<label for="username">密码:</label>
							<input type="password" id="password" name="password" required />
					</div>
					<input type="submit" value="登陆！" />
				</form>
			</div>
			<div data-role="footer">
				<h4>powered by newgxu' s @爱因斯坦的狗</h4>
			</div>
		</div>
	</body>
</html>