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
			<a href="#" data-icon="back">返回</a>
			<h1>${model.forum.name}</h1>
			<a href="#" data-icon="refresh">刷新</a>
		</div>
		<div data-role="content">
			<c:forEach items="${model.replieLines}" var="r">
				<c:choose>
					<c:when test="${r.floor eq 1}">
						<div>${r.reply.postUser.nick} @${r.reply.postTime} 发表</div>
						<p>${r.reply.contentBean.content}</p>
						<hr />
					</c:when>
					<c:when test="${r.floor eq 2}">
						<div>${r.reply.postUser.nick} @${r.reply.postTime} 回复</div>
						<p>${r.reply.contentBean.content}</p>
						<hr />
					</c:when>
					<c:otherwise>
						<div>${r.reply.postUser.nick} @${r.reply.postTime} 回复</div>
						<p>${r.reply.contentBean.content}</p>
						<hr />
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>
</body>
</html>	