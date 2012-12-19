package cn.newgxu.bbs.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.util.HttpUtil;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.service.StatisticService;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HitsCounterFilter implements Filter {

	private static final Log log = LogFactory.getLog(AutoLoginFilter.class);

	protected FilterConfig filterConfig;

	//加的静态化
	StatisticService statisticService;

	public void destroy() {
		filterConfig = null;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		int forumId = HttpUtil.getIntParameter(request, "forumId");

		statisticService.addHit(forumId);

		if (log.isDebugEnabled()) {
			log.debug("forumId :" + forumId + ", 计数加1");
		}
		//StaticService.index();
		chain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		statisticService = (StatisticService) Util.getBean("statisticService");
	}

}
