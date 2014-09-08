package com.bulhosp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bulhosp.utils.SessionUtilsSingleton;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.jsf" })
public class AuthFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String reqURI = req.getRequestURI();
			String sessionToken = SessionUtilsSingleton.getInstance()
					.getSessionToken();
			if (sessionToken != null || reqURI.indexOf("/login.jsf") >= 0) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + "/login.jsf");
			}
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
