package org.keith.seckill.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "commandContextFilter", urlPatterns = { "/*" })
public class CommandContextFilter implements Filter {

	protected FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req;
			request.setAttribute("contextPath", "/seckill");
			chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}
}