package cn.newgxu.bbs.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.MessageList;
import cn.newgxu.bbs.common.filter.FilterUtil;

import com.opensymphony.util.TextUtils;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class Util {

	private static final Log log = LogFactory.getLog(Util.class);

	private final static int ENCODE_XORMASK = 820103;

	private final static char ENCODE_DELIMETER = '\002';

	private final static char ENCODE_CHAR_OFFSET1 = 'X';

	private final static char ENCODE_CHAR_OFFSET2 = 'y';

	public static final long ONE_SECOND = 1000L;

	public static final long ONE_MINUTE = 60 * ONE_SECOND;

	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	public static final long ONE_DAY = 24 * ONE_HOUR;

	public static final long ONE_WEEK = 7 * ONE_DAY;

	public static final long ONE_MONTH = 30 * ONE_DAY;

	public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String[] allowedExt = new String[] { "jpg", "gif",
			"png" };

	private static int UPLOAD_FILE_TOKEN = 0;

	private static WebApplicationContext wac;

	private static final char[] RANDOM_CHARS = { '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'm', 'p',
			'r', 't', 'w' };/*
							 * , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
							 * 'K', 'M', 'P', 'R', 'T', 'W' };
							 */

//	private static final String STATIC_DOMAIN = Constants.SCREEN_CONTENT
//			.startsWith("http://") ? Constants.SCREEN_CONTENT : "http://"
//			+ Constants.STATIC_DOMAIN;

	/**
	 * hash 方法使用。
	 */
	private static MessageDigest digest = null;

	/**
	 * 将输入字符串按MD5运算法则转换成 hash 字符串，返回的结果是16进制的数字。 这个方法是一个同步的方法，以防止过多的
	 * MessageDigest 对象产生。 如果使用这个方法成为了系统的瓶颈，那么可以考虑使用 MessageDigest 对象池 来消除瓶颈。
	 * <p>
	 * 这个hash()方法是一个单向的加密方法。加密字符串不能还原。
	 * <p>
	 * 在论坛中，每次用户登录的时候，都将用户输入的纯文本密码字符串用这个方法
	 * 加密成hash字符串。即使黑客得到了加密以后储存的用户密码字符串，也很难破解。
	 * <p>
	 * 加密过后的字符串长度为32位。
	 * 
	 * @param data
	 *            要加密的字符串。
	 * @return 一个hash字符串。
	 */
	public synchronized static String hash(String data) {
		if (data == null) {
			return null;
		}
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
		// 转换成 hash 字符串。
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            要转换的字节数组。
	 * @return 转换后的十六进制字符串。
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	public static String encodePasswordCookie(String username, String password) {
		StringBuffer buf = new StringBuffer();
		if (username != null && password != null) {
			byte[] bytes = (username + ENCODE_DELIMETER + password).getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}
		return buf.toString();
	}

	public static String[] decodePasswordCookie(String cookieVal) {
		if (cookieVal == null || cookieVal.length() <= 0) {
			return null;
		}

		// 解析COOKIE的值
		char[] chars = cookieVal.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int b;
		for (int n = 0, m = 0; n < bytes.length; n++) {
			b = chars[m++] - ENCODE_CHAR_OFFSET1;
			b |= (chars[m++] - ENCODE_CHAR_OFFSET2) << 4;
			bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
		}
		cookieVal = new String(bytes);
		int pos = cookieVal.indexOf(ENCODE_DELIMETER);
		String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
		String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

		return new String[] { username, password };
	}

	public static final String formatSellTime(Date overdueTime) {
		long distance = overdueTime.getTime() - new Date().getTime();
		if (distance <= ONE_HOUR) {
			return "＜1小时";
		} else if (distance >= Constants.FREE_MARKET_SELL_DAY * ONE_DAY) {
			return "＞" + Constants.FREE_MARKET_SELL_DAY + "天";
		} else if (distance >= ONE_DAY) {
			return "＜" + (distance / ONE_DAY) + "天";
		} else {
			return "＜" + (distance / ONE_HOUR) + "小时";
		}
	}

	public static final String formatTime(Date currentTime, Date lastTime) {
		if (!isSameYear(currentTime, lastTime)) {
			return formatTime(DATE_TIME_FORMAT, lastTime);
		}
		long distance = currentTime.getTime() - lastTime.getTime();
		if (distance >= ONE_MONTH) {
			return distance / ONE_MONTH + "个月前";
		} else if (distance < ONE_MONTH && distance >= ONE_WEEK) {
			return distance / ONE_WEEK + "星期前";
		} else if (distance < ONE_WEEK && distance >= ONE_DAY) {
			return distance / ONE_DAY + "天前";
		} else if (distance < ONE_DAY && distance >= ONE_HOUR) {
			return distance / ONE_HOUR + "小时前";
		} else if (distance < ONE_HOUR && distance >= ONE_MINUTE) {
			return distance / ONE_MINUTE + "分钟前";
		} else {
			return "1分钟内";
		}
	}

	public static final String formatDateTime(Date dt) {
		return formatTime(DEFAULT_DATE_TIME, dt);
	}

	public static final String formatTime(Date lastTime) {
		return formatTime(new Date(), lastTime);
	}

	public static String formatTime(String source, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(source);
		return formatter.format(date);
	}

	public static boolean isSameYear(Date currentTime, Date lastTime) {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentTime);

		Calendar lastCalendar = Calendar.getInstance();
		lastCalendar.setTime(lastTime);

		return currentCalendar.get(Calendar.YEAR)
				- lastCalendar.get(Calendar.YEAR) <= 0;
	}

	public static final int days(Date beginTime, Date endTime) {
		return (int) ((endTime.getTime() - beginTime.getTime()) / ONE_DAY);
	}

	public static void setWebApplicationContext(WebApplicationContext context) {
		wac = context;
	}
	
	public static WebApplicationContext getWebApplicationContext() {
		return wac;
	}

	public static Object getBean(String name) {
		return wac.getBean(name);
	}

	public static String getFilenameExtension(String filename) {
		File file = new File(filename.toLowerCase());
		if (log.isDebugEnabled()) {
			log.debug("filename:" + filename + ", ext:"
					+ StringUtils.substringAfterLast(file.getName(), "."));
		}
		return StringUtils.substringAfterLast(file.getName(), ".");
	}

	private static String makeDirs(String ext) {
		StringBuffer sb = new StringBuffer();
		Calendar c = Calendar.getInstance();
		sb.append(Constants.FILE_ROOT).append("/upload/").append(ext).append(
				"/").append(c.get(Calendar.YEAR)).append("/").append(
				c.get(Calendar.MONTH) + 1).append("/").append(
				c.get(Calendar.DAY_OF_MONTH)).append("/").append(
				c.get(Calendar.HOUR_OF_DAY));
		String path = sb.toString();
		File file = new File(path);
		file.mkdirs();

		return file.toString();
	}

	private static int getFileIndex() {
		return UPLOAD_FILE_TOKEN++;
	}

	/**
	 * 修改记录：
	 * 之前是在文件名前加一个    $
	 * 可以这个  $ 在编辑器中会使用 img 标签的 src 属性不正常，现在去掉
	 * 
	 * 2011.10.11 集成显卡
	 * 
	 * @param ext
	 * @return
	 */
	public static String getUploadFilePath(String ext) {
		String path = makeDirs(ext);
		StringBuffer sb = new StringBuffer(path.length() + 20);
		sb.append(path).append("/@").append(System.currentTimeMillis()).append(
				"_").append(getFileIndex()).append(".").append(ext);
		return sb.toString();
	}

	public static String getUploadFacePath(String ext) {
		StringBuffer sb = new StringBuffer(
				Constants.FILE_FACE_ROOT.length() + 20);
		sb.append(Constants.FILE_FACE_ROOT).append("/images/user_face/")
				.append(System.currentTimeMillis()).append("_").append(
						getFileIndex()).append(".").append(ext);
		return sb.toString();
	}

	public static String getSafeFilename(String filename) {
		int safeLength = 254;
		if (filename.length() > safeLength) {
			return StringUtils.right(filename, safeLength);
		} else {
			return filename;
		}
	}

	public static String getUriFromStoragePath(String storagePath) {
		// if (log.isDebugEnabled()) {
		// log.debug("storagePath=" + storagePath);
		// log.debug("result="
		// + STATIC_DOMAIN
		// + StringUtils.remove(storagePath.replaceAll("\\\\", "/"),
		// Constants.FILE_ROOT));
		// }
		System.out.println("-------->"+storagePath.startsWith(Constants.FILE_ROOT.replaceAll("/","\\\\")));
		return storagePath.startsWith(Constants.FILE_ROOT.replaceAll("/",
				"\\\\")) ?
		// STATIC_DOMAIN+
		StringUtils.remove(storagePath.replaceAll("\\\\", "/"),
				Constants.FILE_ROOT)
				: StringUtils.remove(storagePath.replaceAll("\\\\", "/"),
						Constants.FILE_ROOT);
	}

	public static String getFaceUriFromStoragePath(String storagePath) {
		if (log.isDebugEnabled()) {
			log.debug("storagePath=" + storagePath);
			log.debug("result="
			// + STATIC_DOMAIN
					+ StringUtils.remove(storagePath.replaceAll("\\\\", "/"),
							Constants.FILE_FACE_ROOT));
		}
		return StringUtils.remove(storagePath.replaceAll("\\\\", "/"),
				Constants.FILE_FACE_ROOT);
	}

	public static void saveCookie(HttpServletResponse response,
			String username, String password, boolean isAutoLogin) {
		if (password == null) {
			password = "";
		}
		Cookie cookie = null;
		try {
			cookie = new Cookie(Constants.AUTH_USER_COOKIE, URLEncoder.encode(
					encodePasswordCookie(username, password), "utf8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie autoLogin = new Cookie(Constants.AUTOLOGIN_COOKIE, String
				.valueOf(isAutoLogin));
		if (isAutoLogin) {
			cookie.setMaxAge(Integer.MAX_VALUE);
			autoLogin.setMaxAge(Integer.MAX_VALUE);
		}
		cookie.setPath("/");
		autoLogin.setPath("/");
		response.addCookie(cookie);
		response.addCookie(autoLogin);
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i];
			}
		}
		return null;
	}

	public static Date getCurrentTime() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	public static void putMessageList(MessageList messageList,
			HttpSession session) {
		if (log.isDebugEnabled()) {
			log.debug("set session attribute:" + messageList);
			log.debug(session);
		}
		SessionUtil.setAttribute(session, Constants.MESSAGE_SESSION,
				messageList);
	}

	public static MessageList getMessageList(HttpSession session) {
		MessageList messageList = (MessageList) SessionUtil.getAttribute(
				session, Constants.MESSAGE_SESSION, MessageList
						.getSessionIsInvalidateMessageList());
		if (messageList == null) {
			messageList = MessageList.getSayHelloMessageList();
		}

		return messageList;
	}

	public static Date getDateAfterDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	public static Date getDateAfterDay(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	public static Date getDateAfterHour(int hour) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	public static Date getDateAfterMinute(int minute) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static Date getDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.clear();

		c.set(year, month, day);

		return c.getTime();
	}

	// 增加此方法用于注册时间段的搜索 2010-04-07 23:50
	public static String getDisignDate(String start, int day) {
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(start);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			ca.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			ca.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
			ca.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + day);
			date = ca.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	private static double getAgio(int agio) {
		return (double) agio / 100;
	}

	public static int reckonTax(int money, int taxRate) {
		return (int) ((double) money * ((double) taxRate / 100));
	}

	public static int reckonTax(int money, int taxRate, int agio) {
		return (int) (getAgio(agio) * reckonTax(money, taxRate));
	}

	public static int reckonAfterTax(int money, int taxRate) {
		return money - reckonTax(money, taxRate);
	}

	public static int reckonAfterTax(int money, int taxRate, int agio) {
		return money - reckonTax(money, taxRate, agio);
	}

	public static int reckonCost(int money, int agio) {
		return (int) (getAgio(agio) * money);
	}

	public static String getRandomString() {
		StringBuffer sb = new StringBuffer(Constants.RANDOM_STRING_LENGTH);
		for (int i = 0; i < Constants.RANDOM_STRING_LENGTH; i++) {
			Random r = new Random();
			sb.append(RANDOM_CHARS[r.nextInt(RANDOM_CHARS.length)]);
		}
		return sb.toString();
	}

	public static void saveValidCode(HttpSession session, String code) {
		SessionUtil.setAttribute(session, Constants.VALID_CODE_SESSION, code);
	}

	public static String getValidCode(HttpSession session) {
		return (String) SessionUtil.getAttribute(session,
				Constants.VALID_CODE_SESSION);
	}

	public static boolean equalsValidCode(String input, String code) {
		if (StringUtils.isEmpty(input)) {
			return false;
		}
		return input.equalsIgnoreCase(code);
	}

	public static boolean equalsIDCard(String IDCard1, String IDCard2) {
		IDCard1 = StringUtils.replace(IDCard1, "x", "X");
		IDCard2 = StringUtils.replace(IDCard2, "x", "X");
		return IDCard2.equalsIgnoreCase(IDCard1);
	}

	public static List<String> splitOptions(String str) {
		str = FilterUtil.optionString(str);
		str = StringUtils.replace(str, "|", "｜");
		str = StringUtils.replaceChars(str, "\r\n", "\r");
		str = StringUtils.replaceChars(str, "\n", "\r");

		String[] options = str.split("\r");
		List<String> result = new LinkedList<String>();
		int i = 0;
		for (String option : options) {
			if (!StringUtils.isWhitespace(option)
					&& i < Constants.VOTE_OPTION_SIZE) {
				result.add(StringUtils.left(TextUtils.innerTrim(option).trim(),
						Constants.VOTE_OPTION_LENGTH));
				i++;
			}
		}
		return result;
	}

	public static List<String> splitNicks(String str) {
		str = StringUtils.replaceChars(str, "\r\n", "，");
		str = StringUtils.replaceChars(str, "\n", "，");
		String[] nicks = str.split("，");
		List<String> result = new LinkedList<String>();
		for (String nick : nicks) {
			if (!StringUtils.isWhitespace(nick)
					&& !result.contains(TextUtils.innerTrim(nick).trim())) {
				result.add(TextUtils.innerTrim(nick).trim());
			}
		}
		return result;
	}

	public static int[] splitIds(String str) {
		str = StringUtils.replace(str, "|", ",");
		str = StringUtils.replace(str, "&", ",");
		String[] options = str.split(",");

		int result[] = new int[options.length];
		int i = 0;
		for (String option : options) {
			result[i] = Integer.parseInt(option);
			i++;
		}

		return result;
	}

	public static String intsToString(int[] ints) {
		if (ints == null || ints.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i : ints) {
			sb.append("," + i);
		}
		if (sb != null) {
			sb.replace(0, 1, "");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String s = "1234567我";
		System.out.println(s.getBytes().length);
	}

	public static String getDayOfWeek(String theDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(theDate);
		} catch (ParseException e) {

		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
		String showDate = "";
		switch (mydate) {
		case 1:
			showDate = "星期日";
			break;
		case 2:
			showDate = "星期一";
			break;
		case 3:
			showDate = "星期二";
			break;
		case 4:
			showDate = "星期三";
			break;
		case 5:
			showDate = "星期四";
			break;
		case 6:
			showDate = "星期五";
			break;
		default:
			showDate = "星期六";
			break;
		}
		return showDate;
	}

	@SuppressWarnings("deprecation")
	public static Date getUitDate() {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	public static String toGB(String toGB) {
		if (toGB == null) {
			return null;
		} else {
			try {
				toGB = new String(toGB.getBytes("UTF-8"), "gbk");
			} catch (Exception exception) {
			}
		}

		return toGB;
	}

	public static boolean checkFileExt(String ext) {
		int allowFlag = 0;
		int allowedExtCount = allowedExt.length;
		for (; allowFlag < allowedExtCount; allowFlag++) {
			if (allowedExt[allowFlag].equals(ext))
				return true;
		}
		return false;
	}
}
