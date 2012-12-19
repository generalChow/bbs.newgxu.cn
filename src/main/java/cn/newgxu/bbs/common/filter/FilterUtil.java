package cn.newgxu.bbs.common.filter;

import cn.newgxu.bbs.common.filter.ubb.AudioFilter;
import cn.newgxu.bbs.common.filter.ubb.BrFilter;
import cn.newgxu.bbs.common.filter.ubb.ColorFilter;
import cn.newgxu.bbs.common.filter.ubb.DirFilter;
import cn.newgxu.bbs.common.filter.ubb.EmailFilter;
import cn.newgxu.bbs.common.filter.ubb.FaceFilter;
import cn.newgxu.bbs.common.filter.ubb.FlashFilter;
import cn.newgxu.bbs.common.filter.ubb.FontFilter;
import cn.newgxu.bbs.common.filter.ubb.FormatFilter;
import cn.newgxu.bbs.common.filter.ubb.IMGFilter;
import cn.newgxu.bbs.common.filter.ubb.MpFilter;
import cn.newgxu.bbs.common.filter.ubb.QtFilter;
import cn.newgxu.bbs.common.filter.ubb.QuoteFilter;
import cn.newgxu.bbs.common.filter.ubb.QuoteReluctantFilter;
import cn.newgxu.bbs.common.filter.ubb.RmFilter;
import cn.newgxu.bbs.common.filter.ubb.SizeFilter;
import cn.newgxu.bbs.common.filter.ubb.SoundFilter;
import cn.newgxu.bbs.common.filter.ubb.URLFilter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class FilterUtil {

	private static final Filter ubbFilter = getUbbFilter();

	private static Filter getUbbFilter() {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new AudioFilter(filter);
		filter = new BrFilter(filter);

		// 4*4*4种嵌套
		filter = new SizeFilter(filter);
		filter = new SizeFilter(filter);
		filter = new SizeFilter(filter);
		filter = new SizeFilter(filter);

		filter = new ColorFilter(filter);
		filter = new ColorFilter(filter);
		filter = new ColorFilter(filter);
		filter = new ColorFilter(filter);

		filter = new FaceFilter(filter);
		filter = new FaceFilter(filter);
		filter = new FaceFilter(filter);
		filter = new FaceFilter(filter);
		/*----------------------------------------*/
		filter = new FontFilter(filter);
		filter = new FontFilter(filter);
		filter = new DirFilter(filter);
		filter = new EmailFilter(filter);
		filter = new FlashFilter(filter);
		filter = new FormatFilter(filter);
		filter = new IMGFilter(filter);
		filter = new MpFilter(filter);
		filter = new QtFilter(filter);
		for (int i = 0; i < 10; i++) {
			filter = new QuoteReluctantFilter(filter);
		}
		filter = new QuoteFilter(filter);
		filter = new RmFilter(filter);
		filter = new SoundFilter(filter);
		filter = new URLFilter(filter);
		filter = new TitleWordFilter(filter);

		return filter;
	}

	public static String ubb(String str) {
		str = TagConvertFilter.before(str);
		str = ubbFilter.doFilter(str);
		str = TagConvertFilter.after(str);
		return str;
	}

	public static String nick(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		filter = new NameWordFilter(filter);
		return filter.doFilter(str);
	}

	public static String truename(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		filter = new NameWordFilter(filter);
		return filter.doFilter(str);
	}

	public static String email(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		return filter.doFilter(str);
	}

	public static String idcode(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		return filter.doFilter(str);
	}

	public static String homepage(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		return filter.doFilter(str);
	}

	public static String qq(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new WhitespaceEscapeFilter(filter);
		filter = new QuotationMarkFilter(filter);
		return filter.doFilter(str);
	}

	public static String idiograph(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		/*个人签名不需要过滤掉空格,而是将其格式化2010-11-11*/
		//filter = new WhitespaceEscapeFilter(filter);
		filter = new FormatFilter(filter);
		filter = new QuotationMarkFilter(filter);
		return filter.doFilter(str);
	}

	public static String title(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new TitleWordFilter(filter);
		return filter.doFilter(str.trim());
	}

	public static String replyContent(String str) {
		return ubb(str);
	}

	public static String optionString(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new TitleWordFilter(filter);
		return filter.doFilter(str);
	}

	public static String diaryContent(String str) {
		Filter filter = new BasicFilter();
		filter = new HTMLEscapeFilter(filter);
		filter = new TitleWordFilter(filter);
		filter = new BrFilter(filter);
		return filter.doFilter(str);
	}

}
