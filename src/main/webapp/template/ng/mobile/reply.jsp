<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />	
	</head>
<body>
	<div data-role="page" id="reply_topic">
		<div data-role="header">
			<h1>回复主题</h1>
		</div>
		<div data-role="content">
			<form action="/ng/m/topic/reply_topic" method="post" data-transition="flip" data-ajax="true" id="reply_topic_form">
				<div data-role="fieldcontain">
				    <label for="content">回复内容:</label>
			        <textarea name="content" id="content"></textarea>
		        </div>
		        <input type="submit" id="submit" value="发表！" data-theme="a">
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#reply_topic input#submit').click(function() {
			var params = $('#reply_topic_form').serialize();
			$.ajax({
					type: 'POST',
					url: '/ng/m/topic/reply_topic',
					data: params,
					dataType: 'json',
					success: function(data) {
						if (data.status === 'success') {
							alert(data.info);
						} else {
							alert("发表回复失败！原因：" + data.info);
						}
					},
					error: function() {
						alert('出现了问题，请稍后再试！');
					}
				})
			return false;
		})
	})
</script>
</html>