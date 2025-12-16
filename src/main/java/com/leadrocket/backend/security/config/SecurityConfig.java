package com.leadrocket.backend.security.config;

import com.leadrocket.backend.security.filter.AuthKeyFilter;
import com.leadrocket.backend.security.jwt.JwtFilter;
import com.leadrocket.backend.security.ratelimit.RateLimitFilter;
import com.leadrocket.backend.security.filter.IpWhitelistFilter;
import com.leadrocket.backend.security.authkey.AuthKeyService;
import com.leadrocket.backend.security.jwt.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	@Bean
	public FilterRegistrationBean<AuthKeyFilter> authKeyFilter(AuthKeyService authKeyService) {
		FilterRegistrationBean<AuthKeyFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new AuthKeyFilter(authKeyService));
		bean.addUrlPatterns("/api/v1/*");
		bean.setOrder(1);
		return bean;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter(JwtProvider jwtProvider) {
		FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new JwtFilter(jwtProvider));
		bean.addUrlPatterns("/api/v1/*");
		bean.setOrder(2);
		return bean;
	}
	@Bean
	public FilterRegistrationBean<IpWhitelistFilter> ipFilter() {
		FilterRegistrationBean<IpWhitelistFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new IpWhitelistFilter());
		bean.addUrlPatterns("/api/v1/*");
		bean.setOrder(3);
		return bean;
	}

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
		FilterRegistrationBean<RateLimitFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new RateLimitFilter());
		bean.addUrlPatterns("/api/v1/*");
		bean.setOrder(4);
		return bean;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
