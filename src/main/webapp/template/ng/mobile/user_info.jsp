<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html>
	<head>
		<jsp:include page="mobile-manifest.jsp" />
		<style type="text/css">
			#title {
				text-align: center;
			}
		</style>
	</head>
<body>
	<div data-role="page" id="user_info">
		<div data-role="header">
			<a href="#" data-icon="back" id="back">返回</a>
			<h1>用户信息</h1>
			<a href="#" data-icon="refresh">刷新</a>
		</div>
		<div data-role="content">
			<h2 id="title">${u.nick}' s 信息</h2>

			${u.faceDisplay}

			<section class="ui-grid-b">
				<!-- Row 1 -->
				<div class="ui-block-a">昵称</div>
				<div class="ui-block-b">${u.nick}</div>
				<!-- Row 2 -->
				<div class="ui-block-a">QQ</div>
				<div class="ui-block-b">${u.qq}</div>

				<div class="ui-block-a">头衔</div>
				<div class="ui-block-b">${u.title}</div>

				<div class="ui-block-a">经验/钱</div>
				<div class="ui-block-b">${u.exp}/${money}</div>

				<div class="ui-block-a">性别</div>
				<div class="ui-block-b">
					<c:choose>
						<c:when test="${u.sex eq false}">
							女
						</c:when>
						<c:otherwise>
							男
						</c:otherwise>
					</c:choose>
				</div>

				<div class="ui-block-a">生日</div>
				<div class="ui-block-b">${u.birthday}</div>

				<div class="ui-block-a">E-mail</div>
				<div class="ui-block-b">${u.email}</div>

				<div class="ui-block-a">个人主页</div>
				<div class="ui-block-b">${u.homepage}</div>

				<div class="ui-block-a">注册时间</div>
				<div class="ui-block-b">${u.registerTime}</div>

				<div class="ui-block-a">登录次数</div>
				<div class="ui-block-b">${u.loginTimes}</div>

				<div class="ui-block-a">发帖/回复/精华</div>
				<div class="ui-block-b">${u.numberOfTopic}/${u.numberOfReply}/${u.numberOfGood}</div>

				<div class="ui-block-a">最大/剩余体力</div>
				<div class="ui-block-b">${u.maxPower}/${u.currentPower}</div>

				<div class="ui-block-a">等级</div>
				<div class="ui-block-b">${u.groupNameDisplay}</div>
			</section>
		</div>
	</div>
</body>
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