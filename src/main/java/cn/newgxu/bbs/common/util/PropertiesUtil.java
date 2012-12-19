package cn.newgxu.bbs.common.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class PropertiesUtil {

	private static final Log log = LogFactory.getLog(PropertiesUtil.class);

	Properties initProps = new Properties();

	InputStream in = null;

	private String file;

	public PropertiesUtil(String file) {
		this.file = file;
		init();
	}

	private void init() {
		try {
			in = getClass().getResourceAsStream(this.file);
			initProps.load(in);
		} catch (Exception e) {
			log.fatal(e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.warn(e);
			}
		}
	}

	public String getProperty(String key) {
		return initProps.getProperty(key);
	}

	public void setProperty(String key, String value) {
		initProps.setProperty(key, value);
		try {
			initProps.store(new FileOutputStream("filename.properties"), null);
		} catch (Exception e) {
			log.error(e);
		}
	}

}
