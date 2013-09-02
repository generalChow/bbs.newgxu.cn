/*
 * Copyright (c) 2001-2013 newgxu.cn <the original author or authors>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.newgxu.ng.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.domain.Forum;
import cn.newgxu.bbs.domain.Honor;
import cn.newgxu.bbs.domain.Reply;
import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.user.User;

/**
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-5-23
 * @version 0.1
 */
public class R {

	public static final String	ID		= "_ID";
	 public static final String DOMAIN = "http://bbs.newgxu.cn";
//	public static final String	DOMAIN	= "http://192.168.3.4";

	public static final class topic {
		public static final int		LATEST_TOPICS				= 0;
		public static final int		REFRESH						= 1;
		public static final int		FETCH_MORE					= 2;
		public static final int		FORUM_LATEST_TOPICS			= 3;
		public static final int		FORUM_REFRESH				= 4;
		public static final int		FORUM_FETCH_MORE			= 5;
		public static final int		CLASSICS					= -1;

		public static final String	TITLE						= "title";
		public static final String	ADDED_TIME					= "added_time";
		public static final String	CLICK_TIMES					= "click_times";
		public static final String	AUTHOR_NICK					= "author_nick";
		public static final String	REPLIED_TIMES				= "replied_times";
		public static final String	LAST_REPLIED_TIME			= "last_replied_time";
		public static final String	LAST_REPLIED_USER_NICK		= "last_replied_user_nick";

		public static final String	CONTENT						= "content";
		public static final String	FORUM						= "forum";
	}

	public static final class user {
		public static final String	USERNAME		= "username";
		public static final String	NICK			= "nick";
		public static final String	TITLE			= "title";
		public static final String	PORTRAIT		= "portrait";
		public static final String	DESC			= "desc";
		public static final String	LOGIN_TIMES		= "login_times";
		public static final String	LAST_LOGIN_TIME	= "last_login_time";
		public static final String	SEX				= "sex";
		public static final String	BIRTHDAY		= "birthday";
		public static final String	EMAIL			= "email";
		public static final String	HOMEPAGE		= "homepage";
		public static final String	QQ				= "qq";
		public static final String	PHONE			= "phone";
		public static final String	CURRENT_POWER	= "current_power";
		public static final String	MAX_POWER		= "max_power";
		public static final String	TOPICS_COUNT	= "topics_count";
		public static final String	REPLIES_COUNT	= "replies_count";
		public static final String	FANTASTIC_COUNT	= "FANTASTIC_COUNT";
		public static final String	EXPERIENCE		= "experience";
		public static final String	XDB				= "xdb";
		public static final String	REGISTER_TIME	= "register_time";
		public static final String	HONORS			= "honors";
	}

	public static final class reply {
		public static final int		LATEST				= 0;

		public static final String	TID					= "tid";
		public static final String	POST_UID			= "post_uid";
		public static final String	POST_USER_NICK		= "post_user_nick";
		public static final String	POST_USER_PORTRAIT	= "post_user_portrait";
		public static final String	TOPIC_TITLE			= "topic_title";
		public static final String	POST_TIME			= "post_time";
		public static final String	CONTENT				= "content";
	}

	public static final class forum {
		public static final String	NAME				= "name";
		public static final String	ABOUT				= "about";
		public static final String	MONEY_PER_TOPIC		= "money_per_topic";
		public static final String	MONEY_PER_REPLY		= "money_per_reply";
		public static final String	EXP_PER_TOPIC		= "exp_per_topic";
		public static final String	EXP_PER_REPLY		= "exp_per_reply";
		public static final String	TOTAL_TOPICS_COUNT	= "total_topics_count";
		public static final String	FANTASY_COUNT		= "fantasy_count";
		public static final String	HOT					= "hot";
		public static final String	MASTERS				= "masters";
	}

