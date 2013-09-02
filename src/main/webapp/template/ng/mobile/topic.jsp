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
				border: none;
				border-radius: 4px;
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
			<a href="#" data-icon="back" id="back">返回</a>
			<h1>${model.topic.title}</h1>
			<a href="#" data-icon="refresh" id="refresh">刷新</a>
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
									${r.reply.postUser.faceDisplay}
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
							<c:if test="${sessionScope._user.id eq r.reply.postUser.id}">
								<a href="/ng/m/topic/try_update?topicId=${model.topic.id}&forumId=${model.topic.forum.id}&replyId=${r.reply.id}" data-role="button" data-ajax="false">修改我的帖子</a>
							</c:if>
							<hr />
						</div>
					</c:when>
					<c:otherwise>
						<div class="reply" id="${r.reply.id}" floor="${i.index}">
							<span class="floor">${r.floor}</span>
							<div>
								<div class="face">
									${r.reply.postUser.faceDisplay}
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
							<a href="" data-role="button" rid="${r.reply.id}" data-ajax="false" data-transition="pop" class="_reply">回复Ta！</a>
							<c:if test="${sessionScope._user.id eq r.reply.postUser.id}">
								<a href="/ng/m/topic/try_update?topicId=${model.topic.id}&forumId=${model.topic.forum.id}&replyId=${r.reply.id}" data-role="button" data-ajax="false">修改我的发言</a>
							</c:if>
							<hr />
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- <button id="more" lastRid="${last_rid}" floor="${count}">点击查看更多回复！</button> -->
			<div id="pagination">
				${model.pagination}
			</div>
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
			<h1>回复</h1>
		</div>
		<div data-role="content">
			<form action="/ng/m/topic/reply" method="post" data-transition="flip" data-ajax="true" id="reply">
				<div data-role="fieldcontain">
				    <label for="content">回复内容(引用的内容不必删除！):</label>
			        <textarea name="content" id="content"></textarea>
		        </div>
		        <input name="forumId" type="hidden" id="forumId" value="${param.forumId}" />
				<input name="topicId" type="hidden" id="topicId" value="${param.topicId}" />
				<input type="hidden" id="replyId" name="replyId" value="0" />
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
							window.location.reload();
						} else {
							alert("发表回复失败！原因：" + data.info);
						}
					},
					error: function() {
						alert('出现了问题，请稍后再试！提示：您是否登陆成功？');
					}
				})
			return false;
		});

		$('a._reply').click(function() {
			var topicId = ${param.topicId};
			var forumId = ${param.forumId};
			var replyId = $(this).attr('rid');
			var page = ${model.pagination.page};

			$.getJSON('/ng/m/topic/request_reply', {
				topicId: topicId,
				forumId: forumId,
				replyId: replyId,
				page: page
			}, function(data) {
				if (data.status === 'ok') {
					$('#reply_dialog #content').val(data.content);
					$('#reply_dialog #replyId').val(data.reply_id);
					var str = window.location.href.replace('#reply_dialog', '') + '#reply_dialog';
					window.location.href = str;
				} else {
					alert('回复失败，原因：' + data.info + " . 请稍后再试！");
				}
			})

			$.ajax({

			})
			return false;
		})

		$('form#reply #submit').click(function() {
			var params = $('form#reply').serialize();
			$.ajax({
				url: '/ng/m/topic/reply',
				data: params,
				dataType: 'json',
				type: 'POST',
				success: function(data) {
					if (data.status === 'ok') {
						alert("回复成功！");
						window.location.href = window.location.href.replace('#reply_dialog', '');
					} else {
						alert('回复失败！原因：' + data.info);
					}
				},
				error: function() {
					alert("出现了问题，请稍后再试！");
				}
			})
			return false;
		})
	});
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