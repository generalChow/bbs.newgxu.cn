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
			<a href="#">返回</a>
			<h1>修改内容</h1>
		</div>

		<div data-role="content">
			<form action="/ng/m/topic/update" method="post" data-ajax="true" id="update_form">
				<c:if test="${model.reply.firstReply eq true}">
					<div data-role="fieldcontain" id="forums">
				        <label for="forumId">标题:</label>
					    <input type="text" name="title" value="${model.topic.title}" />
			        </div>

			        <div data-role="fieldcontain">
					    <label for="content">帖子内容:</label>
				        <textarea name="content" id="content">${model.reply.contentBean.contentFilter}</textarea>
		        	</div>
				</c:if>

				<c:if test="${model.reply.firstReply eq false}">
					<div data-role="fieldcontain">
					    <label for="content">内容:</label>
				        <textarea name="content" id="content">${model.reply.contentBean.contentFilter}</textarea>
		        	</div>
				</c:if>

				<input type="hidden" name="topicId" value="${model.topic.id}" />
				<input type="hidden" name="forumId" value="${model.topic.forum.id}" />
				<input type="hidden" name="replyId" value="${model.reply.id}" />

				<input type="submit" id="submit" value="确认修改！" />
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#update_form #submit').click(function() {
			var data = $('#update_form').serialize();

			$.ajax({
				url: '/ng/m/topic/update',
				data: data,
				type: 'POST',
				dataType: 'json',
				success: function(data) {
					if (data.status === 'ok') {
						var msg = '修改成功！现在去查看？';
						if (confirm(msg)) {
							window.location.href = '/ng/m/topic/view?topicId=' + data.tid + '&forumId=' + data.fid;
						}
					} else {
						alert("内容修改失败！原因：" + data.info);
					}
				}
			})
		})
		return false;
	})
</script>
</html>