	public static final Map<String, Object> resolveReply2Map(Reply r) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(ID, r.getId());
		m.put(reply.TID, r.getTopic().getId());
		m.put(reply.POST_UID, r.getPostUser().getId());
		m.put(reply.POST_USER_NICK, r.getPostUser().getNick());
		m.put(reply.POST_USER_PORTRAIT, resolveFaceFromHtml(r.getPostUser()
				.getFace()));
		m.put(reply.POST_TIME, r.getPostTime().getTime());
		m.put(reply.TOPIC_TITLE, r.getTopic().getTitle());
		m.put(reply.CONTENT, bringBackDomain2Content(r.getContentFilter()));
		return m;
	}

	public static final Map<String, Object> resolveUser2Map(User u) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(ID, u.getId());
		m.put(user.USERNAME, u.getUsername());
		m.put(user.NICK, u.getNick());
		m.put(user.TITLE, u.getTitle());
		m.put(user.PORTRAIT, resolveFaceFromHtml(u.getFace()));
		m.put(user.DESC, u.getIdiograph());
		m.put(user.LOGIN_TIMES, u.getLoginTimes());
		m.put(user.LAST_LOGIN_TIME, u.getLastLoginTime().getTime());
		m.put(user.SEX, u.isSex());
		m.put(user.BIRTHDAY, u.getBirthday().getTime());
		m.put(user.EMAIL, u.getEmail());
		m.put(user.HOMEPAGE, u.getHomepage());
		m.put(user.QQ, u.getQq());
		m.put(user.PHONE, u.getTel());
		m.put(user.CURRENT_POWER, u.getCurrentPower());
		m.put(user.MAX_POWER, u.getMaxPower());
		m.put(user.TOPICS_COUNT, u.getNumberOfTopic());
		m.put(user.REPLIES_COUNT, u.getNumberOfReply());
		m.put(user.FANTASTIC_COUNT, u.getNumberOfGood());
		m.put(user.EXPERIENCE, u.getExp());
		m.put(user.XDB, u.getMoney());
		m.put(user.REGISTER_TIME, u.getRegisterTime().getTime());
		List<Honor> honors = u.getHonors();
		String str = "";
		for (Honor h : honors) {
			str += h.getName() + "\n";
		}
		m.put(user.HONORS, str);
		return m;
	}

	public static final Map<String, Object> resolveTopic2Map(Topic t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(R.topic.ADDED_TIME, t.getCreationTime().getTime());
		map.put(R.topic.AUTHOR_NICK, t.getUser().getNick());
		map.put(R.topic.CLICK_TIMES, t.getClickTimes());
		map.put(R.ID, t.getId());
		map.put(R.topic.LAST_REPLIED_TIME, t.getReplyTime().getTime());
		map.put(R.topic.LAST_REPLIED_USER_NICK, t.getReplyUser().getNick());
		map.put(R.topic.REPLIED_TIMES, t.getReplyTimes());
		map.put(R.topic.TITLE, t.getTitle());
		return map;
	}

	public static final String	IMAGE_PATTERN	= "src=(.+)\\s";
	private static Pattern		pattern			= Pattern
														.compile(IMAGE_PATTERN);
	private static Matcher		matcher;

	public static final String resolveFaceFromHtml(String html) {
		// <img name=face src=/images/upload_files/2006-12-6/1.jpg border=1>
		if (html == null || html.length() < 1) {
			return null;
		}
		matcher = pattern.matcher(html);
		if (matcher.find()) {
			return DOMAIN + matcher.group(1);
		} else {
			return null;
		}
	}

	private static final Pattern	p	= Pattern
												.compile("(?i)src=/(.+?\\..{3,4})\\s");
	private static Matcher			m;

	/** 将html片段中的图片相对地址换成包含域名的绝对地址 */
	public static final String bringBackDomain2Content(String content) {
		m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String src = m.group(1);
			System.out.println(src);
			m.appendReplacement(sb, "src='" + DOMAIN + "/" + src + "' ");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static final Map<String, Object> resolveForum2Map(Forum f) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(R.ID, f.getId());
		m.put(forum.ABOUT, f.getDescription());
		m.put(forum.EXP_PER_REPLY, f.getReplyExp());
		m.put(forum.EXP_PER_TOPIC, f.getTopicExp());
		m.put(forum.FANTASY_COUNT, f.getFantasyCount());
		m.put(forum.MONEY_PER_REPLY, f.getReplyMoney());
		m.put(forum.MONEY_PER_TOPIC, f.getTopicMoney());
		m.put(forum.TOTAL_TOPICS_COUNT, f.getTopicsByForum());
		m.put(forum.NAME, f.getName());
		m.put(forum.HOT, f.isHot());

		List<User> masters = f.getWebmasters();
		String masterNames = "";
		for (User u : masters) {
			masterNames += u.getNick() + " ";
		}
		m.put(forum.MASTERS, masterNames);
		return m;
	}

}
