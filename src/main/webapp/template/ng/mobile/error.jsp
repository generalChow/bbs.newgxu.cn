<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />
	</head>
<body>
<div data-role="page">
	<div data-role="header">
		<head>出错啦！</head>
	</div>
	<div data-role="content">
		<div id="error">错误信息：${msg.info}</div>
		<div id="reson">出错原因：${msg.reason}</div>
		<hr />
		<div id="solutions">
			<ul data-role="listview">
				<c:forEach items="${msg.solutions}" var="s">
					<li>${s}</li>
				</c:forEach>
			</ul>
		</div>
		<button id="back">返回上一步</button>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#back').click(function() {
			history.go(-1);
		})
	})
</script>
</html>