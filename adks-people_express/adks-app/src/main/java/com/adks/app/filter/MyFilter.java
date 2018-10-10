package com.adks.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.CompositeFilter;

public class MyFilter extends CompositeFilter {

	public void doFilterInternal(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();
		System.out.println(url);
		if (url.contains("/views/ueditor/jsp/")) {
			chain.doFilter(req, res);
		} else if (url.contains("/ueditor/jsp/")) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(request, res, chain);
		}
	}

}