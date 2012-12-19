package cn.newgxu.bbs.common.config;

import javax.sql.DataSource;

//import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static cn.newgxu.bbs.common.config.NewgxuConfig.*;

/**
 * 这是一个演示，并没有在服务器端应用。不过可以考虑将来使用。
 * 
 * @author longkai
 * @since 2012-08-19
 * @version 6.0
 */
@Configuration
public class JavaConfig {

	@Bean(destroyMethod = DESTROY_METHOD)
	public DataSource dataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
//		dataSource.setUrl(URL);
//		dataSource.setUsername(USERNAME);
//		dataSource.setPassword(PASSWORD);
//		dataSource.setDefaultAutoCommit(DEFAULT_AUTO_COMMIT);
//		return dataSource;
		return null;
	}

}
