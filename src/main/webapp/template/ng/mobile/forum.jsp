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
				<ul data-role="listview" id="topics">
					<c:forEach items="${model.topics}" var="t" varStatus="i">
						<li index="${t.id}">
							<a href="/ng/m/topic/view?topicId=${t.id}&forumId=${t.forum.id}">${t.title}</a>
							[${t.user.nick}] 发表于 ${t.creationTime}
							<span class="ui-li-count">${t.clickTimes}</span>
						</li>
					</c:forEach>
				</ul>
				<hr />
				<button id="more" fid="${model.forum.id}" tid="0">点击查看更多。。。</button>
			</div>
			<div data-role="footer">
				<!-- <div data-role="navbar" data-position="fixed">
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
			    </div> -->
			 </div>
		</div>
</body>
<script>
	$(function() {
		$('#more').click(function() {
			var fid = $(this).attr('fid');
			// var lastTopicId = $('#topics li:last').attr('index');
			var lastTopicId = 0;
			if ($('#more').attr('tid') == 0) {
				lastTopicId = $('#topics li:last').attr('index');
			} else {
				lastTopicId = $('#more').attr('tid');
			}

			$.getJSON('/ng/m/forum/more', {
				tid: lastTopicId,
				fid: fid
			}, function(data) {
				$.each(data, function(i, item) {
					// $('<li>').attr('index', item.tid).append($('<a>').attr('href', '/ng/m/topic/view?topicId=' + item.tid + '&forumId=' + fid).text(item.title)).appendTo('#topics');
					var html = '<li index={index}><a href="/ng/m/topic/view?topicId={tid}&forumId={fid}">{title}</a>[{nick}] 发表于 {ctime}<span class="ui-li-count">{clickTimes}</span></li>';
					$(html.replace('{index}', item.tid).replace('{tid}', item.tid).replace('{fid}', fid).replace('{title}', item.title).replace('{nick}', item.author).replace('{ctime}', item.ctime).replace('{clickTimes}', item.clickTimes)).appendTo('#topics');
					lastTopicId = item.tid;
				})
				$('#topics').listview('refresh');
				$('#more').attr('tid', lastTopicId);
			})

		})
	})
</script>
<script type="text/javascript">
		$(function() {
			$('#back').click(function() {
				history.go(-1);
			})

			$('#refresh').click(function() {
				window.location.reload();
			})
		})
	</script>
</html>