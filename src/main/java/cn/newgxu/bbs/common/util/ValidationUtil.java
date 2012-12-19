package cn.newgxu.bbs.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.exception.ValidationException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ValidationUtil {

	public static void username(String username) throws ValidationException {
		if (StringUtils.isEmpty(username)) {
			throw new ValidationException(BBSExceptionMessage.USERNAME_EMPTY);
		}
		if (username.length() < 2) {
			throw new ValidationException(
					BBSExceptionMessage.USERNAME_TOO_SHORT);
		}
		if (username.length() > 16) {
			throw new ValidationException(BBSExceptionMessage.USERNAME_TOO_LONG);
		}
		
		if (!username.matches("([a-zA-Z0-9_])*")) {
			throw new ValidationException("账号不能包含非法字符！");
		}
	}

	public static void password(String password, String password2)
			throws ValidationException {
		if (StringUtils.isEmpty(password)) {
			throw new ValidationException(BBSExceptionMessage.PASSWORD_EMPTY);
		}
		if (password.length() < 6) {
			throw new ValidationException(
					BBSExceptionMessage.PASSWORD_TOO_SHORT);
		}
		if (!password.equals(password2)) {
			throw new ValidationException(
					BBSExceptionMessage.PASSWORD_NOT_EQUALS);
		}
	}

	public static void nick(String nick) throws ValidationException {
		if (StringUtils.isEmpty(nick)) {
			throw new ValidationException(BBSExceptionMessage.NICK_EMPTY);
		}

		if (nick.length() < 2) {
			throw new ValidationException(BBSExceptionMessage.NICK_TOO_SHORT);
		}

		if (nick.length() > 8) {
			throw new ValidationException(BBSExceptionMessage.NICK_TOO_LONG);
		}
	}

	public static void truename(String truename) throws ValidationException {
		if (StringUtils.isEmpty(truename)) {
			throw new ValidationException(BBSExceptionMessage.TRUENAME_EMPTY);
		}
		if (truename.length() > 16) {
			throw new ValidationException(BBSExceptionMessage.TRUENAME_TOO_LONG);
		}
	}

	public static void email(String email) throws ValidationException {
		if (StringUtils.isEmpty(email)) {
			throw new ValidationException(BBSExceptionMessage.EMAIL_EMPTY);
		}
		if (email.length() > 50) {
			throw new ValidationException(BBSExceptionMessage.EMAIL_TOO_LONG);
		}
	}

	public static void title(String title) throws ValidationException {
		if (StringUtils.isEmpty(title)) {
			throw new ValidationException(BBSExceptionMessage.TITLE_EMPTY);
		}
		if (title.length() < 4) {
			throw new ValidationException(BBSExceptionMessage.TITLE_TOO_SHORT);
		}
		if (title.length() > 50) {
			throw new ValidationException(BBSExceptionMessage.TITLE_TOO_LONG);
		}
	}

	public static void content(String content) throws ValidationException {
		if (StringUtils.isEmpty(content)) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_EMPTY);
		}
		if (content.length() < 5) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_TOO_SHORT);
		}
		if (content.length() > 100000) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_TOO_LONG);
		}
	}

	public static void storeItemNumber(int number) throws ValidationException {
		if (number < 1) {
			throw new ValidationException(BBSExceptionMessage.VALID_NUMBER_ONE);
		}
	}

	public static void freeMarketSellPrice(int price)
			throws ValidationException {
		if (price > Constants.FREE_MARKET_SELL_MAX_PRICE
				|| price < Constants.FREE_MARKET_SELL_MIN_PRICE) {
			throw new ValidationException(
					BBSExceptionMessage.FREE_MARKET_SELL_PRICE_INVALIDATION);
		}
	}

	public static void options(List<String> options) throws ValidationException {
		if (options.isEmpty()) {
			throw new ValidationException(
					BBSExceptionMessage.VOTE_OPTIONS_EMPTY);
		}
		if (options.size() > Constants.VOTE_OPTION_SIZE) {
			throw new ValidationException(
					BBSExceptionMessage.OUT_OF_VOTE_OPTIONS_MAX_SIZE);
		}
	}

	public static void openMoney(int money) throws ValidationException {
		if (money < 1) {
			throw new ValidationException(
					BBSExceptionMessage.OPEN_ACCOUNTS_MONEY);
		}
	}

	public static void money(int money) throws ValidationException {
		if (money < 1) {
			throw new ValidationException(BBSExceptionMessage.MONEY_INVALID);
		}
	}

	public static void days(int days) throws ValidationException {
		if (days < 1) {
			throw new ValidationException(BBSExceptionMessage.DAYS_INVALID);
		}
	}
	public static void nicks(String nicks) throws ValidationException {
		if (StringUtils.isEmpty(nicks)) {
			throw new ValidationException(BBSExceptionMessage.REMIT_NICKS_EMPTY);
		}
	}

	public static void userTitle(String title) throws ValidationException {
		int length=0;
		try{
			length=title.getBytes("GBK").length;
		}
		catch(Exception e){
			length=title.getBytes().length;
		}
		if ((!StringUtils.isEmpty(title)) && length > 14) {
			throw new ValidationException(
					BBSExceptionMessage.USERTITLE_TOO_LONG);
		}
	}

	public static void idiograph(String idiograph) throws ValidationException {
		if ((!StringUtils.isEmpty(idiograph)) && idiograph.length() > 200) {
			throw new ValidationException(
					BBSExceptionMessage.IDIOGRAPH_TOO_LONG);
		}
	}

	public static void smallNews(String title, String content)
			throws ValidationException {
		if (StringUtils.isEmpty(title) || title.length() < 1
				|| title.getBytes().length > 40) {
			throw new ValidationException(
					BBSExceptionMessage.SMALLNEWS_TITLE_INVALID);
		}
		if (StringUtils.isEmpty(content)) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_EMPTY);
		}
		if (content.length() < 5) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_TOO_SHORT);
		}
		if (content.length() > 500) {
			throw new ValidationException(
					BBSExceptionMessage.SMALLNEWS_CONTENT_TOO_LONG);
		}
	}

	public static void sendMessage(String users, String title, String content)
			throws ValidationException {
		if (StringUtils.isEmpty(users)) {
			throw new ValidationException(
					BBSExceptionMessage.MESSAGE_NICKS_EMPTY);
		}
		if (StringUtils.isEmpty(title) || title.length() < 1
				|| title.getBytes().length > 40) {
			throw new ValidationException(
					BBSExceptionMessage.MESSAGE_TITLE_INVALID);
		}
		if (StringUtils.isEmpty(content)) {
			throw new ValidationException(BBSExceptionMessage.CONTENT_EMPTY);
		}
		if (content.length() > 500) {
			throw new ValidationException(
					BBSExceptionMessage.MESSAGE_CONTENT_TOO_LONG);
		}
	}

	public static void diaryBook(String users, String bookName, String viewKey,
			String description) throws ValidationException {
		if (StringUtils.isEmpty(users)) {
			throw new ValidationException(BBSExceptionMessage.NOT_LOGIN);
		}
		if (StringUtils.isEmpty(bookName)) {
			throw new ValidationException(BBSExceptionMessage.BOOK_NAME_EMPTY);
		}
		if (StringUtils.isEmpty(viewKey)) {
			throw new ValidationException(
					BBSExceptionMessage.BOOK_VIEWKEY_EMPTY);
		}
		if (StringUtils.isEmpty(description)) {
			throw new ValidationException(
					BBSExceptionMessage.BOOK_DESCRIPTION_EMPTY);

		}
	}

	public static void checkStudentIdAndMima(String studentid, String mima)
			throws ValidationException {
		if (StringUtils.isEmpty(studentid)) {
			throw new ValidationException(BBSExceptionMessage.STUDENTID_EMPTY);
		}
		if (StringUtils.isEmpty(mima)) {
			throw new ValidationException(BBSExceptionMessage.MIMA_EMPTY);
		}
	}

}
