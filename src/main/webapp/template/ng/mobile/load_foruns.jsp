<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:forEach items="${replies}" var="r" varStatus="i">
<div class="reply" id="${r.id}" index="${i.index + floor}">
	<span class="floor">${i.index + floor}</span>
	<div>
		<div class="face">
			<img src="/resources/face.png" alt="${r.postUser.nick}" />
		</div>
		<div class="detail">
			<div>
				<span class="nick">${r.postUser.nick}</span>
				<span class="u_title">"${r.postUser.title}"</span>
			</div>
			<div>
				<span class="extra">[职务 ${r.postUser.groupNameDisplay}] </span>
				<span class="extra">[经验值 ${r.postUser.exp}] </span>
				<span class="extra">[灌水数 ${r.postUser.totalPostDisplay}]</span>
			</div>
		</div>
	</div>
	<p class="content">${r.contentBean.content}</p>
	<p class="time">@${r.postTime}</p>
	<span class="profile">-- ${r.postUser.idiograph}</span>
	<a href="#reply_topic_dialog" data-role="button">回复Ta！</a>
	<hr />
</div>
</c:forEach>