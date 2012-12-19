package cn.newgxu.bbs.common.filter.ubb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.newgxu.bbs.common.filter.AbstractBaseFilter;
import cn.newgxu.bbs.common.filter.Filter;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class AudioFilter extends AbstractBaseFilter {

	public AudioFilter(Filter filter) {
		super(filter);
	}

	private static final String AUDIO_PATTERN_STRING = "\\[audio(.+?)\\]";

	private static final String[] AUDIO_TEXT = { "就这事啊！地球人都知道", "嘿嘿！逗你玩！",
			"年轻人，做人要厚道！", "借你干嘛，啊,这不是拿我打岔吗？", "一定要幸福哦！", "你好可爱哦！", "加油哦！",
			"我好想你喔！", "你不要这样嘛", "我真的好感激你哦", "你简直太让我感动了！",
			"过来..近点...再近点...Kiss", "弓虽！实在是弓虽！", "真的么？我要晕了。", "最近我真的好想你哦。",
			"啊，你是中邪了...", "你好讨厌喔~", "抽根烟吧！", "不干，不干，不干，就是不干！", "饿不饿我请你吃饭。",
			"听见了吗,那是我肚子在叫", "哼！小样！我看你还真是找扁！", "坦白从宽，你就交代了吧。", "哼，你这骗子！",
			"我就是喜欢你动作非常慢的时候！", "Hi!是你吧，我知道你在，我可看见你了。", "哎，看来你真的很寂寞！",
			"年轻人虚心点没错。", "我说你还不明白，你猪头啊！", "886~", "88了您呢", "哎哟，老板来了，我先走了啊。",
			"不行不行，我得上厕所...上厕所。", "晚安，做个好梦！", "我困得不行了，得先走一步了", "有人在家吗？", "开会咯！",
			"早安！", "还在加班？真忙还是假忙啊。", "你可算来了，我等你很久了！", "来啦小妞~", "早上好，你来了~",
			"你要死了，到现在才出现，到哪里潜水去！", "喂~~我老板来了，等会再聊。", "啊~~~这是一定要的啦。",
			"嗯哼~人家...去撇个尿啰..嗯哼~", "嗯哼~来，亲一个~",
			"起来动一动吧，fowllow me,one more,tow more...",
			"祝你生日快乐~祝你生日快乐..喔喔，祝你生日快乐哦", "不知道该说什么...", "好想睡哦...zzZ", "让我想一想~" };

	private static final String AUDIO_APPEND_1 = "<br><TABLE width=90% "
			+ "border=0 align=center cellPadding=0 cellSpacing=0><TBODY><TR><TD "
			+ "title=鼠标左键单击这里播放 style='CURSOR: hand' "
			+ "onclick=this.parentElement.children[0].children[0].Play() noWrap>"
			+ "<OBJECT id=Audible19704814047 codeBase='<a target=_blank "
			+ "href=http://download.macromedia.com/pub/shockwave/cabs/flash"
			+ "/swflash.cab>http://download.macromedia.com/pub/shockwave/cabs/"
			+ "flash/swflash.cab</a>#version=7,0,0,0' height=48 width=48 "
			+ "classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000><PARAM "
			+ "NAME='movie' VALUE='flash/";

	private static final String AUDIO_APPEND_2 = ".swf'><PARAM NAME='menu' "
			+ "VALUE='false'><PARAM NAME='quality' VALUE='high'><PARAM NAME='play' "
			+ "VALUE='false'><PARAM NAME='wmode' VALUE='transparent'>"
			+ "<embed src='flash/";

	private static final String AUDIO_APPEND_3 = ".swf' quality='high' "
			+ "width='48' height='48'></embed></OBJECT>";

	private static final String AUDIO_APPEND_4 = "</TD></TR></TBODY></TABLE><br>";

	public String convert(String input) {
		try {
			return audioConverter(input);
		} catch (Exception e) {
			return input;
		}
	}

	private String getText(String index) throws Exception {
		int i = Integer.parseInt(index) - 1;
		return AUDIO_TEXT[i];
	}

	private String audioConverter(String input) throws Exception {
		Pattern pattern = Pattern.compile(AUDIO_PATTERN_STRING, 2);
		Matcher matcher = pattern.matcher(input);
		StringBuffer stringbuffer = new StringBuffer();
		int i = 0;
		for (boolean flag = matcher.find(); flag; flag = matcher.find()) {
			i++;
			if (i > 3) {
				matcher.appendReplacement(stringbuffer, "");
			} else {
				matcher.appendReplacement(stringbuffer, AUDIO_APPEND_1
						+ matcher.group(1) + AUDIO_APPEND_2 + matcher.group(1)
						+ AUDIO_APPEND_3 + getText(matcher.group(1))
						+ AUDIO_APPEND_4);
			}
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

}
