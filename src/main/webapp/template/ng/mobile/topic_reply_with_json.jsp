<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />		
		<style type="text/css">
			h3#title {
				text-align: center;
			}

			.face img {
				width: 50px;
				height: 50px;
				margin-right: 5px;
				float: left;
			}

			.nick {
				font-size: 1.1em;
				color: purple;
				padding-right: 5px;
			}

			.u_title {
				font-size: .7em;
				color: blue;
			}

			.extra {
				font-size: 0.7em;
			}

			.profile {
				font-size: 0.7em;
			}

			p.content {
				clear: both;
				padding-top: 10px;
			}

			p.time {
				text-align: right;
				font-size: .7em;
			}
			.floor {
				float: right;
				font-size: 0.7em;
				color: purple;
			}
		</style>
	</head>
<body>
	<div data-role="page">
		<div data-role="header">
			<a href="#" data-icon="back">返回</a>
			<h1>${model.topic.title}</h1>
			<a href="#" data-icon="refresh">刷新</a>
		</div>
		<div data-role="content" id="main">
			<h3 id="title">${model.topic.title}}</h3>
			<hr />
			<c:forEach items="${model.replieLines}" var="r" varStatus="i">
				<c:choose>
					<c:when test="${r.floor eq 1}">
						<div id="topic">
							<div>
								<div class="face">
									<img src="/resources/face.png" alt="${r.reply.postUser.nick}" />
								</div>
								<div class="detail">
									<div>
										<span class="nick">${r.reply.postUser.nick}</span>
										<span class="u_title">"${r.reply.postUser.title}"</span>
									</div>
									<div>
										<span class="extra">[职务 ${r.reply.postUser.groupNameDisplay}] </span>
										<span class="extra">[经验值 ${r.reply.postUser.exp}] </span>
										<span class="extra">[灌水数 ${r.reply.postUser.totalPostDisplay}]</span>
									</div>
								</div>
							</div>
							<p class="content">${r.reply.contentBean.content}</p>
							<p class="time">@${r.reply.postTime}</p>
							<span class="profile">-- ${r.reply.postUser.idiograph}</span>
							<a href="#reply_topic_dialog" data-role="button">回复主题！</a>
							<hr />
						</div>
					</c:when>
					<c:otherwise>
						<div class="reply" id="${r.reply.id}" floor="${i.index}">
							<span class="floor">${i.index}</span>
							<div>
								<div class="face">
									<img src="/resources/face.png" alt="${r.reply.postUser.nick}" />
								</div>
								<div class="detail">
									<div>
										<span class="nick">${r.reply.postUser.nick}</span>
										<span class="u_title">"${r.reply.postUser.title}"</span>
									</div>
									<div>
										<span class="extra">[职务 ${r.reply.postUser.groupNameDisplay}] </span>
										<span class="extra">[经验值 ${r.reply.postUser.exp}] </span>
										<span class="extra">[灌水数 ${r.reply.postUser.totalPostDisplay}]</span>
									</div>
								</div>
							</div>
							<p class="content">${r.reply.contentBean.content}</p>
							<p class="time">@${r.reply.postTime}</p>
							<span class="profile">${r.reply.id}-- ${r.reply.postUser.idiograph}</span>
							<a href="#reply_topic_dialog" data-role="button" rid="${r.reply.id}" class="_reply">回复Ta！</a>
							<hr />
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<button id="more" lastRid="${last_rid}" floor="${count}">点击查看更多回复！</button>
			<div id="nima"></div>
		</div>
	</div>

	<div data-role="dialog" id="reply_topic_dialog">
		<div data-role="header">
			<h1>回复主题</h1>
		</div>
		<div data-role="content">
			<form action="/ng/m/topic/reply_topic" method="post" data-transition="flip" data-ajax="true" id="reply_topic">
				<div data-role="fieldcontain">
				    <label for="content">回复内容:</label>
			        <textarea name="content" id="content"></textarea>
		        </div>
		        <input name="forumId" type="hidden" id="forumId" value="${param.forumId}" />
				<input name="topicId" type="hidden" id="topicId" value="${param.topicId}" />
		        <input type="submit" id="submit" value="发表！" data-theme="a">
			</form>
		</div>
	</div>

	<div data-role="dialog" id="reply_dialog">
		<div data-role="header">
			<h1>回复主题</h1>
		</div>
		<div data-role="content">
			<form action="/ng/m/topic/reply" method="post" data-transition="flip" data-ajax="true" id="reply">
				<div data-role="fieldcontain">
				    <label for="content">回复内容:</label>
			        <textarea name="content" id="content"></textarea>
		        </div>
		        <input name="forumId" type="hidden" id="forumId" value="${param.forumId}" />
				<input name="topicId" type="hidden" id="topicId" value="${param.topicId}" />
		        <input type="submit" id="submit" value="发表！" data-theme="a">
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#reply_topic input#submit').click(function() {
			var params = $('#reply_topic').serialize();
			$.ajax({
					type: 'POST',
					url: '/ng/m/topic/reply_topic',
					data: params,
					dataType: 'json',
					success: function(data) {
						if (data.status === 'ok') {
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
		});

		$('a.reply').click(function() {
			
		})

		// var last = ${last};

		// $('#more').click(function() {
		// 	if (last !== ${last}) {
		// 		last = $('#main div.reply:last').attr('index');
		// 	}
		// 	var offset = $('#main div.reply:last').attr('id');
		// 	var url = '/ng/m/topic/load_replies?offset={offset}&tid=${param.topicId}&floor={floor}'.replace('{offset}', offset).replace('{floor}', last);
		// 	$.ajax({
		// 		url: url,
		// 		type: 'GET',
		// 		success: function(data) {
		// 			$('#main div.reply:last').append(data);
		// 		}
		// 	})
		// 	// alert($('#main div.reply:last').attr('index'));
		// 	// $('#more').attr('last', $('#main div.reply:last').attr('index'));
		// 	// last = $('#main div.reply:last').attr('index');
		// })


		$('#more').click(function() {
			var lastFloor = $(this).attr('floor');
			var lastReplyId = $(this).attr('lastRid');
			var html = '<div class="reply" id="{rid}" floor="{floor}"><span class="floor">{floor}</span><div><div class="face"><img src="/resources/face.png" alt="" /></div><div class="detail"><div><span class="nick">{nick}</span><span class="u_title">"{u_title}"</span></div><div><span class="extra">[职务 {groupName}] </span><span class="extra">[经验值 {exp}] </span><span class="extra">[灌水数 {totalPost}]</span></div></div></div><p class="content">{content}</p><p class="time">@{ctime}</p><span class="profile">{profile}</span><a href="#reply_topic_dialog" data-role="button" rid="{rid}" class="_reply">回复Ta！</a><hr /></div>';

			var url = '/ng/m/topic/load_replies?offset={offset}&tid=${param.topicId}'.replace('{offset}', lastReplyId);

			$.ajax({
				url: url,
				type: 'GET',
				dataType: 'json',
				success: function(data) {
					$.each(data, function(i, item) {
						alert(data.rid);
						$(html
									.replace('{nick}', item.nick).replace('{u_title}', item.u_title)
									.replace('{groupName}', item.groupName).replace('{exp}', item.exp)
									.replace('{totalPost}', item.totalPost).replace('{content}', item.content)
									.replace('{ctime}', item.ctime).replace('{profile}', item.profile)
									.replace('{rid}', item._rid).replace('{floor}', lastFloor)).appendTo('#main');
						lastReplyId = item.rid;
					})
				}
			})
			$('._reply').button('refresh');
			$('#more').attr('floor', lastFloor);
			$('#more').attr('lastRid', lastReplyId);
		})
	});
</script>
</html>	