/*
 * Edit by udk @ByTheWay-PC
 * 2010-6-8
 *   function detail 
 *   询问操作员以如何方式发送删除原因    默认 或者 自定义
 * */
function execOperate(url, topicId, userId,action,targetForum) {
	if (confirm("是否发送消息给网友?")) {
		var width = 700;
		var height = 500;
		var left = eval(screen.Width - width) / 2;
		var top = eval(screen.Height - height) / 2;
		MM_openBrWindow("/message/send_message.yws?userId=" + userId
				+ "&topicId=" + topicId+"&action="+action+"&targetForumId="+targetForum, '发送短消息',
				'menubar=yes,scrollbars=yes,width=' + width + ',height='
						+ height + ',left=' + left + ',top=' + top + '');
	}
	if(url!='')
		window.location = url;
	else
		return true;
}