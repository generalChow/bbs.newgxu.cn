<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />		
	</head>
<body>
		<div data-role="page" id="forum">
			<div data-role="header">
				<a href="#" data-icon="back">返回</a>
				<h1>${model.forum.name}</h1>
				<a href="#" data-icon="refresh">刷新</a>
			</div>
			<div data-role="content">
				<section>
					<a href="/ng/m/home">雨无声移动论坛</a> -> <a href="/ng/m/forum?forumId=${model.forum.area.id}">${model.forum.area.name}</a> -> ${model.forum.name}
				</section>
				<hr />
				<section class="ui-grid-b">
					<!-- Row 1 -->
					<div class="ui-block-a">版主</div>
					<div class="ui-block-b">
						<c:forEach items="${model.forum.webmasters}" var="u">
							${u.nick} 
						</c:forEach>
					</div>
					<!-- Row 2 -->
					<div class="ui-block-a">本版宣言</div>
					<div class="ui-block-b">${model.forum.description}</div>
				</section>
				<hr />
				<ul data-role="listview">
					<c:forEach items="${model.topics}" var="t" varStatus="i">
						<li index="${i.count}" id="${t.id}">
							<a href="/ng/m/topic/view?topicId=${t.id}&forumId=${t.forum.id}">${t.title}</a>
							[${t.user.nick}] 发表于 ${t.creationTime}
							<span class="ui-li-count">${t.clickTimes}</span>
						</li>
					</c:forEach>
					<hr />
					<li><a href="#" style="text-align: center;">点击查看更多。。。</a></li>
				</ul>
			</div>
			<div data-role="footer">
				<div data-role="navbar" data-position="fixed">
			        <ul>
				        <li><a href="#home" data-icon="arrow-l" data-transition="fade">上一页</a></li>
				        <li><a href="#sessions" data-icon="arrow-r" data-transition="fade">下一页</a></li>
				        <li><a href="#sessions" data-icon="home" data-transition="fade">首页</a></li>
				        <c:if test="${not empty sessionScope.user}">
				        	<li><a href="#">发帖</a></li>
				    	</c:if>
				        <c:choose>
				        	<c:when test="${not empty sessionScope.user}">
				        		<li><a href="#sessions" data-icon="event" data-transition="fade">退出</a></li>
				        	</c:when>
				        	<c:otherwise>
				        		<li><a href="#sessions" data-icon="grid" data-transition="fade">登陆</a></li>
				        	</c:otherwise>
				    	</c:choose>
			        </ul>
			    </div>
			 </div>
		</div>
</body>
<script>
	$(function() {

	})
</script>
</html>