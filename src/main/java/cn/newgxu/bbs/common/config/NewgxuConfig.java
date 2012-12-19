package cn.newgxu.bbs.common.config;

/**
 * 配置文件，写在java类里的配置，配饰spring的java配置。可以考虑将来使用。
 * 
 * @author longkai
 * @version 6.0
 * @since 2012-08-19
 */
public class NewgxuConfig {

	public static final String	DRIVER_CLASS_NAME	= "com.mysql.jdbc.Driver";
	public static final String	URL					= "jdbc:mysql://127.0.0.1/valhalla?useUnicode=true&amp;characterEncoding=utf8";
	public static final String	USERNAME			= "root";
	public static final String	PASSWORD			= "root";
	public static final String	DESTROY_METHOD		= "close";
	public static final boolean	DEFAULT_AUTO_COMMIT	= false;

}
