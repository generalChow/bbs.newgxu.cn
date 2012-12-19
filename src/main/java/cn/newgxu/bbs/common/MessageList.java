package cn.newgxu.bbs.common;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.exception.BBSExceptionMessage;


/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MessageList {

	private static final String DEFAULT_URL = "/";

	private String url = DEFAULT_URL;

	private List<String> messageList = new LinkedList<String>();

	public boolean isEmpty() {
		return messageList.isEmpty();
	}

	public int size() {
		return messageList.size();
	}

	public List<String> getMessages() {
		return messageList;
	}

	public void setUrl(Object... args) {
		List<Pair> params = new LinkedList<Pair>();
		for (Object o : args) {
			if (o instanceof String) {
				url = (String) o;
			} else if (o instanceof Pair) {
				params.add((Pair) o);
			}
		}
		_setUrl(params);
	}

	private void _setUrl(List<Pair> params) {
		for (Pair pair : params) {
			url = StringUtils.replace(url, pair.regex, pair.value);
		}
	}

	public void addMessage(Object... args) {
		String msg = "";
		List<Pair> params = new LinkedList<Pair>();
		for (Object o : args) {
			if (o instanceof String) {
				msg = (String) o;
			} else if (o instanceof Pair) {
				params.add((Pair) o);
			}
		}
		_addMessage(msg, params);
	}

	private void _addMessage(String msg, List<Pair> params) {
		for (Pair pair : params) {
			msg = StringUtils.replace(msg, pair.regex, pair.value);
		}
		messageList.add(msg);
	}

	public static Pair P(String code, String value) {
		return new Pair(code, value);
	}

	public static Pair P(String code, int value) {
		return new Pair(code, String.valueOf(value));
	}

	public static Pair P(String code, boolean value) {
		return new Pair(code, String.valueOf(value));
	}

	public static Pair P(String code, long value) {
		return new Pair(code, String.valueOf(value));
	}

	public static Pair P(String code, double value) {
		return new Pair(code, String.valueOf(value));
	}

	public static class Pair {
		String regex;

		String value;

		public Pair(String regex, String value) {
			this.regex = regex;
			this.value = value;
		}

		public String toString() {
			return "" + regex + "=>" + value;
		}
	}

	public String getUrl() {
		return url;
	}
	
	public static MessageList getSessionIsInvalidateMessageList() {
		MessageList m = new MessageList();
		m.addMessage(BBSExceptionMessage.MESSAGE_SESSION_IS_INVALIDATED);
		return m;
	}

	public static MessageList getSayHelloMessageList() {
		MessageList m = new MessageList();
		m.addMessage("Hello! What a nice day!");
		return m;
	}

}
