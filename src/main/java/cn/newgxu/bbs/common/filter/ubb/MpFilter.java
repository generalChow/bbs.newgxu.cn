package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class MpFilter extends AbstractBaseFilter {

	public MpFilter(Filter filter) {
		super(filter);
	}

//	private static final String MP_PATTERN_STRING = "\\[mp=(.+?),(.+?)\\](.+?)\\[\\/mp\\]";
//
//	private static final String MP_APPEND_1 = "<br><OBJECT class=OBJECT "
//			+ "id=MediaPlayer height=\"";
//
//	private static final String MP_APPEND_2 = "\" width=\"";
//
//	private static final String MP_APPEND_3 = "\" align=middle classid=CLSID:"
//			+ "22d6f312-b0f6-11d0-94ab-0080c74c7e95><PARAM NAME=\"ShowStatusBar\""
//			+ " VALUE=\"-1\"><PARAM NAME=\"Filename\" VALUE=\"";
//
//	private static final String MP_APPEND_4 = "\"><embed type=application/"
//			+ "x-oleobject codebase=http://activex.microsoft.com/activex/controls/"
//			+ "mplayer/en/nsmp2inf.cab#Version=5,1,52,701 flename=mp src=\"";
//
//	private static final String MP_APPEND_5 = "\" width=\"";
//
//	private static final String MP_APPEND_6 = "\" height=\"";
//
//	private static final String MP_APPEND_7 = "\"></embed></OBJECT><br>";
	private static final String TEMP_CHAR="-!-!-!-!-";
	private static final String MP_PATTERN_STRING = "\\[mp(.+?)\\](.+?)\\[\\/mp\\]";

	private static final String MP_APPEND_1 = "<object type=\"application/x-shockwave-flash\" data=\"/js/audioplayer.swf\" width=\"290\" height=\"24\" id=\"ID70554\"><param name=\"movie\" "
                                              +"value=\"/js/audioplayer.swf\" /><param name=\"FlashVars\" " 
                                              +"value=\"playerID=ID70554&bg=0xCDDFF3&leftbg=0x357DCE&lefticon=0xF2F2F2&rightbg=0x357DCE&rightbghover=0x4499EE&righticon=0xF2F2F2&righticonhover=0xFFFFFF&text=0x357DCE&slider=0x357DCE&track=0xFFFFFF&border=0xFFFFFF"
                                              +"&loader=0x8EC2F4&loop=no&autostart=no&soundFile=";
                         

	private static final String MP_APPEND_2 = "\" /><param name=\"qualit\" value=\"high\" /><param name=\"menu\" value=\"false\" /><param name=\"wmode\" value=\"transparent\" /></object>";


//
//	private static final String MP_APPEND_3 = "\" align=middle classid=CLSID:"
//			+ "22d6f312-b0f6-11d0-94ab-0080c74c7e95><PARAM NAME=\"ShowStatusBar\""
//			+ " VALUE=\"-1\"><PARAM NAME=\"Filename\" VALUE=\"";
//
//	private static final String MP_APPEND_4 = "\"><embed type=application/"
//			+ "x-oleobject codebase=http://activex.microsoft.com/activex/controls/"
//			+ "mplayer/en/nsmp2inf.cab#Version=5,1,52,701 flename=mp src=\"";
//
//	private static final String MP_APPEND_5 = "\" width=\"";
//
//	private static final String MP_APPEND_6 = "\" height=\"";
//
//	private static final String MP_APPEND_7 = "\"></embed></OBJECT><br>";

	public String convert(String input) {
		try {
			return mpConverter(input);
		} catch (Exception e) {
			e.printStackTrace();
			return input;
		}
	}

//	private String mpConverter(String input) throws Exception {
//		Pattern pattern = Pattern.compile(MP_PATTERN_STRING, 2);
//		Matcher matcher = pattern.matcher(input);
//		StringBuffer stringbuffer = new StringBuffer();
//		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
//			matcher.appendReplacement(stringbuffer, MP_APPEND_1
//					+ matcher.group(2) + MP_APPEND_2 + matcher.group(1)
//					+ MP_APPEND_3 + matcher.group(3) + MP_APPEND_4
//					+ matcher.group(3) + MP_APPEND_5 + matcher.group(1)
//					+ MP_APPEND_6 + matcher.group(2) + MP_APPEND_7);
//		}
//
//		matcher.appendTail(stringbuffer);
//		return stringbuffer.toString();
//	}
	private String mpConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(MP_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			matcher.appendReplacement(stringbuffer, MP_APPEND_1
					+ StringUtils.replace(matcher.group(2), ".yws", " ") + MP_APPEND_2);
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}
	
	/**
	 * 文件名中有 $ ，要使用 convert 方法时，不能得到想要的结果。
	 * 这里使用其他路径得到想要的结果。
	 * 保留了原来的命名规则
	 * 
	 * @param input
	 * @return
	 */
	public String newConvert(String input) {
		try {
			Pattern pattern = Pattern.compile(MP_PATTERN_STRING, 2);
			Matcher matcher = pattern.matcher(input);
			StringBuffer stringbuffer = new StringBuffer();
			String temp=null;
			for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
				temp=matcher.group(2);
				temp=temp.replace("$",TEMP_CHAR);
				matcher.appendReplacement(stringbuffer, MP_APPEND_1
						+ StringUtils.replace(temp, ".yws", " ") + MP_APPEND_2);
			}
			stringbuffer=matcher.appendTail(stringbuffer);
			temp=stringbuffer.toString();
			return temp.replace(TEMP_CHAR,"$");
		} catch (Exception e) {
			e.printStackTrace();
			return input;
		}
	}
	
	public static void main(String a[]){
		MpFilter mpf=new MpFilter(null);
		System.out.println(mpf.newConvert("<br /><p>sasa</p><div>[mp=500,350]/upload/mp3/2011/10/8/16/$1318064231512_10.mp3[/mp]</div>"));
		System.out.println(mpf.convert("<br /><p>sasa</p><div>[mp=500,350]/upload/mp3/2011/10/8/16/$1318064231512_10.mp3[/mp]</div>"));
	}
}
