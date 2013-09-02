<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />
		<style type="text/css">
			.error {
				color: red;
				text-align: center;
			}
		</style>
	</head>
<body>
		<div data-role="page">
			<div data-role="header">
				<h1>登陆 - 论坛</h1>
			</div>
			<div data-role="content">
				<!-- <img src="/resources/images/mobile/logo.png" alt="欢迎登陆雨无声社区论坛！" /> -->
				<div id="info">
					<span class="error">${msg}</span>
				</div>
				<form action="/ng/m/do_login" data-ajax="false" method="post">
					<div data-role="fieldcontainer">
							<label for="username">用户名:</label>
							<input type="text" id="username" name="username" required />
					</div>
					<div data-role="fieldcontainer">
							<label for="password">密码:</label>
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