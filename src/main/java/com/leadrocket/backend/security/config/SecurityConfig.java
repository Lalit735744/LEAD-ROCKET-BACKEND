package com.leadrocket.backend.security.config;

import com.leadrocket.backend.security.jwt.JwtFilter;
import com.leadrocket.backend.security.ratelimit.RateLimitFilter;
import com.leadrocket.backend.security.filter.IpWhitelistFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class SecurityConfig {

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new JwtFilter());
		bean.addUrlPatterns("/api/v1/*");
		return bean;
	}
	@Bean
	public FilterRegistrationBean<IpWhitelistFilter> ipFilter() {
		FilterRegistrationBean<IpWhitelistFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new IpWhitelistFilter());
		bean.addUrlPatterns("/api/v1/*");
		return bean;
	}

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
		FilterRegistrationBean<RateLimitFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new RateLimitFilter());
		bean.addUrlPatterns("/api/v1/*");
		return bean;
	}
}
