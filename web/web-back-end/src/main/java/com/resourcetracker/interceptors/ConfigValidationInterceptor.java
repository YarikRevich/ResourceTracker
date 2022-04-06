package com.resourcetracker.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
public class ConfigValidationInterceptor implements HandlerInterceptor {
	final static Logger logger = LogManager.getLogger(Loop.class);

	@Override
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		StringBuilder targetUrl = new StringBuilder();
		if (this.redirectURL.startsWith("/")) {
			// Do not apply context path to relative URLs.
			targetUrl.append(request.getContextPath());
		}
		targetUrl.append(this.redirectURL);

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to: " + targetUrl.toString());
		}

		response.sendRedirect(targetUrl.toString());
		return false;
		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception exception) throws Exception {
	}
}
