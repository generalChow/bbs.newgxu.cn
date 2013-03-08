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
		  <h1>发表新帖子</h1>
		</div>
  
		<div data-role="content">
	   		<form action="/ng/m/topic/submit" method="post" data-transition="none">
		    	<div data-role="fieldcontain">
			        <label for="areaId">分区:</label>
			        <select name="areaId" id="areaId" data-native-menu="false" data-overlay-theme="e">
			        	<option value="0" data-placeholder="true">请选择主题所要发表在的分区</option>
			        	<c:forEach items="${model.areas}" var="a">
			        		<option value="${a.id}">${a.name}</option>
			        	</c:forEach>
			        </select>
		        </div>

		        <div data-role="fieldcontain">
			        <label for="forumId">板块:</label>
			        <select name="forumId" id="forumId" data-native-menu="false" data-overlay-theme="e">
			        	<option value="0" data-placeholder="true">请选择主题所要发表在的板块</option>
			        	<!-- todo jason!!! -->

			        </select>
		        </div>

		        <div data-role="fieldcontain">
			        <label for="type">类型:</label>
			        <select name="type" id="type"  data-native-menu="false" data-overlay-theme="e">
			        	  <option value="0" data-placeholder="true">请选择帖子的类型</option>
			        	  <option value="【原创】">【原创】</option>
                          <option value="【转帖】">【转帖】</option>
                          <option value="【灌水】">【灌水】</option>
                          <option value="【讨论】">【讨论】</option>
                          <option value="【发布】">【发布】</option>
                          <option value="【求助】">【求助】</option>
                          <option value="【推荐】">【推荐】</option>
                          <option value="【公告】">【公告】</option>
                          <option value="【注意】">【注意】</option>
                          <option value="【贴图】">【贴图】</option>
                          <option value="【影片】">【影片】</option>
                          <option value="【音乐】">【音乐】</option>
                          <option value="【动画】">【动画】</option>
                          <option value="【游戏】">【游戏】</option>
                          <option value="【建议】">【建议】</option>
                          <option value="【下载】">【下载】</option>
                          <option value="【分享】">【分享】</option>
                          <option value="【教学】">【教学】</option>
                          <option value="【测试】">【测试】</option>
                          <option value="【插件】">【插件】</option>
                          <option value="【风格】">【风格】</option>
			        </select>
		        </div>

		        <div data-role="fieldcontain">
			        <label for="title">标题:</label>
			        <input type="text" name="title" id="title" value="" required />
		        </div>
		       
				<div data-role="fieldcontain">
				    <label for="content">帖子内容:</label>
			        <textarea cols="40" rows="8" name="content" id="content"></textarea>
		        </div>

			    <div data-role="fieldcontain">
			        <label for="zt">转帖or原创</label>
			        <select name="zt" id="zt" data-role="slider">
				        <option value="false">原创</option>
				        <option value="true">转帖</option>
			        </select>
		        </div>
		        <input type="submit" value="Send" data-theme="a">
		    </form>
		    <div id="t"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		var person = {
			"name": "longkai",
			"age": "21"
		}
		$('#areaId').change(function() {
			var areaId = $(this).val();

			$.getJSON('/ng/m/topic/load_forums?areaId=' + areaId, function(data) {
				$.each(data, function(i, item) {
					$('<div>').html(item.name).appendTo('#t');
				})
			})
			// $('#t').load('/ng/m/topic/load_forums?areaId=1');
		});
	})
</script>
</html>	