package com.silas.project.common.interceptor;

import com.silas.project.main.config.ProjectConfigParams;
import com.silas.project.main.config.ProjectPropertyBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 跨域拦截器
 *
 * @author silas
 */
public class CrosInterceptor implements HandlerInterceptor {


	@Autowired
	private ProjectPropertyBean propertyBean;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		ProjectConfigParams configParams = propertyBean.getConfigParams();
		String wildcard = "*";
		if (configParams != null) {
			String originHeader = httpServletRequest.getHeader("Origin");
			if (!StringUtils.isEmpty(configParams.getAllowedOrigin()) && !StringUtils.isEmpty(originHeader)) {
				List<String> origins = Arrays.asList(configParams.getAllowedOrigin().split(","));
				if (origins.contains(originHeader) || configParams.getAllowedOrigin().toLowerCase().contains(wildcard)) {
					httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
				}
			}
			if (!StringUtils.isEmpty(configParams.getAllowedHeader())) {
				httpServletResponse.setHeader("Access-Control-Allow-Headers", configParams.getAllowedHeader());
			}
			if (configParams.getAllowedCredentials()) {
				httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			}
			if (!StringUtils.isEmpty(configParams.getAllowedMethod())) {
				httpServletResponse.setHeader("Access-Control-Allow-Methods", configParams.getAllowedMethod());
				String method = httpServletRequest.getMethod().toLowerCase();
				if (!configParams.getAllowedMethod().toLowerCase().contains(method) && !configParams.getAllowedMethod().toLowerCase().contains(wildcard)) {
					httpServletResponse.setHeader("Content-type", "text/html");
					httpServletResponse.setStatus(405);
					httpServletResponse.getWriter().write("只允许访问 " + configParams.getAllowedMethod() + " 中的方法");
					return false;
				}
			}

		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
