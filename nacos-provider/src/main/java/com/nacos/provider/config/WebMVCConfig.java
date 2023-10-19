package com.nacos.provider.config;

import com.nacos.provider.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer {

	private final LoginFilter loginFilter;


	@Value("${base.bsscm.permit-all:/shusi/auth/**}")
	private String permitAll;
	public WebMVCConfig(LoginFilter loginFilter) {
		this.loginFilter = loginFilter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] excludes = permitAll.split(",");
		registry.addInterceptor(loginFilter)
				.addPathPatterns("/**")
				.excludePathPatterns(excludes);
	}
}
