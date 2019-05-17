package com.silas.project.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 全局拦截器注册配置类
 */
@Configuration
//@EnableWebMvc
public class InterceptorConfig  extends WebMvcConfigurerAdapter {

	@Bean
	CrosInterceptor  crosInterceptor(){
		return new CrosInterceptor();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(crosInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
